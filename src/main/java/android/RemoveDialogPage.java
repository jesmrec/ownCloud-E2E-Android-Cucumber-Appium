/**
 * ownCloud Android Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.log.Log;

public class RemoveDialogPage extends CommonPage {

    @AndroidFindBy(id = "com.owncloud.android:id/dialog_remove_yes")
    private WebElement buttonYesFiles;

    @AndroidFindBy(id = "com.owncloud.android:id/dialog_remove_local_only")
    private WebElement buttonLocalFiles;

    @AndroidFindBy(id = "com.owncloud.android:id/dialog_remove_no")
    private WebElement buttonNoFiles;

    @AndroidFindBy(id = "android:id/button1")
    private WebElement buttonYesFolders;

    @AndroidFindBy(id = "android:id/button2")
    private WebElement buttonLocalFolders;

    @AndroidFindBy(id = "android:id/button3")
    private WebElement buttonNoFolders;

    public RemoveDialogPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void removeAllFiles() {
        Log.log(Level.FINE, "Starts: Remove from server and local");
        buttonYesFiles.click();
    }

    public void dontRemoveFiles() {
        Log.log(Level.FINE, "Starts: Cancel Remove");
        buttonNoFiles.click();
    }

    public void onlyLocalFiles() {
        Log.log(Level.FINE, "Starts: Remove only from local");
        buttonLocalFiles.click();
    }

    public void removeAllFolders() {
        Log.log(Level.FINE, "Starts: Remove from server and local");
        buttonYesFolders.click();
    }

    public void dontRemoveFolders() {
        Log.log(Level.FINE, "Starts: Cancel Remove");
        buttonNoFolders.click();
    }

    public void onlyLocalFolders() {
        Log.log(Level.FINE, "Starts: Remove only from local");
        buttonLocalFolders.click();
    }

}
