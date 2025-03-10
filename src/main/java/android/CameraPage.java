package android;

import org.openqa.selenium.Dimension;
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

    @AndroidFindBy(id = "com.android.camera2:id/done_button")
    private WebElement doneButton;

    public static CameraPage instance;

    private CameraPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public static CameraPage getInstance() {
        if (instance == null) {
            instance = new CameraPage();
        }
        return instance;
    }

    public void takePicture() {
        Log.log(Level.FINE, "Starts: taking picture from camera");
        String cameraViewId = "com.android.camera2:id/activity_root_view";
        waitById(WAIT_TIME, cameraViewId);
        clickShutterByCoordinate();
        doneButton.click();
    }

    private void clickShutterByCoordinate() {
        Log.log(Level.FINE, "Starts: Clicking on shutter coordinate");
        //Clicking in the shutter by coordinate because id changes in Android emus and versions
        Dimension size = driver.manage().window().getSize();
        double X = (double) (size.width) / 2;
        double Y = (double) (size.height) * 0.90;
        tap((int) X, (int) Y);
    }
}
