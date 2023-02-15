/**
 * ownCloud Android Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package io.cucumber;

import android.AppiumManager;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.xml.parsers.ParserConfigurationException;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.entities.OCFile;
import utils.log.Log;

public class Hooks {

    private World world;

    public Hooks (World world){
        this.world = world;
    }

    @Before
    public void setup(Scenario scenario) {
        Log.log(Level.FINE, "START SCENARIO EXECUTION: " + scenario.getName());
        AppiumManager.getManager().getDriver().launchApp();
    }

    @After
    public void tearDown(Scenario scenario)
            throws IOException, ParserConfigurationException, SAXException {
        AppiumManager.getManager().getDriver().closeApp();
        cleanUp();
        Log.log(Level.FINE, "END SCENARIO EXECUTION: " + scenario.getName() + "\n\n");
    }

    private void cleanUp()
            throws IOException, ParserConfigurationException, SAXException {
        //First, remove leftovers in root folder. Just keeping the skeleton items
        ArrayList<OCFile> filesRoot = world.filesAPI.listItems("");
        for (OCFile iterator: filesRoot){
            Log.log(Level.FINE, "CLEANUP: removing " + iterator.getName());
            world.filesAPI.removeItem(iterator.getName());
        }
        world.trashbinAPI.emptyTrashbin();
        if (world.authAPI.checkAuthMethod().equals("OIDC")){ //remove spaces
            world.graphAPI.removeSpacesOfUser();
        }
    }
}
