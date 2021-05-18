package android;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import io.appium.java_client.MobileBy;
import utils.date.DateUtils;
import utils.entities.OCCapability;
import utils.entities.OCShare;
import utils.log.Log;

/* Pending: move to Page Factory when view is refactored.*/

public class SharingPage extends CommonPage {

    private final String addshareebutton_id = "com.owncloud.android:id/addUserButton";
    private final String addpubliclinkbutton_id = "com.owncloud.android:id/addPublicLinkButton";
    private final String editprivateshare_id = "com.owncloud.android:id/editShareButton";
    private final String editpubliclink_id = "com.owncloud.android:id/editPublicLinkButton";
    private final String sharefilename_id = "com.owncloud.android:id/shareFileName";
    private final String unshareprivate_id = "com.owncloud.android:id/unshareButton";
    private final String deleteprivatelink_id = "com.owncloud.android:id/deletePublicLinkButton";
    private final String privatesharesectiontitle_id = "com.owncloud.android:id/shareWithUsersSectionTitle";
    private String acceptdeletion_id = "android:id/button1";
    private String canceldeletion_id = "android:id/button3";

    public SharingPage(){
        super();
    }

    public boolean isHeader() {
        return !driver.findElementsByAndroidUIAutomator("new UiSelector().text(\"Share\");").isEmpty();
    }

    public void addPrivateShare(){
        Log.log(Level.FINE, "Starts: add private share");
        waitById(5, sharefilename_id);
        driver.findElement(MobileBy.id(addshareebutton_id)).click();
    }

    public void addPublicLink(){
        Log.log(Level.FINE, "Starts: add public link");
        waitById(5, sharefilename_id);
        driver.findElement(MobileBy.id(addpubliclinkbutton_id)).click();
    }

    public void openPrivateShare(String itemName){
        Log.log(Level.FINE, "Starts: edit private share: " + itemName);
        waitById(5, editprivateshare_id);
        driver.findElement(MobileBy.id(editprivateshare_id)).click();
    }

    public void openPublicLink(String itemName){
        Log.log(Level.FINE, "Starts: open public link: " + itemName);
        driver.findElement(MobileBy.id(editpubliclink_id)).click();
    }

    public boolean isItemInListPrivateShares(String sharee) {
        waitById(5, privatesharesectiontitle_id);
        takeScreenshot("PrivateShare/ItemInListPrivateShare_"+sharee);
        return !driver.findElementsByAndroidUIAutomator("new UiSelector().text(\""+sharee+"\");")
                .isEmpty();
    }

    public boolean isItemInListPublicShares(String itemName) {
        waitById(5, privatesharesectiontitle_id);
        takeScreenshot("PublicShare/ItemInListPubilcShare_"+itemName);
        return !driver.findElementsByAndroidUIAutomator("new UiSelector().text(\""+itemName+"\");")
                .isEmpty();
    }

    public void deletePrivateShare(){
        driver.findElement(MobileBy.id(unshareprivate_id)).click();
        takeScreenshot("PrivateShare/Deletion");
    }

    public void deletePublicShare(){
        driver.findElement(MobileBy.id(deleteprivatelink_id)).click();
        takeScreenshot("PublicShare/Deletion");
    }

    public boolean checkCorrectShare(OCShare remoteShare, List<List<String>> dataList ){
        Log.log(Level.FINE, "Starts: Check correct share");
        HashMap<String, String> mapFields = turnListToHashmap(dataList);
        if (remoteShare == null){
            Log.log(Level.FINE, "Remote share is null, returning false");
            return false;
        }
        for (Map.Entry<String, String> entry : mapFields.entrySet()) {
            Log.log(Level.FINE, "Entry KEY: " + entry.getKey() + " - VALUE: " + entry.getValue());
            switch (entry.getKey()){
                case "id":{
                    if (!remoteShare.getId().equals(entry.getValue())){
                        Log.log(Level.FINE, "ID does not match - Remote: " + remoteShare.getId()
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                    break;
                }
                case "user":{
                    if (remoteShare.getType().equals("0")) { // private share
                        if (!remoteShare.getShareeName().equals(entry.getValue())) {
                            Log.log(Level.FINE, "Sharee does not match - Remote: " + remoteShare.getShareeName()
                                    + " - Expected: " + entry.getValue());
                            return false;
                        }
                    }
                    break;
                }
                case "password":{
                    if (!(remoteShare.getType().equals("3") && remoteShare.hasPassword())) {
                        Log.log(Level.FINE, "Password not present");
                        return false;
                    }
                    break;
                }
                case "name":{
                    if (!remoteShare.getLinkName().equals(entry.getValue())){
                        Log.log(Level.FINE, "Item name does not match - Remote: " + remoteShare.getLinkName()
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                    break;
                }
                case "path":{
                    if (!remoteShare.getItemName().equals(entry.getValue())){
                        Log.log(Level.FINE, "Item path does not match - Remote: " + remoteShare.getItemName()
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                    break;
                }
                case "uid_owner":{
                    if (!remoteShare.getOwner().equals(entry.getValue())){
                        Log.log(Level.FINE, "Owner name does not match - Remote: " + remoteShare.getOwner()
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                    break;
                }
                case "permissions":{
                    if (!remoteShare.getPermissions().equals(entry.getValue())){
                        Log.log(Level.FINE, "Permissions do not match - Remote: " + remoteShare.getPermissions()
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                    break;
                }
                case "expiration days":{
                    String dateRemote = remoteShare.getExpiration();
                    int expiration = DateUtils.minExpirationDate(
                            OCCapability.getInstance().expirationDateDays(),
                            Integer.valueOf(entry.getValue())
                    );
                    String expDate = DateUtils.dateInDaysWithServerFormat(Integer.toString(expiration));
                    Log.log(Level.FINE, "Expiration dates: Remote: " + dateRemote
                            + " - Expected: " + expDate);
                    if (!dateRemote.equals(expDate)){
                        Log.log(Level.FINE, "Expiration dates do not match");
                        return false;
                    }
                }
            }
        }
        Log.log(Level.FINE, "All fields match. Returning true");
        return true;
    }

    public void acceptDeletion(){
        takeScreenshot("PrivateShare/AcceptDeletion");
        driver.findElement(MobileBy.id(acceptdeletion_id)).click();
    }

    public void cancelDeletion(){
        driver.findElement(MobileBy.id(canceldeletion_id)).click();
    }

    private HashMap turnListToHashmap(List<List<String>> dataList){
        HashMap<String, String> mapFields = new HashMap<String, String>();
        for (List<String> rows : dataList) {
            mapFields.put(rows.get(0),rows.get(1));
        }
        return mapFields;
    }
}