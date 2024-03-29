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

    @AndroidFindBy(id = "android:id/button1")
    private WebElement buttonYes;

    @AndroidFindBy(id = "android:id/button2")
    private WebElement buttonLocal;

    @AndroidFindBy(id = "android:id/button3")
    private WebElement buttonNo;

    public RemoveDialogPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void removeAll() {
        Log.log(Level.FINE, "Starts: Remove from server and local");
        buttonYes.click();
    }

    public void dontRemove() {
        Log.log(Level.FINE, "Starts: Cancel Remove");
        buttonNo.click();
    }

    public void onlyLocal() {
        Log.log(Level.FINE, "Starts: Remove only from local");
        buttonLocal.click();
    }

}
