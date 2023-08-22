/**
 * ownCloud Android Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package io.cucumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.entities.OCFile;
import utils.log.Log;

public class FileListSteps {

    private World world;

    public FileListSteps(World world) {
        this.world = world;
    }

    @ParameterType("replace|keep both")
    public String conflictFix(String type){
        return type;
    }

    @Given("there is an item called {word} in the folder Downloads of the device")
    public void there_is_an_item_in_folder_downloads(String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().pushFile(itemName);
    }

    @Given("the following items have been created in the account")
    public void items_have_been_created_in_account(DataTable table) throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> listItems = table.asLists();
        for (List<String> rows : listItems) {
            String type = rows.get(0);
            String name = rows.get(1);
            Log.log(Level.FINE, type + " " + name);
            if (!world.getFilesAPI().itemExist(name)) {
                if (type.equalsIgnoreCase("folder") || type.equalsIgnoreCase("item")) {
                    world.getFilesAPI().createFolder(name);
                } else if (type.equalsIgnoreCase("file")) {
                    world.getFilesAPI().pushFile(name);
                }
            }
        }
    }

    @Given("the folder {word} contains {int} files")
    public void folder_contains_files(String folderName, int files) throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        if (!world.getFilesAPI().itemExist(folderName)) {
            world.getFilesAPI().createFolder(folderName);
        }
        for (int i = 0; i < files; i++) {
            world.getFilesAPI().pushFile(folderName + "/file_" + i + ".txt");
        }
    }

    @When("Alice selects the option Create Folder")
    public void user_selects_option_create_folder() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().createFolder();
    }

    @When("Alice selects to set as av.offline the item {word}")
    public void user_selects_to_set_as_avoffline_item(String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().waitToload(itemName);
        world.getFileListPage().executeOperation("Set as available offline", itemName);
        world.getFileListPage().closeSelectionMode();
    }

    @When("Alice selects to unset as av.offline the item {word}")
    public void user_selects_to_unset_as_unavoffline_item(String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().executeOperation("Unset as available offline", itemName);
        world.getFileListPage().closeSelectionMode();
    }

    @When("Alice selects to {word} the {itemtype} {word}")
    public void user_selects_item_to_some_operation(String operation, String type, String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        if (operation.equals("Download") || operation.equals("open")) {
            world.getFileListPage().downloadAction(itemName);
        } else {
            world.getFileListPage().executeOperation(operation, itemName);
        }
    }

    @When("Alice selects {word} as target folder")
    public void user_selects_target_folder(String targetFolder) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFolderPickerPage().selectFolder(targetFolder);
        world.getFolderPickerPage().accept();
    }

    @When("Alice selects {word} as space")
    public void user_selects_space(String spaceName)
            throws IOException {
        String stepName = new Object() {}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        if (world.getAuthAPI().checkAuthMethod().equals("OIDC")) {
            world.getFolderPickerPage().selectSpace(spaceName);
        }
    }

    @When("Alice creates new folder {word} in the folder picker")
    public void user_creates_folder_picker(String targetFolder) {
        String stepName = new Object() {}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFolderPickerPage().createFolder();
        world.getInputNamePage().setItemName(targetFolder);
    }

    @When("Alice selects the option upload")
    public void user_selects_option_upload() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().uploadFiles();
    }

    @When ("Alice refreshes the list")
    public void user_refreshes_list() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().refreshList();
    }

    @When("Alice accepts the deletion")
    public void user_accepts_deletion() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getRemoveDialogPage().removeAll();
    }

    @When("the {word} has been deleted remotely")
    public void item_has_been_deleted_remotely(String fileName)
            throws IOException {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFilesAPI().removeItem(fileName);
        world.getFileListPage().refreshList();
    }

    @When("Alice sets {word} as new name")
    public void user_sets_new_name(String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getInputNamePage().setItemName(itemName);
    }

    @When ("Alice opens the public link shortcut")
    public void user_opens_public_link_shortcut() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().openLinkShortcut();
        world.getFileListPage().refreshList();
    }

    @When ("Alice opens the available offline shortcut")
    public void user_opens_av_offline_shortcut() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().openAvOffhortcut();
        world.getFileListPage().refreshList();
    }

    @When ("Alice browses into {word}")
    public void user_browses_into_folder(String path) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().refreshList();
        world.getFileListPage().browseToFolder(path);
    }

    @When("Alice browses to root folder")
    public void user_browses_root_folder() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().browseRoot();
    }

    @When("Alice clicks on the thumbnail")
    public void user_clicks_on_the_thumbnail(){
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getDetailsPage().downloadFromThumbnail();
    }

    @When("file {word} is modified externally adding {word}")
    public void file_is_modified_externally_adding_text(String itemName, String text)
            throws IOException {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFilesAPI().pushFile(itemName, text);
    }

    @When("Alice closes the preview")
    public void user_closes_preview() {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getDetailsPage().backListFiles();
    }

    @When("Alice fixes the conflict with {conflictFix}")
    public void user_fixes_conflict(String conflictFix) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().fixConflict(conflictFix);
    }

    @Then("Alice should see {word} in the (file)list")
    public void user_should_see_item_in_filelist(String itemName) throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().refreshList();
        //Get the last token of the item path
        assertTrue(world.getFileListPage().isItemInList(itemName.substring(itemName.lastIndexOf('/') + 1)));
        assertTrue(world.getFilesAPI().itemExist(itemName));
    }

    @Then("Alice should not see {word} in the filelist anymore")
    public void user_should_not_see_item_in_list_anymore(String itemName) throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(world.getFileListPage().isItemInList(itemName));
        assertFalse(world.getFilesAPI().itemExist(itemName));
    }

    @Then("Alice should not see {word} in the links/offline list")
    public void user_should_not_see_item_in_links_list(String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertFalse(world.getFileListPage().isItemInList(itemName));
    }

    //Variant with word parameter
    @Then("Alice should see {word} inside the folder {word}")
    public void user_should_see_item_inside_folder_word(String itemName, String targetFolder)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().browseInto(targetFolder);
        assertTrue(world.getFileListPage().isItemInList(itemName));
        assertTrue(world.getFilesAPI().itemExist(targetFolder + "/" + itemName));
    }

    //Variant with string parameter, in case the itemName contain blanks
    @Then("Alice should see {string} inside the folder {word}")
    public void user_should_see_item_inside_folder_string(String itemName, String targetFolder)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().browseInto(targetFolder);
        world.getFileListPage().isItemInList(itemName);
        assertTrue(world.getFilesAPI().itemExist(targetFolder + "/" + itemName));
    }

    @Then("Alice should see {word} inside the space {word}")
    public void user_should_see_item_inside_space(String itemName, String spaceName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().openSpaces();
        world.getSpacesPage().openSpace(spaceName);
        assertTrue(world.getFileListPage().isItemInList(itemName));
        //assertTrue(world.filesAPI.itemExist(targetFolder + "/" + itemName));
    }

    @Then("Alice should see {word} in the filelist as original")
    public void user_should_see_item_in_filelist_as_original_word(String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.getFileListPage().isItemInList(itemName.substring(itemName.lastIndexOf('/') + 1)));
        assertTrue(world.getFilesAPI().itemExist(itemName));
    }

    @Then("Alice should see {string} in the filelist as original")
    public void user_should_see_item_in_filelist_as_original_string(String itemName)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().refreshList();
        assertTrue(world.getFileListPage().isItemInList(itemName.substring(itemName.lastIndexOf('/') + 1)));
        assertTrue(world.getFilesAPI().itemExist(itemName));
    }

    @Then("Alice should see the detailed information: {word}, {word}, and {word}")
    public void user_should_see_defailed_information(String itemName, String type, String size) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getDetailsPage().removeShareSheet();
        assertEquals(world.getDetailsPage().getName(), itemName);
        assertEquals(world.getDetailsPage().getSize(), size);
        assertEquals(world.getDetailsPage().getType(), type);
        world.getDetailsPage().backListFiles();
    }

    @Then("the item {word} should be marked as downloaded")
    public void item_should_be_marked_as_downloaded(String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.getFileListPage().fileIsMarkedAsDownloaded(itemName));
    }

    @Then("Alice should see the {itemtype} {word} as av.offline")
    public void user_should_see_item_marked_as_avOffline(String type, String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.getFileListPage().itemIsMarkedAsAvOffline(itemName));
    }

    @Then("Alice should not see the {itemtype} {word} as av.offline")
    public void user_should_not_see_item_marked_as_avOffline(String type, String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().waitToload(itemName);
        assertTrue(world.getFileListPage().itemIsMarkedAsUnAvOffline(itemName));
    }

    @Then("the item {word} should be opened and previewed")
    public void item_should_be_opened_and_previewed(String itemName) {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();;
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.getDetailsPage().itemPreviewed());
        world.getDetailsPage().backListFiles();
    }

    @Then("the list of files in {word} folder should match with the server")
    public void list_of_files_in_folder_should_match_server(String path)
            throws Throwable {
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().refreshList();
        world.getFileListPage().browseToFolder(path);
        ArrayList<OCFile> listServer = world.getFilesAPI().listItems(path);
        assertTrue(world.getFileListPage().displayedList(path, listServer));
    }

    @Then("Alice should see the following error/message")
    public void user_should_see_following_error(DataTable table){
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        List<List<String>> listItems = table.asLists();
        String error = listItems.get(0).get(0);
        Log.log(Level.FINE, "Error/Message to check: " + error);
        assertTrue(world.getFileListPage().errorDisplayed(error));
    }

    @Then("Alice should see the file {word} with {word}")
    public void user_should_see_the_file_with_text(String itemName, String text){
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().downloadAction(itemName);
        assertTrue(world.getDetailsPage().itemPreviewed() &&
                world.getDetailsPage().textInFile(text));
    }

    @Then("share sheet for the item {word} is displayed")
    public void share_sheet_for_item_displayed(String itemName){
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        assertTrue(world.getDetailsPage().shareSheetDisplayed(itemName));
    }

    @Then("Alice cannot unset as av.offline the item {word}")
    public void user_cannot_unset_avoffline_the_item(String itemName){
        String stepName = new Object(){}.getClass().getEnclosingMethod().getName().toUpperCase();
        Log.log(Level.FINE, "----STEP----: " + stepName);
        world.getFileListPage().selectItemList(itemName);
        assertFalse(world.getFileListPage().operationAvailable("Unset as available offline"));
    }
}
