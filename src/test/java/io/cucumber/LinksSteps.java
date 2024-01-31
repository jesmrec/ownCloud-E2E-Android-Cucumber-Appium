/**
 * ownCloud Android Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package io.cucumber;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.logging.Level;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.entities.OCShare;
import utils.log.Log;

public class LinksSteps {

    private World world;

    public LinksSteps(World world) {
        this.world = world;
    }

    @ParameterType("item|file|folder")
    public String itemtype(String type){
        return type;
    }

    @Given("{word} has shared the {itemtype} {word} by link")
    public void user_has_shared_the_item_by_link(String sharingUser, String type, String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getShareAPI().createShare(sharingUser, itemName, "", "3", "1", "aa55AA..", itemName + " link", 0);
    }

    @When("Alice creates link on {word} {word} with the following fields")
    public void user_creates_link_with_fields(String type, String itemName, DataTable table)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getSharePage().addPublicLink();
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            switch (rows.get(0)) {
                case "name": {
                    world.getPublicLinksPage().addLinkName(rows.get(1));
                    break;
                }
                case "password": {
                    world.getPublicLinksPage().addPassword(itemName, rows.get(1));
                    break;
                }
                case "permission": {
                    world.getPublicLinksPage().setPermission(rows.get(1));
                    break;
                }
                case "expiration days": {
                    world.getPublicLinksPage().setExpiration(rows.get(1));
                    break;
                }
                default:
                    break;
            }
        }
        world.getPublicLinksPage().submitLink();
    }

    @When("Alice edits the link on {word} with the following fields")
    public void user_edits_public_link_with_fields(String itemName, DataTable table)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> listItems = table.asLists();
        world.getSharePage().openPublicLink(itemName);
        for (List<String> rows : listItems) {
            switch (rows.get(0)) {
                case "name": {
                    world.getPublicLinksPage().addLinkName(rows.get(1));
                    break;
                }
                case "permissions": {
                    switch (rows.get(1)) {
                        case ("1"): { //Download / View
                            Log.log(Level.FINE, "Select Download / View");
                            world.getPublicLinksPage().selectDownloadView();
                            break;
                        }
                        case ("15"): { //Download / View / Upload
                            Log.log(Level.FINE, "Select Download / View / Upload");
                            world.getPublicLinksPage().selectDownloadViewUpload();
                            break;
                        }
                        case ("4"): { //Upload Only (File Drop)
                            Log.log(Level.FINE, "Select Upload Only (File Drop)");
                            world.getPublicLinksPage().selectUploadOnly();
                            break;
                        }
                        default:
                            break;
                    }
                    break;
                }
                case "password": {
                    world.getPublicLinksPage().addPassword(itemName, rows.get(1));
                    break;
                }
                case "expiration days": {
                    world.getPublicLinksPage().setExpiration(rows.get(1));
                    break;
                }
                default:
                    break;
            }
        }
        world.getPublicLinksPage().submitLink();
    }

    @When("Alice deletes the link on {word}")
    public void user_deletes_link(String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getSharePage().deletePublicShare();
        world.getSharePage().acceptDeletion();
    }

    @Then("link should be created on {word} with the following fields")
    public void link_should_be_created_with_fields(String itemName, DataTable table)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        //Asserts in UI
        Log.log(Level.FINE, "Checking UI asserts");
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            switch (rows.get(0)) {
                case "name": {
                    Log.log(Level.FINE, "Checking name: " + rows.get(1));
                    assertTrue(world.getSharePage().isItemInListPublicShares(rows.get(1)));
                    break;
                }
                case "password": {
                    world.getSharePage().openPublicLink(itemName);
                    assertTrue(world.getPublicLinksPage().isPasswordEnabled());
                    world.getPublicLinksPage().close();
                    break;
                }
                case "user": {
                    Log.log(Level.FINE, "checking user: " + itemName);
                    assertTrue(world.getSharePage().isItemInListPublicShares(itemName));
                    break;
                }
                case "permission": {
                    Log.log(Level.FINE, "checking permissions: " + rows.get(1));
                    world.getSharePage().openPublicLink(itemName);
                    assertTrue(world.getPublicLinksPage().checkPermissions(rows.get(1)));
                    world.getPublicLinksPage().close();
                    break;
                }
                case "expiration days": {
                    Log.log(Level.FINE, "checking expirations day: " + rows.get(1));
                    world.getSharePage().openPublicLink(itemName);
                    assertTrue(world.getPublicLinksPage().checkExpiration(rows.get(1)));
                    world.getPublicLinksPage().close();
                    break;
                }
                default:
                    break;
            }
        }
        //Asserts in server via API
        Log.log(Level.FINE, "Checking API/server asserts");
        OCShare share = world.getShareAPI().getShare(itemName);
        assertTrue(world.getSharePage().checkCorrectShare(share, listItems));
    }

    @Then("link on {word} should not exist anymore")
    public void link_should_not_exist_anymore(String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        Log.log(Level.FINE, "Checking if item exists: " + itemName);
        assertTrue(world.getSharePage().isListPublicLinksEmpty());
        assertTrue(world.getShareAPI().getSharesByDefault().size() == 0);
        assertFalse(world.getSharePage().isItemInListPublicShares(itemName + " link"));
    }
}
