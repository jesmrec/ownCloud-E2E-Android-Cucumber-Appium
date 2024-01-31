package utils.api;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.LocProperties;
import utils.log.Log;
import utils.network.oCHttpClient;

public class AuthAPI {

    private final OkHttpClient httpClient = oCHttpClient.getUnsafeOkHttpClient();
    private final String wellKnown = "/.well-known/openid-configuration";
    private String urlServer = System.getProperty("server");
    private String userAgent = LocProperties.getProperties().getProperty("userAgent");
    private String host = urlServer.split("//")[1];

    public AuthAPI() {
    }

    public boolean isOidc()
            throws IOException {
        String urlCheck = urlServer + wellKnown;
        Log.log(Level.FINE, "is OIDC?: " + urlCheck);
        Request request = davRequestUnauth(urlCheck);
        Response response = httpClient.newCall(request).execute();
        Log.log(Level.FINE, "Body length: " + response.body().contentLength());
        boolean withBody = response.body().contentLength() > 0;
        response.close();
        return withBody;
    }

    protected Request davRequestUnauth(String url) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("OCS-APIREQUEST", "true")
                .addHeader("User-Agent", userAgent)
                .addHeader("Host", host)
                .method("GET", null)
                .build();
        return request;
    }
}
