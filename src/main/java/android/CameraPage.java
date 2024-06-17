package android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.logging.Level;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.log.Log;

public class CameraPage extends CommonPage {

    @AndroidFindBy(id = "android:id/ok")
    private List<WebElement> gotIt;

    @AndroidFindBy(id = "com.android.camera2:id/shutter_button")
    private WebElement shutterButton;

    @AndroidFindBy(id = "com.google.android.GoogleCamera:id/shutter_button")
    private WebElement shutterButtonLegacy;

    @AndroidFindBy(id = "com.android.camera2:id/done_button")
    private WebElement doneButton;

    public CameraPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void takePicture() {
        Log.log(Level.FINE, "Starts: taking picture from camera");
        shutterButton.click();
        doneButton.click();
    }
}
