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
    private MobileElement shareeUsername;

    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]")
    private MobileElement navigateUp;

    public SearchShareePage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void shareWithUser(String sharee)
            throws InterruptedException {
        Log.log(Level.FINE, "Starts: Share with user: " + sharee);
        shareeUsername.sendKeys(sharee);
        selectShareeFromList(sharee);
        //Go back to Share Page
        backListShares();
    }

    private void selectShareeFromList(String sharee)
            throws InterruptedException {
        wait(1);
        TouchAction selectSharee = new TouchAction(driver);
        selectSharee.tap(PointOption.point(500, 470)).perform();
    }

    private void backListShares() {
        Log.log(Level.FINE, "Starts: Back to the list of shares");
        navigateUp.click();
    }
}