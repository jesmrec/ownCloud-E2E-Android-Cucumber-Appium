package android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.log.Log;

public class ShortcutDialogPage extends CommonPage {

    @AndroidFindBy(id = "com.owncloud.android:id/create_shortcut_dialog_url_value")
    private WebElement shortcutURL;

    @AndroidFindBy(id = "com.owncloud.android:id/create_shortcut_dialog_name_file_value")
    private WebElement shortcutName;

    @AndroidFindBy(id = "com.owncloud.android:id/createButton")
    private WebElement submitShortcut;

    @AndroidFindBy(id = "android:id/button1")
    private WebElement openShortcut;

    @AndroidFindBy(id = "com.android.chrome:id/fre_main_layout")
    private WebElement browser;

    public ShortcutDialogPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void typeURLName(String name, String url) {
        Log.log(Level.FINE, "Starts: enter shortcut fields: " + name + " " + url);
        shortcutURL.sendKeys(url);
        shortcutName.sendKeys(name);
    }

    public void submitShortcut() {
        Log.log(Level.FINE, "Starts: submit shortcut");
        submitShortcut.click();
    }

    public void openShortcut() {
        Log.log(Level.FINE, "Starts: open shortcut");
        openShortcut.click();
    }

    public boolean isBrowserOpen() {
        Log.log(Level.FINE, "Starts: check if browser is open");
        return browser.isDisplayed();
    }
}
