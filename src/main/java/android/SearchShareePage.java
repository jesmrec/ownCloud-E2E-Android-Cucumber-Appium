/**
 * ownCloud Android Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package android;

import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.offset.PointOption;
import utils.log.Log;

public class SearchShareePage extends CommonPage {

    @AndroidFindBy(id = "com.owncloud.android:id/search_src_text")
    private MobileElement shareeUserName;

    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]")
    private MobileElement navigateUp;

    public SearchShareePage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void shareWithUser(String sharee) {
        Log.log(Level.FINE, "Starts: Share with user: " + sharee);
        shareeUserName.sendKeys(sharee);
        selectShareeFromList(sharee);
        backListShares();
    }

    private void selectShareeFromList(String sharee) {
        wait(1);
        TouchAction selectSharee = new TouchAction(driver);
        //Clicking on screen... very bad but no other solution since results are presented
        //in another provider
        selectSharee.tap(PointOption.point(500, 470)).perform();
    }

    private void backListShares() {
        Log.log(Level.FINE, "Starts: Back to the list of shares");
        navigateUp.click();
    }
}