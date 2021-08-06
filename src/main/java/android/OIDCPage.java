package android;

import java.util.logging.Level;

import utils.log.Log;

public class OIDCPage extends CommonPage {

    private long deviceVersion;
    private boolean realDevice;
    private String device = System.getProperty("device");

    //Real device & emulator, Android >= 10
    private String username_xpath_10 = "/hierarchy/android.widget.FrameLayout/android.widget." +
            "LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget." +
            "FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget." +
            "FrameLayout[2]/android.webkit.WebView/android.view.View/android.view.View[1]/android.view." +
            "View/android.widget.EditText[1]";

    //Real device Android <= 10
    private String username_xpath_real = "/hierarchy/android.widget.FrameLayout/android.widget." +
            "LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget." +
            "FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget." +
            "FrameLayout[1]/android.webkit.WebView/android.view.View/android.view.View[1]/android.view." +
            "View/android.widget.EditText[1]";

    // Emulator Android < 10
    private String username_xpath_emu = "android.webkit.WebView[@content-desc=\"Sign in — ownCloud\"]/android." +
            "view.View/android.view.View[1]/android.view.View/android.view.View[1]/android.widget.EditText[1]";

    // Real device & emulator Android >= 10
    private String password_xpath_10 = "/hierarchy/android.widget.FrameLayout/android.widget." +
            "LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget." +
            "FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget." +
            "FrameLayout[2]/android.webkit.WebView/android.view.View/android.view.View[1]/android.view." +
            "View/android.widget.EditText[2]";

    // Real device Android < 10
    private String password_xpath_real = "/hierarchy/android.widget.FrameLayout/android.widget." +
            "LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget." +
            "FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget." +
            "FrameLayout[1]/android.webkit.WebView/android.view.View/android.view.View[1]/android.view." +
            "View/android.widget.EditText[2]";

    // Emulator Android < 10
    private String password_xpath_emu = "//android.webkit.WebView[@content-desc=\"Sign in — ownCloud\"]/android" +
            ".view.View/android.view.View[1]/android.view.View/android.widget.EditText[2]";

    // Real device & emulator Android >= 10
    private String acceptButton_xpath_10 = "/hierarchy/android.widget.FrameLayout/android.widget." +
            "LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget." +
            "FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget." +
            "FrameLayout[2]/android.webkit.WebView/android.view.View/android.view.View[1]/android.view." +
            "View/android.view.View/android.widget.Button";

    // Real device Android < 10
    private String acceptButton_xpath_real = "/hierarchy/android.widget.FrameLayout/android.widget." +
            "LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget." +
            "FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget." +
            "FrameLayout[1]/android.webkit.WebView/android.view.View/android.view.View[1]/android.view." +
            "View/android.view.View/android.widget.Button";

    // Emulator Android < 10
    private String acceptButton_xpath_emu = "//android.widget.Button[@content-desc=\"Log in \"]";

    // Real device & emulator Android >= 10
    private String authorize_xpath_10 = "/hierarchy/android.widget.FrameLayout/android.widget." +
            "LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget." +
            "FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget." +
            "FrameLayout[2]/android.webkit.WebView/android.view.View/android.view.View[1]/android.view." +
            "View[5]/android.view.View[2]/android.widget.Button";

    // Real device Android < 10
    private String authorize_xpath_real = "/hierarchy/android.widget.FrameLayout/android.widget." +
            "LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget." +
            "FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget." +
            "FrameLayout[1]/android.webkit.WebView/android.view.View/android.view.View[1]/android.view." +
            "View[5]/android.view.View[2]/android.widget.Button";

    // Emulator Android < 10
    private String authorize_xpath_emu = "//android.widget.Button[@content-desc=\"Allow \"]";


    public OIDCPage() {
        deviceVersion = (long) driver.getCapabilities().getCapability("deviceApiLevel");
        if (device == null) {
            device = "emulator";
        }
        realDevice = device.contains("emulator") ? false : true;
        Log.log(Level.FINE, "Real device?: " + realDevice);
    }

    public void enterCredentials(String username, String password) {
        Log.log(Level.FINE, "Starts: enter OIDC credentials");
        if (deviceVersion >= 29) {
            Log.log(Level.FINE, "Android 10");
            if (!driver.findElementsByXPath(username_xpath_10).isEmpty()) {
                Log.log(Level.FINE, "Entering credentials");
                findXpath(username_xpath_10).sendKeys(username);
                findXpath(password_xpath_10).sendKeys(password);
                findXpath(acceptButton_xpath_10).click();
            }
        } else {
            Log.log(Level.FINE, "Android < 10");
            if (realDevice) {
                if (!driver.findElementsByXPath(username_xpath_real).isEmpty()) {
                    Log.log(Level.FINE, "Entering credentials in real device");
                    findXpath(username_xpath_real).sendKeys(username);
                    findXpath(password_xpath_real).sendKeys(password);
                    findXpath(acceptButton_xpath_real).click();
                }
            } else {  //running emulator
                if (!driver.findElementsByXPath(username_xpath_emu).isEmpty()) {
                    Log.log(Level.FINE, "Entering credentials emulator");
                    findXpath(username_xpath_emu).sendKeys(username);
                    findXpath(password_xpath_emu).sendKeys(password);
                    findXpath(acceptButton_xpath_emu).click();
                }
            }
        }
    }

    public void authorize() {
        Log.log(Level.FINE, "Starts: Authorize OIDC");
        if (deviceVersion >= 29) {
            Log.log(Level.FINE, "Android 10");
            findXpath(authorize_xpath_10).click();
        } else {
            Log.log(Level.FINE, "Android < 10");
            if (realDevice) {
                findXpath(authorize_xpath_real).click();
            } else { //running emulator
                findXpath(authorize_xpath_emu).click();
            }
        }
    }
}
