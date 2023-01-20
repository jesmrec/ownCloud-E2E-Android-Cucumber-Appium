package io.cucumber;

import static org.junit.Assert.assertTrue;

import android.FileListPage;
import android.SpacesPage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.api.GraphAPI;
import utils.log.Log;

public class SpacesSteps {

    //Involved pages
    protected FileListPage fileListPage = new FileListPage();
    protected SpacesPage spacesPage = new SpacesPage();

    //APIs to call
    protected GraphAPI graphAPI = new GraphAPI();

    public SpacesSteps() throws IOException {
    }

    @Given("the following spaces have been created in the account")
    public void spaces_have_been_created(DataTable table) throws IOException {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            String name = rows.get(0);
            String description = rows.get(1);
            graphAPI.createSpace(name, description);
        }
    }

    @When("Alice selects the spaces view")
    public void user_selects_spaces_view() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        fileListPage.openSpaces();
    }

    @Then("Alice should see the following spaces")
    public void user_should_see_following_spaces(DataTable table){
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> listItems = table.asLists();
        assertTrue(spacesPage.areAllSpacesVisible(listItems));
    }
}
