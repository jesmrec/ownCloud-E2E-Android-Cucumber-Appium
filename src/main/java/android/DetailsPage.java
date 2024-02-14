/**
 * ownCloud Android Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.logging.Level;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.log.Log;

public class DetailsPage extends CommonPage {

    @AndroidFindBy(id = "com.owncloud.android:id/lytName")
    private WebElement thumbnail;

    @AndroidFindBy(id = "com.owncloud.android:id/fdFilename")
    private WebElement itemName;

    @AndroidFindBy(id = "com.owncloud.android:id/fdType")
    private WebElement itemType;

    @AndroidFindBy(id = "com.owncloud.android:id/fdSize")
    private WebElement itemSize;

    @AndroidFindBy(id = "com.owncloud.android:id/fdProgressText")
    private WebElement downloading;

    @AndroidFindBy(id = "com.owncloud.android:id/text_preview")
    private WebElement textPreview;

    @AndroidFindBy(id = "com.owncloud.android:id/visual_area")
    private WebElement visualArea;

    @AndroidFindBy(id = "com.owncloud.android:id/media_controller")
    private WebElement mediaControls;

    @AndroidFindBy(id = "com.owncloud.android:id/photo_view")
    private WebElement photoPreview;

    @AndroidFindBy(id = "com.owncloud.android:id/video_player")
    private WebElement videoPreview;

    @AndroidFindBy(id = "toolbar")
    private List<WebElement> toolbar;

    @AndroidFindBy(id = "android:id/contentPanel")
    private WebElement contentPanel;

    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]")
    private WebElement navigateUp;

    public DetailsPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public String getName() {
        return itemName.getText();
    }

    public String getType() {
        return itemType.getText();
    }

    public String getSize() {
        return itemSize.getText();
    }

    public void backListFiles() {
        Log.log(Level.FINE, "Start: Back to the list of files");
        navigateUp.click();
    }

    public void closeOpenIn() {
        Log.log(Level.FINE, "Start: Close Open In");
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
    }

    public boolean itemPreviewed() {
        return textPreview.isDisplayed();
    }

    public boolean imagePreviewed() {
        return photoPreview.isDisplayed();
    }

    public boolean audioPreviewed() {
        boolean isArtDisplayed = visualArea.isDisplayed();
        boolean areControlsDisplayed = mediaControls.isDisplayed();
        return isArtDisplayed && areControlsDisplayed;
    }

    public boolean videoPreviewed() {
        return videoPreview.isDisplayed();
    }

    public void displayControls() {
        photoPreview.click();
    }

    public void removeShareSheet() {
        if (toolbar.isEmpty()) {
            driver.navigate().back();
        }
    }

    public void downloadFromThumbnail() {
        thumbnail.click();
    }

    public boolean textInFile(String text) {
        return findUIAutomatorText(text).isDisplayed();
    }

    public boolean shareSheetDisplayed(String itemName) {
        return contentPanel.isDisplayed();
    }
}