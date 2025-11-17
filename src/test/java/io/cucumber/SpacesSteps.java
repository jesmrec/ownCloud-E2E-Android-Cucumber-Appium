/**
 * ownCloud Android Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package io.cucumber;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.entities.OCSpace;
import utils.log.Log;
import utils.log.StepLogger;

public class SpacesSteps {

    private World world;

    public SpacesSteps(World world) {
        this.world = world;
    }

    @ParameterType("(?: (enabled|disabled))?")
    public String spaceStatus(String s) {
        return s == null ? "" : s;
    }

    @Given("the following spaces have been created in {word} account")
    public void spaces_have_been_created(String userName, DataTable table) throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String name = row.get("name");
            String subtitle = row.get("subtitle");
            world.graphAPI.createSpace(name, subtitle, userName);
        }
    }

    @When("Alice selects the spaces view")
    public void user_selects_spaces_view() {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListPage.openSpaces();
    }

    @When("the following spaces are disabled in server")
    public void space_disabled_server(DataTable table) throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String name = row.get("name");
            String subtitle = row.get("subtitle");
            world.graphAPI.disableSpace(name, subtitle);
        }
    }

    @When("Alice filters the list using {word}")
    public void user_filters_list(String pattern) {
        StepLogger.logCurrentStep(Level.FINE);
        world.spacesPage.typeSearch(pattern);
    }

    @When("Alice creates a new space with the following fields")
    public void creates_new_space(DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        handleSpace(table, "create");
    }

    @When("Alice edits the space {word}")
    public void user_edit_space(String spaceName){
        StepLogger.logCurrentStep(Level.FINE);
        world.spacesPage.openEditSpace(spaceName);
    }

    @When("Alice disables the space {word}")
    public void user_disables_space(String spaceName){
        StepLogger.logCurrentStep(Level.FINE);
        world.spacesPage.openDisableSpace(spaceName);
    }

    @When("Alice enables the space {word}")
    public void user_enables_space(String spaceName){
        StepLogger.logCurrentStep(Level.FINE);
        world.spacesPage.openEnableSpace(spaceName);
    }

    @When("Alice deletes the space {word}")
    public void user_deletes_space(String spaceName){
        StepLogger.logCurrentStep(Level.FINE);
        world.spacesPage.openDeleteSpace(spaceName);
    }

    @When("Alice updates the space with the following fields")
    public void updates_new_space(DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        handleSpace(table, "update");
    }

    @When("Alice edits the image of the space {word} with the file {word}")
    public void edits_image(String spaceName, String fileName) {
        StepLogger.logCurrentStep(Level.FINE);
        world.spacesPage.openEditSpaceImage(spaceName);
        world.documentProviderPage.selectFileToUpload(fileName);
    }

    @Then("Alice should{typePosNeg} see the following spaces")
    public void user_should_see_following_spaces(String sense, DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String name = row.get("name");
            String subtitle = row.get("subtitle");
            Log.log(Level.FINE, "Checking sense: " + sense + " for space: " + name + " " + subtitle);
            if (sense.isEmpty()) { // positive case
                assertTrue(world.spacesPage.isSpaceDisplayed(name, subtitle));
            } else if (sense.equals(" not")) { // negative case
                assertFalse(world.spacesPage.isSpaceDisplayed(name, subtitle));
            }
        }
    }

    @Then("the space should be created/updated in server with the following fields")
    public void spaces_created_in_server(DataTable table) throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        // Spaces in scenario definition
        Map<String, String> fields = table.asMap(String.class, String.class);
        boolean matches = true;
        String name = fields.get("name");
        // Description can be null
        String subtitle = fields.get("subtitle") != null ? fields.get("subtitle") : "";
        String quota = fields.get("quota");
        String unit = fields.get("unit");
        Log.log(Level.FINE, "Space from scenario: " + name + " " + subtitle + " " + quota);
        // Spaces in server
        List<OCSpace> spaces = world.graphAPI.getMySpaces();
        for (OCSpace space : spaces) {
            // Check if space in server matches with space in scenario definition
            Log.log(Level.FINE, "Space in server: " + space.getName() + " "
                    + space.getDescription() + " " + space.getQuota(unit));
            if (!(space.getName().equals(name)
                    && space.getDescription().equals(subtitle)
                    && space.getQuota(unit).equals(quota))) {
                matches = false;
                break;
            }
        }
        // Check if all spaces in scenario definition match with spaces in server
        assertTrue(matches);
    }

    @Then("the space image should be updated in server with file {word}")
    public void space_image_updated(String fileName, DataTable table) throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String name = row.get("name");
            String subtitle = row.get("subtitle");
            String id = world.graphAPI.getSpaceIdFromName(name, subtitle);
            assertTrue(world.filesAPI.itemExist(id, "/.space/" + fileName));
        }
    }

    @Then("the quota is correctly displayed")
    public void quota_displayed(DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        Map<String, String> values = table.asMap(String.class, String.class);
        String spaceName = values.get("name");
        String quota = values.get("quota");
        String unit = values.get("unit");
        user_edit_space(spaceName);
        assertTrue(world.spacesPage.isQuotaDisplayed(quota, unit));
    }

    private void handleSpace(DataTable table, String operation){
        Map<String, String> data = table.asMap(String.class, String.class);
        String name = data.get("name");
        String subtitle = data.get("subtitle")!=null ? data.get("subtitle") : "";
        String quota = data.get("quota");
        if (operation.equals("update")) {
            world.spacesPage.editSpace(name, subtitle, quota);
        } else if (operation.equals("create")) {
            world.spacesPage.createSpace(name, subtitle, quota);
        }
    }
}
