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
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            String name = rows.get(0);
            String subtitle = rows.get(1);
            world.graphAPI.createSpace(name, subtitle, userName);
        }
    }

    @When("Alice selects the spaces view")
    public void user_selects_spaces_view() {
        StepLogger.logCurrentStep(Level.FINE);
        world.fileListPage.openSpaces();
    }

    @When("following space is disabled in server")
    public void space_disabled_server(DataTable table)
            throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            String name = rows.get(0);
            String subtitle = rows.get(1);
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

    @When("Alice edits the image of the space {word} with the file {word}")
    public void edits_image(String spaceName, String fileName) {
        StepLogger.logCurrentStep(Level.FINE);
        world.spacesPage.openEditSpaceImage(spaceName);
        world.documentProviderPage.selectFileToUpload(fileName);
    }

    @Then("Alice should{typePosNeg} see the following{spaceStatus} spaces")
    public void user_should_see_following_spaces(String sense, String spaceState, DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        List<List<String>> listItems = table.asLists();
        if (sense.isEmpty()){
            assertTrue(world.spacesPage.areAllSpacesVisible(listItems, spaceState));
        } else if (sense.equals(" not")) {
            assertFalse(world.spacesPage.areAllSpacesVisible(listItems, spaceState));
        }
    }

    @Then("space should be created/updated in server with the following fields")
    public void spaces_created_in_server(DataTable table) throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        // Spaces in scenario definition
        List<List<String>> listItems = table.asLists();
        boolean matches = true;
        String name = listItems.get(0).get(1);
        // Description can be null
        String description = listItems.get(1).get(1) != null ? listItems.get(1).get(1) : "";
        String quota = listItems.get(2).get(1);
        String unit = listItems.get(3).get(1);
        Log.log(Level.FINE, "Space from scenario: " + name + " " + description + " " + quota);
        // Spaces in server
        List<OCSpace> spaces = world.graphAPI.getMySpaces();
        for (OCSpace space : spaces) {
            // Check if space in server matches with space in scenario definition
            Log.log(Level.FINE, "Space in server: " + space.getName() + " "
                    + space.getDescription() + " " + space.getQuota(unit));
            if (!(space.getName().equals(name)
                    && space.getDescription().equals(description)
                    && space.getQuota(unit).equals(quota))) {
                matches = false;
                break;
            }
        }
        // Check if all spaces in scenario definition match with spaces in server
        assertTrue(matches);
    }

    @Then("space image should be updated in server with file {word}")
    public void space_image_updated(String fileName, DataTable table) throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        List<List<String>> listItems = table.asLists();
        String spaceName = listItems.get(0).get(0);
        String spaceSubtitle = listItems.get(0).get(1);
        String id = world.graphAPI.getSpaceIdFromName(spaceName, spaceSubtitle);
        assertTrue(world.filesAPI.itemExist(id ,"/.space/"+fileName));
    }

    @Then("quota is correctly displayed")
    public void quota_displayed(DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        List<List<String>> listItems = table.asLists();
        String value = listItems.get(0).get(0);
        String unit = listItems.get(0).get(1);
        String spaceName = listItems.get(0).get(2);
        user_edit_space(spaceName);
        assertTrue(world.spacesPage.isQuotaDisplayed(value, unit));
    }
}
