/**
 * ownCloud Android Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package android;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import utils.log.Log;

public class CommonPage {

    @AndroidFindBy(id = "com.owncloud.android:id/nav_all_files")
    private WebElement toRoot;

    protected static AndroidDriver driver = AppiumManager.getManager().getDriver();
    protected static Actions actions;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");

    public CommonPage() {
        actions = new Actions(driver);
    }

    /* Finders */

    public WebElement findId(String id) {
        return driver.findElement(AppiumBy.id(id));
    }

    public List<WebElement> findListId(String id) {
        return driver.findElements(AppiumBy.id(id));
    }

    public WebElement findXpath(String xpath) {
        return driver.findElement(AppiumBy.xpath(xpath));
    }

    public List<WebElement> findListXpath(String xpath) {
        return driver.findElements(AppiumBy.xpath(xpath));
    }

    public WebElement findUIAutomatorText(String text) {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"" + text + "\");"));
    }

    public WebElement findUIAutomatorSubText(String text) {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"" + text + "\");"));
    }

    public WebElement findUIAutomatorDescription(String description) {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().description(\"" + description + "\");"));
    }

    public List<WebElement> findListUIAutomatorText(String finder) {
        return driver.findElements(AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"" + finder + "\");"));
    }

    public WebElement findAccesibility(String id) {
        return driver.findElement(new AppiumBy.ByAccessibilityId(id));
    }

    public List<WebElement> findListAccesibility(String id) {
        return driver.findElements(new AppiumBy.ByAccessibilityId(id));
    }

    /* Waiters by different parameters */

    public static void waitByXpath(int timeToWait, String resourceXpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeToWait));
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath(resourceXpath)));
    }

    public static void waitById(int timeToWait, String resourceId) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeToWait));
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id(resourceId)));
    }

    public static void waitById(int timeToWait, WebElement mobileElement) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeToWait));
        wait.until(ExpectedConditions.visibilityOf(mobileElement));
    }

    public static void waitByIdInvisible(int timeToWait, String resourceId) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeToWait));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(AppiumBy.id(resourceId)));
    }

    public static void waitByIdInvisible(int timeToWait, WebElement mobileElement) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeToWait));
        wait.until(ExpectedConditions.invisibilityOf(mobileElement));
    }

    public static void waitByTextVisible(int timeToWait, String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeToWait));
        WebElement mobileElement = (WebElement)
                driver.findElement(AppiumBy.androidUIAutomator
                        ("new UiSelector().text(\"" + text + "\");"));
        wait.until(ExpectedConditions.textToBePresentInElement(mobileElement, text));
    }

    public void waitByTextInvisible(int timeToWait, String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeToWait));
        WebElement mobileElement = (WebElement)
                driver.findElement(AppiumBy.androidUIAutomator
                        ("new UiSelector().text(\"" + text + "\");"));
        wait.until(ExpectedConditions.invisibilityOfElementWithText(AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"Download enqueued\");"), "Download enqueued"));
    }

    // The following method should be used only in case implicit/explicit waits are not valid for the
    // scenario. Blocking the thread is not desirable and using it is not a good solution.
    public static void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected HashMap turnListToHashmap(List<List<String>> dataList) {
        HashMap<String, String> mapFields = new HashMap<String, String>();
        for (List<String> rows : dataList) {
            mapFields.put(rows.get(0), rows.get(1));
        }
        return mapFields;
    }

    /* Finger actions */

    public static void swipe(double startx, double starty, double endx, double endy) {
        Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height * starty);
        int endY = (int) (size.height * endy);
        int startX = (int) (size.width * startx);
        int endX = (int) (size.height * endx);
        TouchAction ts = new TouchAction(driver);
        ts.press(PointOption.point(startX, startY))
                .moveTo(PointOption.point(endX, endY)).release().perform();
    }

    public static void longPress(WebElement element) {
        AndroidTouchAction touch = new AndroidTouchAction(driver);
        touch.longPress(LongPressOptions.longPressOptions()
                .withElement(ElementOption.element(element))).perform();
    }

    /* Browsing methods used in several pages */

    /*
     * Receives: name of the folder in the current list to browse into
     */
    public void browseInto(String folderName) {
        Log.log(Level.FINE, "Starts: browse to " + folderName);
        findUIAutomatorText(folderName).click();
    }

    /*
     * Browses to root folder using the shortcut in the bottom bar
     */
    public void browseRoot() {
        Log.log(Level.FINE, "Starts: browse to root");
        toRoot.click();
    }

    /*
     * Receives: path to a folder. If path does not contain "/", folder is in root.
     * Otherwise browsing to.
     */
    public void browseToFolder(String path) {
        if (path.equals("/")) { //Go to Root
            browseRoot();
        } else if (path.contains("/")) { //browsing to the folder
            int i = 0;
            String[] route = path.split("/");
            for (i = 0; i < route.length; i++) {
                Log.log(Level.FINE, "browsing to " + route[i]);
                browseInto(route[i]);
            }
        } else { //no path to browse, just clicking
            browseInto(path);
        }
    }

    /*
     * Receives: path to a file. If path does not contain "/", file is in the root folder,
     * otherwise browsing to
     * Returns: File name (last chunk of the path), after browsing to reach it.
     */
    public String browseToFile(String path) {
        String[] route = path.split("/");
        int i = 0;
        if (route.length > 0) { //browse
            for (i = 0; i < route.length - 1; i++) {
                Log.log(Level.FINE, "browsing to " + route[i]);
                browseInto(route[i]);
            }
            Log.log(Level.FINE, "Returning: " + route[i]);
            return route[i];
        }
        return path;
    }

    protected boolean parseIntBool(String s) {
        return Boolean.parseBoolean(s);
    }

    /* Methods to help debugging */

    public static void takeScreenshot(String name) {
        try {
            String sd = sdf.format(new Timestamp(System.currentTimeMillis()).getTime());
            File screenShotFile = (driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenShotFile, new File("screenshots/" + name + "_" + sd + ".png"));
            Log.log(Level.FINE, "Take screenshot " + name + " at: " + sd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startRecording() {
        AndroidStartScreenRecordingOptions androidStartScreenRecordingOptions =
                new AndroidStartScreenRecordingOptions();
        androidStartScreenRecordingOptions.withBitRate(2000000);
        androidStartScreenRecordingOptions.withVideoSize("360x640");
        driver.startRecordingScreen(androidStartScreenRecordingOptions);
    }

    public static void stopRecording(String filename) {
        String base64String = driver.stopRecordingScreen();
        byte[] data = Base64.decodeBase64(base64String);
        String destinationPath = "video/" + filename + "_" +
                sdf.format(new Timestamp(System.currentTimeMillis()).getTime()) + ".mp4";
        Path path = Paths.get(destinationPath);
        try {
            Files.write(path, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}