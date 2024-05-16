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

public class SearchShareePage extends CommonPage {

    @AndroidFindBy(id = "com.owncloud.android:id/search_src_text")
    private WebElement shareeUserName;

    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc=\"Back\"]")
    private WebElement back;

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
        //Clicking on screen... very bad but no other solution since results are presented
        //in another provider
        int startX = 500;
        int startY = 470;
        tap(startX, startY);
    }

    private void backListShares() {
        Log.log(Level.FINE, "Starts: Back to the list of shares");
        back.click();
    }
}