/**
 * ownCloud Android Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package io.cucumber;

import java.util.logging.Level;

import io.cucumber.java.en.Given;
import utils.LocProperties;
import utils.log.Log;

public class LoginSteps {

    private World world;

    public LoginSteps(World world) {
        this.world = world;
    }

    @Given("user {word} is logged")
    public void user_is_logged(String user) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        // Server and app MUST work with basic auth mode.
        if(!world.fileListPage.isFileListVisible()) {
            String username = LocProperties.getProperties().getProperty("userName1");
            String password = LocProperties.getProperties().getProperty("passw1");
            world.loginPage.typeURL();
            world.loginPage.typeCredentials(username, password);
            world.loginPage.submitLogin();
        }
    }
}
