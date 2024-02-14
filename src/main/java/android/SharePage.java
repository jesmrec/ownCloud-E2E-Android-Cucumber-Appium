/**
 * ownCloud Android Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.date.DateUtils;
import utils.entities.OCShare;
import utils.log.Log;

public class SharePage extends CommonPage {

    @AndroidFindBy(id = "com.owncloud.android:id/addUserButton")
    private WebElement addPrivateShare;

    @AndroidFindBy(id = "com.owncloud.android:id/addPublicLinkButton")
    private WebElement addPublicLink;

    @AndroidFindBy(id = "com.owncloud.android:id/editShareButton")
    private WebElement editPrivateShare;

    @AndroidFindBy(id = "com.owncloud.android:id/editPublicLinkButton")
    private WebElement editPublicLink;

    @AndroidFindBy(id = "com.owncloud.android:id/unshareButton")
    private WebElement removePrivateShare;

    @AndroidFindBy(id = "com.owncloud.android:id/deletePublicLinkButton")
    private WebElement removePublicLink;

    @AndroidFindBy(id = "android:id/button1")
    private WebElement acceptDeletion;

    @AndroidFindBy(id = "android:id/button3")
    private WebElement cancelDeletion;

    @AndroidFindBy(id = "com.owncloud.android:id/shareNoUsers")
    private WebElement noPrivateShares;

    @AndroidFindBy(id = "com.owncloud.android:id/shareNoPublicLinks")
    private WebElement noPublicLinks;

    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]")
    private WebElement close;

    public SharePage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void addPrivateShare() {
        Log.log(Level.FINE, "Starts: add private share");
        addPrivateShare.click();
    }

    public void addPublicLink() {
        Log.log(Level.FINE, "Starts: add public link");
        addPublicLink.click();
    }

    public void openPrivateShare(String itemName) {
        Log.log(Level.FINE, "Starts: edit private share: " + itemName);
        editPrivateShare.click();
    }

    public void openPublicLink(String itemName) {
        Log.log(Level.FINE, "Starts: open public link: " + itemName);
        editPublicLink.click();
    }

    public boolean isItemInListPrivateShares(String sharee) {
        return !findListUIAutomatorText(sharee).isEmpty();
    }

    public boolean isItemInListPublicShares(String itemName) {
        return !findListUIAutomatorText(itemName).isEmpty();
    }

    public boolean isListPublicLinksEmpty() {
        return noPublicLinks.isDisplayed();
    }

    public void deletePrivateShare() {
        removePrivateShare.click();
    }

    public void deletePublicShare() {
        removePublicLink.click();
    }

    public boolean isShareCorrect(OCShare remoteShare, List<List<String>> dataList) {
        Log.log(Level.FINE, "Starts: Check correct share");
        HashMap<String, String> mapFields = turnListToHashmap(dataList);
        if (remoteShare == null) {
            Log.log(Level.FINE, "Remote share is null, returning false");
            return false;
        }
        for (Map.Entry<String, String> entry : mapFields.entrySet()) {
            Log.log(Level.FINE, "Entry KEY: " + entry.getKey() + " - VALUE: " + entry.getValue());
            switch (entry.getKey()) {
                case "id": {
                    if (!remoteShare.getId().equals(entry.getValue())) {
                        Log.log(Level.FINE, "ID does not match - Remote: " + remoteShare.getId()
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                    break;
                }
                case "user": {
                    if (remoteShare.getType().equals("0")) { // private share
                        if (!remoteShare.getShareeName().equalsIgnoreCase(entry.getValue())) {
                            Log.log(Level.FINE, "Sharee does not match - Remote: " + remoteShare.getShareeName()
                                    + " - Expected: " + entry.getValue());
                            return false;
                        }
                    }
                    break;
                }
                case "password": {
                    if (!(remoteShare.getType().equals("3") && remoteShare.hasPassword())) {
                        Log.log(Level.FINE, "Password not present");
                        return false;
                    }
                    break;
                }
                case "name": {
                    if (!remoteShare.getLinkName().equals(entry.getValue())) {
                        Log.log(Level.FINE, "Item name does not match - Remote: " + remoteShare.getLinkName()
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                    break;
                }
                case "path": {
                    if (!remoteShare.getItemName().equals(entry.getValue())) {
                        Log.log(Level.FINE, "Item path does not match - Remote: " + remoteShare.getItemName()
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                    break;
                }
                case "uid_owner": {
                    if (!remoteShare.getOwner().equalsIgnoreCase(entry.getValue())) {
                        Log.log(Level.FINE, "Owner name does not match - Remote: " + remoteShare.getOwner()
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                    break;
                }
                case "permissions": {
                    if (!remoteShare.getPermissions().equals(entry.getValue())) {
                        Log.log(Level.FINE, "Permissions do not match - Remote: " + remoteShare.getPermissions()
                                + " - Expected: " + entry.getValue());
                        return false;
                    }
                    break;
                }
                case "expiration days": {
                    String dateRemote = remoteShare.getExpiration();
                    int expiration = Integer.valueOf(entry.getValue());
                    String expDate = DateUtils.dateInDaysWithServerFormat(Integer.toString(expiration));
                    Log.log(Level.FINE, "Expiration dates: Remote: " + dateRemote
                            + " - Expected: " + expDate);
                    if (!dateRemote.equals(expDate)) {
                        Log.log(Level.FINE, "Expiration dates do not match");
                        return false;
                    }
                }
            }
        }
        Log.log(Level.FINE, "All fields match. Returning true");
        return true;
    }

    public void acceptDeletion() {
        acceptDeletion.click();
    }

    public void cancelDeletion() {
        cancelDeletion.click();
    }

    public void close() {
        close.click();
    }
}