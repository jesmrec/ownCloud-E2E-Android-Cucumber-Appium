/**
 * ownCloud Android Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package io.cucumber;

import android.AndroidManager;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.xml.parsers.ParserConfigurationException;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.LocProperties;
import utils.entities.OCFile;
import utils.log.Log;

public class Hooks {

    private World world;

    public Hooks(World world) {
        this.world = world;
    }

    @Before
    public void setup(Scenario scenario) {
        Log.log(Level.FINE, "START SCENARIO EXECUTION: " + scenario.getName());
        AndroidManager.getDriver().activateApp(
                LocProperties.getProperties().getProperty("appPackage"));
    }

    @After
    public void tearDown(Scenario scenario)
            throws IOException, ParserConfigurationException, SAXException {
        AndroidManager.getDriver().terminateApp(
                LocProperties.getProperties().getProperty("appPackage"));
        cleanUp();
        Log.log(Level.FINE, "END SCENARIO EXECUTION: " + scenario.getName() + "\n\n");
    }

    private void cleanUp()
            throws IOException, ParserConfigurationException, SAXException {
        //First, remove leftovers in root folder. Just keeping the skeleton items
        ArrayList<OCFile> filesRoot = world.getFilesAPI().listItems("");
        for (OCFile iterator : filesRoot) {
            Log.log(Level.FINE, "CLEANUP: removing " + iterator.getName());
            world.getFilesAPI().removeItem(iterator.getName());
        }
        world.getTrashbinAPI().emptyTrashbin();
        if (world.getAuthAPI().isOidc()) { //remove spaces
            world.getGraphAPI().removeSpacesOfUser();
        }
    }
}
