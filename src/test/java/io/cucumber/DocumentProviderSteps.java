package io.cucumber;

import java.util.logging.Level;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import utils.log.Log;

public class DocumentProviderSteps {

    private World world;

    public DocumentProviderSteps(World world) {
        this.world = world;
    }

    @Given("a file {word} exists in the device")
    public void a_file_exists_in_device(String fileName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.devicePage.pushFile(fileName);
    }

    @When("Alice selects {word} to upload")
    public void user_selects_file_to_upload(String fileName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.documentProviderPage.selectFileToUpload(fileName);
    }
}
