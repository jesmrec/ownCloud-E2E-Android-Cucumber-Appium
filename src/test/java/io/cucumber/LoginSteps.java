package io.cucumber;

import android.LoginPage;

import java.util.logging.Level;

import io.cucumber.java.en.Given;
import utils.LocProperties;
import utils.log.Log;

public class LoginSteps {

    //Involved pages
    protected LoginPage loginPage = new LoginPage();

    public LoginSteps() {
    }

    @Given("user {word} is logged")
    public void user_is_logged(String user) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        // Server and app MUST work with basic auth mode. Both required.
        if (loginPage.notLoggedIn()) {
            String username = LocProperties.getProperties().getProperty("userName1");
            String password = LocProperties.getProperties().getProperty("passw1");
            loginPage.typeURL();
            loginPage.typeCredentials(username, password);
            loginPage.submitLogin();
        }
    }
}
