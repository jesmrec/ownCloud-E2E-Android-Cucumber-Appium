package io.cucumber;

import android.DetailsPage;
import android.FileListPage;
import android.FolderPickerPage;
import android.InputNamePage;
import android.RemoveDialogPage;

import net.thucydides.core.steps.StepEventBus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.api.FilesAPI;
import utils.entities.OCFile;
import utils.log.Log;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileListSteps {

    //Involved pages
    protected FileListPage fileListPage = new FileListPage();
    protected InputNamePage inputNamePage = new InputNamePage();
    protected FolderPickerPage folderPickerPage = new FolderPickerPage();
    protected RemoveDialogPage removeDialogPage = new RemoveDialogPage();
    protected DetailsPage detailsPage = new DetailsPage();

    //APIs to call
    protected FilesAPI filesAPI = new FilesAPI();

    @Given("^there is an item called (.+) in the folder Downloads of the device$")
    public void push_file_to_device(String itemName){
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        fileListPage.pushFile(itemName);
    }

    @Given("the following items have been created in the account")
    public void item_exists(DataTable table) throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        List<String> listItems = (List<String>) table.asList();
        Iterator iterator = listItems.iterator();
        while(iterator.hasNext()) {
            String itemName = (String)iterator.next();
            if (!filesAPI.itemExist(itemName)) {
                filesAPI.createFolder(itemName);
            }
        }
    }

    @When("^user selects the option Create Folder$")
    public void i_select_create_folder() {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        fileListPage.startRecording();
        fileListPage.createFolder();
    }

    @When("^user selects to (.+) the item (.+)$")
    public void i_select_item_to_some_operation(String operation, String itemName) {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        fileListPage.startRecording();
        fileListPage.waitToload();
        fileListPage.refreshList();
        switch (operation){
            case "Download":
                fileListPage.downloadAction(itemName);
                detailsPage.waitFinishedDownload(30);
                break;
            case "Upload":
                //fileListPage.selectFileUpload(itemName);
                break;
            default:
                fileListPage.executeOperation(operation, itemName);
                break;
        }
    }

    @When ("^user selects (.+) as target folder$")
    public void i_select_target_folder(String targetFolder) {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        folderPickerPage.selectFolder(targetFolder);
        folderPickerPage.accept();
    }

    @When("^user selects the option upload$")
    public void i_select_upload() {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        fileListPage.upload();
    }

    @When("^user accepts the deletion$")
    public void i_accept_the_deletion(){
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        removeDialogPage.removeAll();
    }

    @When("^user sets (.+) as new name$")
    public void i_set_new_name(String itemName) {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        inputNamePage.setItemName(itemName);
    }

    @Then("^user should see (.+) in the filelist$")
    public void i_see_the_item(String itemName) throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        fileListPage.waitToload();
        //Get the last token of the item path
        assertTrue(fileListPage.isItemInList(itemName.substring(itemName.lastIndexOf('/')+1)));
        assertTrue(filesAPI.itemExist(itemName));
        filesAPI.removeItem(itemName);
        fileListPage.stopRecording("createfolder");
    }

    @Then("^user should not see (.+) in the filelist anymore$")
    public void i_do_not_see_the_item(String itemName) throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        fileListPage.waitToload();
        assertFalse(fileListPage.isItemInList(itemName));
        assertFalse(filesAPI.itemExist(itemName));
    }

    @Then("^user should see (.+) inside the folder (.+)$")
    public void i_see_item_in_folder(String itemName, String targetFolder) throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        assertTrue(filesAPI.itemExist(targetFolder+"/"+itemName));
        filesAPI.removeItem(targetFolder+"/"+itemName);
    }

    @Then("^user should see (.+) in the filelist as original$")
    public void i_see_original_the_item(String itemName) throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        //Copy keeps the selection mode. To improve.
        fileListPage.closeSelectionMode();
        fileListPage.waitToload();
        assertTrue(fileListPage.isItemInList(itemName.substring(itemName.lastIndexOf('/')+1)));
        assertTrue(filesAPI.itemExist(itemName));
        filesAPI.removeItem(itemName);
    }

    @Then("^the item (.+) should be stored in the device$")
    public void item_downloaded(String itemName) {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        assertTrue(fileListPage.fileIsDownloaded(itemName));
    }

    @Then("^user should see the detailed information: (.+), (.+), and (.+)$")
    public void preview_in_screen(String itemName, String type, String size) {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        detailsPage.removeShareSheet();
        assertEquals(detailsPage.getName(), itemName);
        assertEquals(detailsPage.getSize(), size);
        assertEquals(detailsPage.getType(), type);
        detailsPage.backListFiles();
    }

    @Then("^the item (.+) should be marked as downloaded$")
    public void item_marked_as_downloaded(String itemName) {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        assertTrue(fileListPage.fileIsMarkedAsDownloaded(itemName));
    }

    @Then("^user should see the item (.+) as av.offline$")
    public void item_marked_as_avOffline(String itemName) {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        assertTrue(fileListPage.fileIsMarkedAsAvOffline(itemName));
    }

    @Then("^the item (.+) should be opened and previewed$")
    public void item_opened_previewed(String itemName) {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        assertTrue(detailsPage.itemPreviewed());
    }

    @Then("^the list of files in (.+) folder should match with the server$")
    public void list_matches_server(String path) throws Throwable {
        String currentStep = StepEventBus.getEventBus().getCurrentStep().get().toString();
        Log.log(Level.FINE, "----STEP----: " + currentStep);
        fileListPage.waitToload();
        ArrayList<OCFile> listServer = filesAPI.listItems(path);
        assertTrue(fileListPage.displayedList(path, listServer));
    }
}
