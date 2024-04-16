/**
 * ownCloud Android Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package io.cucumber;

import android.AndroidManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.util.logging.Level;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import utils.LocProperties;
import utils.log.Log;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"})

public class RunCucumberTest {

    @BeforeClass
    public static void beforeclass() {
        Log.init();
        Log.log(Level.FINE, "START EXECUTION\n");
    }

    @AfterClass
    public static void afterclass() {
        //remove the oC app
        AndroidManager.getDriver().removeApp(
                LocProperties.getProperties().getProperty("appPackage"));
        //remove Appium Settings
        AndroidManager.getDriver().removeApp("io.appium.settings");
        AndroidManager.getDriver().quit();
        Log.log(Level.FINE, "END EXECUTION\n");
    }
}
