package io.cucumber;

import java.util.logging.Level;

import io.cucumber.java.en.Then;
import utils.log.Log;

public class UploadSteps {

    private World world;

    public UploadSteps(World world) {
        this.world = world;
    }

    @Then("Alice should see {word} as {word} in the uploads view")
    public void file_in_uploads(String fileName, String status) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().openUploadsView();
        if (status.equals("uploaded")) {
            world.getUploadsPage().isFileUploaded(fileName);
        }
        world.getUploadsPage().clearList();
    }

}
