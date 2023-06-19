/**
 * ownCloud Android Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package android;

import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.log.Log;

public class PrivateSharePage extends CommonPage {

    private final String createboxId = "com.owncloud.android:id/canEditCreateCheckBox";
    private final String changeboxId = "com.owncloud.android:id/canEditChangeCheckBox";
    private final String deleteboxId = "com.owncloud.android:id/canEditDeleteCheckBox";
    private final String editboxId = "com.owncloud.android:id/canEditSwitch";
    private final String shareboxId = "com.owncloud.android:id/canShareSwitch";

    @AndroidFindBy (id = createboxId)
    private MobileElement create;

    @AndroidFindBy (id = changeboxId)
    private MobileElement change;

    @AndroidFindBy (id = deleteboxId)
    private MobileElement delete;

    @AndroidFindBy (id = editboxId)
    private MobileElement edit;

    @AndroidFindBy (id = shareboxId)
    private MobileElement share;

    @AndroidFindBy (id = "com.owncloud.android:id/closeButton")
    private MobileElement close;

    public PrivateSharePage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public boolean isSharee(String user) {
        Log.log(Level.FINE, "Starts: Sharee in list: " + user);
        return findUIAutomatorText(user).isDisplayed();
    }

    public void switchCreate() {
        Log.log(Level.FINE, "Starts: Click create checkbox");
        //findId(createbox_id).click();
        create.click();
    }

    public void switchChange() {
        Log.log(Level.FINE, "Starts: Click change checkbox");
        //findId(changebox_id).click();
        change.click();
    }

    public void switchDelete() {
        Log.log(Level.FINE, "Starts: Click delete checkbox:");
        //findId(deletebox_id).click();
        delete.click();
    }

    public void switchShare() {
        Log.log(Level.FINE, "Starts: Switch share button");
        //findId(sharebox_id).click();
        share.click();
    }

    public void switchEditFile() {
        Log.log(Level.FINE, "Starts: Switch edit button");
        //findId(editbox_id).click();
        edit.click();
    }

    public boolean isCreateSelected() {
        return checkSwitch(createboxId);
    }

    public boolean isChangeSelected() {
        return checkSwitch(changeboxId);
    }

    public boolean isDeleteSelected() {
        return checkSwitch(deleteboxId);
    }

    public boolean isEditFileEnabled() {
        return checkSwitch(editboxId);
    }

    public boolean isShareEnabled() {
        return parseIntBool(findId(shareboxId).getAttribute("checked"));
    }

    public boolean isEditEnabled() {
        return parseIntBool(findId(editboxId).getAttribute("checked"));
    }

    private boolean checkSwitch(String id) {
        if (!findListId(id).isEmpty()) {
            return parseIntBool(findId(id).getAttribute("checked"));
        } else {
            return false;
        }
    }

    public void close() {
        close.click();
    }
}
