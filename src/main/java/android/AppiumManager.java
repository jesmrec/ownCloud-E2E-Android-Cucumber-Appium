/**
 * ownCloud Android Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 * <p>
 * Last Appium review: v2.0.1
 * If posible, execute tests with such version
 */


package android;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.logging.Level;

import io.appium.java_client.android.AndroidDriver;
import utils.LocProperties;
import utils.log.Log;

public class AppiumManager {

    private static final String driverDefect = LocProperties.getProperties().getProperty("appiumURL");
    private static final String driverURL = System.getProperty("appium");
    private static final String device = System.getProperty("device");
    private static AppiumManager appiumManager;
    private static AndroidDriver driver;
    private static File app;

    private AppiumManager() {
        init();
    }

    private static void init() {

        File rootPath = new File(System.getProperty("user.dir"));
        File appDir = new File(rootPath, "src/test/resources");
        app = new File(appDir, LocProperties.getProperties().getProperty("apkName"));

        DesiredCapabilities capabilities = new DesiredCapabilities();
        setCapabilities(capabilities);

        try {
            if (!driverURL.isEmpty()) {
                Log.log(Level.FINE, "Appium driver located in: " + driverURL);
                driver = new AndroidDriver(new URL(driverURL), capabilities);
            } else {
                Log.log(Level.FINE, "Appium driver located in: " + driverDefect);
                driver = new AndroidDriver(new URL(driverDefect), capabilities);
            }
        } catch (MalformedURLException e) {
            Log.log(Level.SEVERE, "Driver could not be created: " + e.getMessage());
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        Log.log(Level.FINE, "Device: " +
                driver.getCapabilities().getCapability("deviceManufacturer") + " " +
                driver.getCapabilities().getCapability("deviceModel"));
        Log.log(Level.FINE, "Platform: " +
                driver.getCapabilities().getCapability("platformName") + " " +
                driver.getCapabilities().getCapability("platformVersion"));
        Log.log(Level.FINE, "API Level: " +
                driver.getCapabilities().getCapability("deviceApiLevel") + "\n");

        Log.log(Level.FINE, "Device UDID: " + device);

    }

    //Singletonize
    public static AppiumManager getManager() {
        if (appiumManager == null) {
            appiumManager = new AppiumManager();
        }
        return appiumManager;
    }

    //Check https://appium.io/docs/en/writing-running-appium/caps/
    private static void setCapabilities(DesiredCapabilities capabilities) {

        capabilities.setCapability("appium:deviceName", "test");
        capabilities.setCapability("appium:app", app.getAbsolutePath());
        capabilities.setCapability("appium:platformName", "Android");
        capabilities.setCapability("appium:automationName", "UIAutomator2");
        capabilities.setCapability("appium:appPackage",
                LocProperties.getProperties().getProperty("appPackage"));
        capabilities.setCapability("appium:appActivity",
                "com.owncloud.android.ui.activity.SplashActivity");
        capabilities.setCapability("appWaitPackage",
                LocProperties.getProperties().getProperty("appPackage"));
        capabilities.setCapability("autoGrantPermissions", true);
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);
        capabilities.setCapability("disableWindowAnimation", true);
        capabilities.setCapability("noReset", true);
        capabilities.setCapability("appium:newCommandTimeout", 60);
        if (device != null) {
            capabilities.setCapability("udid", device);
        }
    }

    public AndroidDriver getDriver() {
        return driver;
    }
}
