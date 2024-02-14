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

public class SharesSteps {

    private World world;

    public SharesSteps(World world) {
        this.world = world;
    }

    @ParameterType("user|group")
    public String usertype(String type){
        return type;
    }

    @ParameterType("shared|reshared")
    public int sharelevel(String type){
        if (type.equals("shared")) {
            return 0; //share, first level
        } else {
            return 1; //reshare
        }
    }

    @Given("{word} has {sharelevel} {itemtype} {word} with {word} with permissions {word}")
    public void user_has_shared_item_with_permissions(String sharingUser, int shareLevel, String type, String itemName,
                                    String recipientUser, String permissions) throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getShareAPI().createShare(sharingUser, itemName, recipientUser, "0", permissions, "", "", shareLevel);
        world.getShareAPI().acceptAllShares("user", recipientUser);
    }

    @When("Alice selects {usertype} {word} as sharee")
    public void user_selects_sharee(String type, String sharee)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getSharePage().addPrivateShare();
        world.getSearchShareePage().shareWithUser(sharee);
        world.getShareAPI().acceptAllShares(type,sharee);
    }

    @When("Alice edits the share on {itemtype} {word} with permissions {word}")
    public void user_edits_share_with_permissions(String type, String itemName, String permissions) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getSharePage().openPrivateShare(itemName);
        int permissionsToInt = Integer.parseInt(permissions);
        String permissionsToString = String.format("%5s", Integer.toBinaryString(permissionsToInt))
                .replace(" ", "0");
        Log.log(Level.FINE, "Permissions converted: " + permissionsToString);
        for (int i = 0; i <= permissionsToString.length() - 1; i++) {
            switch (i) {
                case (0): {
                    Log.log(Level.FINE, "Check Share");
                    char status = permissionsToString.charAt(i);
                    boolean enabled = world.getPrivateSharePage().isShareEnabled();
                    Log.log(Level.FINE, "Status: " + status + ". Enabled: " + enabled);
                    if (enabled != (status == '1'))
                        world.getPrivateSharePage().switchShare();
                    break;
                }
                case (1): {
                    if (type.equalsIgnoreCase("folder")) {
                        Log.log(Level.FINE, "Check Delete");
                        char status = permissionsToString.charAt(i);
                        boolean enabled = world.getPrivateSharePage().isDeleteSelected();
                        Log.log(Level.FINE, "Status: " + status + ". Enabled: " + enabled);
                        if (enabled != (status == '1'))
                            world.getPrivateSharePage().switchDelete();
                    }
                    break;
                }
                case (2): {
                    if (type.equalsIgnoreCase("folder")) {
                        Log.log(Level.FINE, "Check Create");
                        char status = permissionsToString.charAt(i);
                        boolean enabled = world.getPrivateSharePage().isCreateSelected();
                        Log.log(Level.FINE, "Status: " + status + ". Enabled: " + enabled);
                        if (enabled != (status == '1'))
                            world.getPrivateSharePage().switchCreate();
                    }
                    break;
                }
                case (3): {
                    Log.log(Level.FINE, "Check Change");
                    char status = permissionsToString.charAt(i);
                    if (type.equalsIgnoreCase("folder")) {
                        boolean enabled = world.getPrivateSharePage().isChangeSelected();
                        Log.log(Level.FINE, "Status Folder: " + status + ". Enabled: " + enabled);
                        if (enabled != (status == '1')) {
                            world.getPrivateSharePage().switchChange();
                        }
                    } else if (type.equalsIgnoreCase("file")) {
                        boolean enabled = world.getPrivateSharePage().isEditFileEnabled();
                        Log.log(Level.FINE, "Status File: " + status + ". Enabled: " + enabled);
                        if (enabled != (status == '1')) {
                            world.getPrivateSharePage().switchEditFile();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
        world.getPrivateSharePage().close();
    }

    @When("Alice deletes the share")
    public void user_deletes_share() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getSharePage().deletePrivateShare();
        world.getSharePage().acceptDeletion();
    }

    @When("Alice closes share view")
    public void user_closes_shares_view() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getSharePage().close();
    }

    @Then("share should be created on {word} with the following fields")
    public void share_should_be_created_with_fields(String itemName, DataTable table)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        //Asserts in UI
        String groupName = null;
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            switch (rows.get(0)) {
                case "sharee": {
                    assertTrue(world.getSharePage().isItemInListPrivateShares(rows.get(1)));
                    break;
                }
                case "group": {
                    assertTrue(world.getSharePage().isItemInListPrivateShares(rows.get(1) + " (group)"));
                    break;
                }
                case "permissions": {
                    world.getSharePage().openPrivateShare(itemName);
                    switch (rows.get(1)) {
                        case ("1"): { // only read
                            Log.log(Level.FINE, "Only read");
                            assertTrue(!world.getPrivateSharePage().isEditEnabled() &&
                                    !world.getPrivateSharePage().isShareEnabled());
                            break;
                        }
                        case ("3"): { //edit
                            Log.log(Level.FINE, "Edit");
                            Log.log(Level.FINE, Boolean.toString(world.getPrivateSharePage().isEditEnabled()));
                            Log.log(Level.FINE, Boolean.toString(world.getPrivateSharePage().isShareEnabled()));
                            assertTrue(world.getPrivateSharePage().isEditEnabled() &&
                                    !world.getPrivateSharePage().isShareEnabled());
                            break;
                        }
                        case ("9"): { //delete
                            Log.log(Level.FINE, "Delete");
                            assertTrue(!world.getPrivateSharePage().isCreateSelected() &&
                                    !world.getPrivateSharePage().isChangeSelected() &&
                                    world.getPrivateSharePage().isDeleteSelected() &&
                                    !world.getPrivateSharePage().isShareEnabled());
                            break;
                        }
                        case ("13"): { //delete and create
                            Log.log(Level.FINE, "Delete and Create");
                            assertTrue(world.getPrivateSharePage().isCreateSelected() &&
                                    !world.getPrivateSharePage().isChangeSelected() &&
                                    world.getPrivateSharePage().isDeleteSelected() &&
                                    !world.getPrivateSharePage().isShareEnabled());
                            break;
                        }
                        case ("17"): { //share
                            Log.log(Level.FINE, "Share");
                            assertTrue(!world.getPrivateSharePage().isEditEnabled() &&
                                    world.getPrivateSharePage().isShareEnabled());
                            break;
                        }
                        case ("19"): { //share and edit
                            Log.log(Level.FINE, "Share and Edit");
                            assertTrue(world.getPrivateSharePage().isEditEnabled() &&
                                    world.getPrivateSharePage().isShareEnabled());
                            break;
                        }
                        default:
                            break;
                    }
                }
                default:
                    break;
            }
        }
        //Asserts in server via API
        OCShare share = world.getShareAPI().getShare(itemName);
        assertTrue(world.getSharePage().checkCorrectShare(share, listItems));
    }

    @Then("{word} should not have access to {word}")
    public void sharee_should_not_have_access_to_item(String userName, String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(world.getShareAPI().isSharedWithMe(itemName, userName, false));
    }

    @Then("{usertype} {word} should have access to {word}")
    public void sharee_should_not_have_access_the_item(String type, String shareeName, String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        if (type.equalsIgnoreCase("user")) {
            assertTrue(world.getShareAPI().isSharedWithMe(itemName, shareeName, false));
        } else if (type.equalsIgnoreCase("group")) {
            assertTrue(world.getShareAPI().isSharedWithMe(itemName, shareeName, true));
        }
    }

    @Then("{word} should not be shared anymore with {word}")
    public void item_should_not_be_shared_with(String itemName, String sharee)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(world.getSharePage().isItemInListPrivateShares(sharee));
    }

    @Then("Alice should see {word} as recipient")
    public void user_should_see_recipient(String sharee) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.getPrivateSharePage().isSharee(sharee));
    }
}
