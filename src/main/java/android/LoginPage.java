package android;

import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.logging.Level;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.log.Log;

public class LoginPage extends CommonPage {

    @AndroidFindBy(id = "com.owncloud.android:id/hostUrlInput")
    private List<MobileElement> urlServer;

    @AndroidFindBy(id = "com.owncloud.android:id/embeddedCheckServerButton")
    private MobileElement checkServerButton;

    @AndroidFindBy(id = "com.owncloud.android:id/account_username")
    private MobileElement userNameText;

    @AndroidFindBy(id = "com.owncloud.android:id/account_password")
    private MobileElement passwordText;

    @AndroidFindBy(id = "com.owncloud.android:id/loginButton")
    private MobileElement loginButton;

    @AndroidFindBy(id = "ok")
    private MobileElement acceptCertificate;

    //For the regular tests
    private final String server = System.getProperty("server");

    private String errorcredentialstext_xpath = "//*[@text='Wrong username or password']";

    public LoginPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public boolean notLoggedIn() {
        return !urlServer.isEmpty();
    }

    public void typeURL() {
        Log.log(Level.FINE, "Starts: Type URL.");
        urlServer.get(0).sendKeys(server);
        checkServerButton.click();
    }

    public void typeCredentials(String username, String password) {
        Log.log(Level.FINE, "Starts: Type credentials: username: "
                + username + " - password: " + password);
        acceptCertificate();
        userNameText.sendKeys(username);
        passwordText.sendKeys(password);
    }

    public void submitLogin(/*String method*/) {
        Log.log(Level.FINE, "Starts: Submit login");
        acceptCertificate();
        loginButton.click();
    }

    public void acceptCertificate(){
        if (!findListUIAutomatorText("YES").isEmpty()){
            findListUIAutomatorText("YES").get(0).click();
        }
    }
}
