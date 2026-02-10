package utils.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.entities.OCSpace;
import utils.entities.OCSpaceMember;
import utils.log.Log;
import utils.parser.OCMemberJSONHandler;

public class GraphAPI extends CommonAPI {

    private final String graphPath = "/graph/v1.0/";
    private final String drives = "drives/";
    private final String myDrives = "me/drives/";
    private final String members = "/graph/v1beta1/drives/";

    public static GraphAPI instance;

    private GraphAPI() throws IOException {
    }

    public static GraphAPI getInstance() throws IOException {
        if (instance == null) {
            instance = new GraphAPI();
        }
        return instance;
    }

    public void createSpace(String name, String description, String userName) throws IOException {
        Log.log(Level.FINE, "CREATE SPACE: " + name + " " + description);
        String url = urlServer + graphPath + drives;
        Log.log(Level.FINE, "URL: " + url);
        Request request = postRequest(url, createBodySpace(name, description), userName);
        Response response = httpClient.newCall(request).execute();
        Log.log(Level.FINE, "Response Code: " + response.code());
        Log.log(Level.FINE, "Response Body: " + response.body().string());
        response.close();
    }

    public OCSpace getPersonal() throws IOException {
        Log.log(Level.FINE, "Get personal space");
        String url = urlServer + graphPath + myDrives;
        Request request = getRequest(url);
        Response response = httpClient.newCall(request).execute();
        String json = response.body().string();
        OCSpace personal = new OCSpace();
        JSONObject obj = new JSONObject(json);
        JSONArray value = obj.getJSONArray("value");
        for (int i = 0; i < value.length(); i++) {
            JSONObject jsonObject = value.getJSONObject(i);
            String type = jsonObject.getString("driveType");
            if (type.equals("personal")) { //Just for user created spaces
                personal.setType(jsonObject.getString("driveType"));
                personal.setId(jsonObject.getString("id"));
                personal.setName(jsonObject.getString("name"));
                Log.log(Level.FINE, "Space id returned: " +
                        personal.getId() + " " + personal.getName());
            }
        }
        return personal;
    }

    private RequestBody createBodySpace(String name, String description) {
        Log.log(Level.FINE, "BODY SPACE: Name: " + name + " . Description: " + description);
        String json = "{\"name\":\" " + name + " \",\"driveType\":\"project\", \"description\":\" " + description + " \"}";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        return body;
    }

    public List<OCSpace> getMySpaces() throws IOException {
        Log.log(Level.FINE, "GET my SPACES");
        String url = urlServer + graphPath + myDrives;
        Request request = getRequest(url);
        Response response = httpClient.newCall(request).execute();
        return getSpacesFromResponse(response);
    }

    //User "alice" by default
    public void removeSpacesOfUser() throws IOException {
        Log.log(Level.FINE, "REMOVE custom SPACES of: " + user);
        List<OCSpace> spacesOfUser = getMySpaces();
        for (OCSpace space : spacesOfUser) {
            String url = urlServer + graphPath + drives + space.getId();
            Log.log(Level.FINE, "URL remove space: " + url);
            //First, disable
            Request requestDisable = deleteRequest(url, "Alice");
            httpClient.newCall(requestDisable).execute();
            //Then, delete
            Request requestDelete = deleteSpaceRequest(url);
            httpClient.newCall(requestDelete).execute();
        }
    }

    public void disableSpace(String name, String description) throws IOException {
        Log.log(Level.FINE, "DISABLE SPACE: " + name + " " + description);
        String spaceId = getSpaceIdFromNameAndDescription(name, description);
        String url = urlServer + graphPath + drives + spaceId;
        Log.log(Level.FINE, "URL: " + url);
        Request request = deleteRequest(url, "Alice");
        Response response = httpClient.newCall(request).execute();
        Log.log(Level.FINE, "Response Code: " + response.code());
        Log.log(Level.FINE, "Response Body: " + response.body().string());
        response.close();
    }

    private Request deleteSpaceRequest(String url) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Authorization", "Basic " + credentialsB64)
                .addHeader("Host", host)
                .addHeader("Purge", "T")
                .delete()
                .build();
        return request;
    }

    public String getSpaceIdFromNameAndDescription(String name, String description) throws IOException {
        Log.log(Level.FINE, "Look for space ID or null: " + name + " " + description);
        List<OCSpace> mySpaces = getMySpaces();
        for (OCSpace space : mySpaces) {
            if (space.getName().trim().equals(name) &&
                    space.getDescription().trim().equals(description)) {
                Log.log(Level.FINE, "FOUND: ID of space: " + space.getId() + " " + space.getName());
                return space.getId();
            }
        }
        return null;
    }

    public String getSpaceIdFromName(String name) throws IOException {
        Log.log(Level.FINE, "Look for space ID or null: " + name);
        List<OCSpace> mySpaces = getMySpaces();
        for (OCSpace space : mySpaces) {
            if (space.getName().trim().equals(name)) {
                Log.log(Level.FINE, "FOUND: ID of space: " + space.getId() + " " + space.getName());
                return space.getId();
            }
        }
        return null;
    }

    public OCSpaceMember getMemberOfSpace(String spaceName, String userName) throws IOException {
        Log.log(Level.FINE, "Get member of space: " + spaceName + " user: " + " userName");
        String spaceId = getSpaceIdFromName(spaceName);
        String url = urlServer + members + spaceId + "/root/permissions";
        Log.log(Level.FINE, "URL: " + url);
        Request request = getRequest(url);
        Response response = httpClient.newCall(request).execute();
        OCMemberJSONHandler handler = new OCMemberJSONHandler();
        List<OCSpaceMember> spaceMembers =  handler.parse(response.body().string());
        for (OCSpaceMember member : spaceMembers){
            if (member.getDisplayName().equals(userName)) {
                return member;
            }
        }
        return null;
    }

    //Move to parser space
    private List<OCSpace> getSpacesFromResponse(Response httpResponse) throws IOException {
        String json = httpResponse.body().string();
        ArrayList<OCSpace> spaces = new ArrayList<>();
        JSONObject obj = new JSONObject(json);
        JSONArray value = obj.getJSONArray("value");
        for (int i = 0; i < value.length(); i++) {
            JSONObject jsonObject = value.getJSONObject(i);
            String type = jsonObject.getString("driveType");
            if (type.equals("project")) { //Just for user created spaces
                OCSpace space = new OCSpace();
                space.setType(jsonObject.getString("driveType"));
                space.setId(jsonObject.getString("id"));
                space.setName(jsonObject.getString("name"));
                // Description can be null
                space.setDescription(jsonObject.optString("description", ""));
                JSONObject owner = jsonObject.getJSONObject("owner");
                JSONObject user = owner.getJSONObject("user");
                space.setOwner(user.getString("id"));
                JSONObject quota = jsonObject.getJSONObject("quota");
                space.setQuota(quota.getLong("total"));
                spaces.add(space);
                Log.log(Level.FINE, "Space id returned: " + space.getId() + " " + space.getName());
            }
        }
        return spaces;
    }
}
