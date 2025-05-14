package android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.log.Log;

public class ConflictPage extends CommonPage {

    @AndroidFindBy(id = "android:id/button1")
    private WebElement localVersionButton;

    @AndroidFindBy(id = "android:id/button2")
    private WebElement serverVersionButton;

    @AndroidFindBy(id = "android:id/button3")
    private WebElement bothVersionButton;

    public static ConflictPage instance;

    private ConflictPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public static ConflictPage getInstance() {
        if (instance == null) {
            instance = new ConflictPage();
        }
        return instance;
    }

    public boolean isConflictPageDisplayed() {
        return isButtonVisibleWithText(localVersionButton, "LOCAL VERSION") &&
                isButtonVisibleWithText(serverVersionButton, "SERVER VERSION") &&
                isButtonVisibleWithText(bothVersionButton, "KEEP BOTH");
    }

    private boolean isButtonVisibleWithText(WebElement button, String expectedText) {
        return button.isDisplayed() && button.getText().contains(expectedText);
    }

    public void fixConflict (String conflict){
        switch (conflict){
            case "local" -> selectLocalVersion();
            case "server" -> selectServerVersion();
            case "keep both" -> selectBothVersions();
        }
    }


    private void selectLocalVersion() {
        Log.log(Level.FINE, "Starts: Fix conflict with local version");
        localVersionButton.click();
    }

    private void selectServerVersion() {
        Log.log(Level.FINE, "Starts: Fix conflict with server version");
        serverVersionButton.click();
    }

    private void selectBothVersions() {
        Log.log(Level.FINE, "Starts: Fix conflict by keeping both versions");
        bothVersionButton.click();
    }
}
