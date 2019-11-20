package android;

import org.openqa.selenium.interactions.Actions;

import io.appium.java_client.android.AndroidDriver;

public class CommonPage {

    protected AndroidDriver driver;
    protected Actions actions;

    public CommonPage()  {
        AppiumManager manager = AppiumManager.getManager();
        driver = manager.getDriver();
        actions = new Actions(driver);
    }
}
