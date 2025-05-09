package android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

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
}
