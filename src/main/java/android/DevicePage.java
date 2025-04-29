package android;

import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.LocProperties;
import utils.log.Log;

public class DevicePage extends CommonPage {

    public static DevicePage instance;

    private DevicePage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public static DevicePage getInstance() {
        if (instance == null) {
            instance = new DevicePage();
        }
        return instance;
    }

    public void cleanUpDevice() {
        Log.log(Level.FINE, "Starts: Clean up device, owncloud folder");
        // Remove owncloud folder from device
        Map<String, Object> args = new HashMap<>();
        args.put("command", "rm");
        args.put("args", Arrays.asList("-rf", "sdcard/Download/owncloud/"));
        driver.executeScript("mobile: shell", args);
    }

    public void pushFile(String itemName) {
        Log.log(Level.FINE, "Starts: push file: " + itemName);
        File rootPath = new File(System.getProperty("user.dir"));
        File appDir = new File(rootPath, "src/test/resources");
        File app = new File(appDir, "io/cucumber/example-files/" + itemName);
        try {
            driver.pushFile("/sdcard/Download/" + itemName, app);
            Log.log(Level.FINE, "File " + itemName + " pushed");
        } catch (IOException e) {
            Log.log(Level.SEVERE, "IO Exception: " + e.getMessage());
        }
    }

    public String pullList(String folderId) {
        Log.log(Level.FINE, "Starts: pull file from: " + folderId);
        Map<String, Object> args = new HashMap<>();

        String downloadFolder = "sdcard/Download/owncloud";
        String user = LocProperties.getProperties().getProperty("userName1").toLowerCase();
        String server = System.getProperty("server")
                .replaceFirst("^https?://", "")
                .replace(":", "%3A" );
        //String server = System.getProperty("server").substring(8); // Remove "https://"
        String target = downloadFolder + "/" + user + "@" + server  + "/" + folderId;
        Log.log(Level.FINE, "Command args to execute: " + target);
        args.put("command", "ls");
        args.put("args", List.of(target));

        String output = (String) driver.executeScript("mobile: shell", args);
        Log.log(Level.FINE, "List of files in given folder: " + output);
        return output;
    }

}
