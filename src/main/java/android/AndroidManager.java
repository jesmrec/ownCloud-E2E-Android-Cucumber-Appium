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

public class AndroidManager {

    private static final String driverDefect = LocProperties.getProperties().getProperty("appiumURL");
    private static final String driverURL = System.getProperty("appium");
    private static final String device = System.getProperty("device");
    private static AndroidDriver driver = null;
    private static File app;

    private AndroidManager() {
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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));

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
    public static AndroidDriver getDriver() {
        if (driver == null) {
            init();
        }
        return driver;
    }

    //Check https://appium.io/docs/en/2.5/guides/caps/
    private static void setCapabilities(DesiredCapabilities capabilities) {

        capabilities.setCapability("appium:deviceName", "test");
        capabilities.setCapability("appium:app", app.getAbsolutePath());
        capabilities.setCapability("appium:platformName", "Android");
        capabilities.setCapability("appium:automationName", "UIAutomator2");
        capabilities.setCapability("appium:appPackage",
                LocProperties.getProperties().getProperty("appPackage"));
        capabilities.setCapability("appium:appActivity",
                "com.owncloud.android.ui.activity.SplashActivity");
        capabilities.setCapability("appium:appWaitPackage",
                LocProperties.getProperties().getProperty("appPackage"));
        capabilities.setCapability("appium:appWaitForLaunch","true");
        capabilities.setCapability("appium:autoGrantPermissions", true);
        capabilities.setCapability("appium:unicodeKeyboard", true);
        capabilities.setCapability("appium:resetKeyboard", true);
        capabilities.setCapability("appium:disableWindowAnimation", true);
        capabilities.setCapability("appium:noReset", true);
        capabilities.setCapability("appium:newCommandTimeout", 60);
        if (device != null) {
            capabilities.setCapability("appium:udid", device);
        }
        // Maximum time (in ms) to wait for the UiAutomator2 server to start on the device
        capabilities.setCapability("appium:uiautomator2ServerLaunchTimeout", 60000);

        // Maximum time (in ms) Appium will wait for an ADB command to complete
        // Useful in CI where emulator responsiveness might lag temporarily
        capabilities.setCapability("appium:adbExecTimeout", 60000);

        // Time (in ms) Appium waits after each UI operation before proceeding
        // Set to 0 to reduce test time and avoid unexpected delays in element detection
        capabilities.setCapability("appium:waitForIdleTimeout", 0);

        // Max time (in ms) allowed for installing the APK on the emulator
        // Increase for slow CI runners or large APKs
        capabilities.setCapability("appium:androidInstallTimeout", 90000);

        // Time (in ms) Appium will wait for the app to become idle after launching
        // Helps stabilize flaky launches in CI
        capabilities.setCapability("appium:appWaitDuration", 30000);

    }
}
