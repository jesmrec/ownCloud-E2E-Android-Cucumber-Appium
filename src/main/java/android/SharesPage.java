package android;

import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.log.Log;

public class SharesPage extends CommonPage {

    private String createbox_id = "com.owncloud.android:id/canEditCreateCheckBox";
    private String changebox_id = "com.owncloud.android:id/canEditChangeCheckBox";
    private String deletebox_id = "com.owncloud.android:id/canEditDeleteCheckBox";
    private String editbox_id = "com.owncloud.android:id/canEditSwitch";
    private String sharebox_id = "com.owncloud.android:id/canShareSwitch";
    private String closeButtonid = "com.owncloud.android:id/closeButton";

    public SharesPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public boolean isSharee(String user) {
        Log.log(Level.FINE, "Starts: Sharee in list: " + user);
        return findUIAutomatorText(user).isDisplayed();
    }

    public void switchCreate() {
        Log.log(Level.FINE, "Starts: Click create checkbox");
        findId(createbox_id).click();
    }

    public void switchChange() {
        Log.log(Level.FINE, "Starts: Click change checkbox");
        findId(changebox_id).click();
    }

    public void switchDelete() {
        Log.log(Level.FINE, "Starts: Click delete checkbox:");
        findId(deletebox_id).click();
    }

    public void switchShare() {
        Log.log(Level.FINE, "Starts: Switch share button");
        findId(sharebox_id).click();
    }

    public void switchEditFile() {
        Log.log(Level.FINE, "Starts: Switch edit button");
        findId(editbox_id).click();
    }

    public boolean isCreateSelected() {
        return checkSwitch(createbox_id);
    }

    public boolean isChangeSelected() {
        return checkSwitch(changebox_id);
    }

    public boolean isDeleteSelected() {
        return checkSwitch(deletebox_id);
    }

    public boolean isEditFileEnabled() {
        return checkSwitch(editbox_id);
    }

    public boolean isShareEnabled() {
        return parseIntBool(findId(sharebox_id).getAttribute("checked"));
    }

    public boolean isEditEnabled() {
        return parseIntBool(findId(editbox_id).getAttribute("checked"));
    }

    private boolean checkSwitch(String id) {
        if (!findListId(id).isEmpty()) {
            return parseIntBool(findId(id).getAttribute("checked"));
        } else {
            return false;
        }
    }

    public void close() {
        findId(closeButtonid).click();
    }
}
