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
import io.cucumber.java.en.And;
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

    @And("Alice edits the space {word}")
    public void user_edit_space(String spaceName){
        StepLogger.logCurrentStep(Level.FINE);
        world.spacesPage.openSpaceMenu(spaceName);
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

    @Then("Alice should{typePosNeg} see the following spaces")
    public void user_should_see_following_spaces(String sense, DataTable table) {
        StepLogger.logCurrentStep(Level.FINE);
        List<List<String>> listItems = table.asLists();
        if (sense.isEmpty()){
            assertTrue(world.spacesPage.areAllSpacesVisible(listItems));
        } else if (sense.equals(" not")) {
            assertFalse(world.spacesPage.areAllSpacesVisible(listItems));
        }
    }

    @Then("space should be created/updated in server with the following fields")
    public void spaces_created_in_server(DataTable table) throws IOException {
        StepLogger.logCurrentStep(Level.FINE);
        // Spaces in scenario definition
        List<List<String>> listItems = table.asLists();
        // Spaces in server
        List<OCSpace> spaces = world.graphAPI.getMySpaces();
        boolean matches = true;
        String name = listItems.get(0).get(1);
        String description = listItems.get(1).get(1);
        String quota = listItems.get(2).get(1);
        Log.log(Level.FINE, "Space from scenario: " + name + " " + description + " " + quota);
        Log.log(Level.FINE, "Spaces in server: " + spaces.size());
        for (OCSpace space : spaces) {
            Log.log(Level.FINE, "Space in server: " + space.getName() + " "
                    + space.getDescription() + " " + space.getQuota());
            if (!(space.getName().equals(name)
                    && space.getDescription().equals(description)
                    && space.getQuota().equals(quota))) {
                matches = false;
                break;
            }
        }
        // Check if all spaces in scenario definition match with spaces in server
        assertTrue(matches);
    }
}
