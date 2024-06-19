/**
 * ownCloud Android Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.LocProperties;
import utils.entities.OCFile;
import utils.log.Log;

public class FileListPage extends CommonPage {

    private final String shareoption_id = "com.owncloud.android:id/action_share_file";
    private final String avofflineoption_id = "com.owncloud.android:id/action_set_available_offline";
    private final String unavofflineoption_id = "com.owncloud.android:id/action_set_unavailable_offline";
    private final String downloadoption_id = "com.owncloud.android:id/action_download_file";
    private final String syncoption_id = "com.owncloud.android:id/action_sync_file";

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.owncloud.android:id/action_mode_close_button\");")
    private WebElement closeSelectionMode;

    @AndroidFindBy(id = "com.owncloud.android:id/fab_expand_menu_button")
    private WebElement fabButton;

    @AndroidFindBy(id = "com.owncloud.android:id/root_toolbar_left_icon")
    private List<WebElement> hamburgerButton;

    @AndroidFindBy(id = "com.owncloud.android:id/fab_mkdir")
    private WebElement createFolder;

    @AndroidFindBy(id = "com.owncloud.android:id/fab_upload")
    private WebElement uploadOption;

    @AndroidFindBy(id = "com.owncloud.android:id/upload_from_files_item_view")
    private WebElement uploadFiles;

    @AndroidFindBy(id = "com.owncloud.android:id/upload_from_camera_item_view")
    private WebElement uploadPic;

    @AndroidFindBy(id = "com.owncloud.android:id/root_toolbar")
    private List<WebElement> toolbar;

    @AndroidFindBy(id = "com.owncloud.android:id/bottom_nav_view")
    WebElement bottomBar;

    @AndroidFindBy(id = "com.owncloud.android:id/list_root")
    private WebElement listFiles;

    @AndroidFindBy(id = "com.owncloud.android:id/file_list_constraint_layout")
    private WebElement fileCell;

    @AndroidFindBy(id = "com.owncloud.android:id/Filename")
    private WebElement fileName;

    @AndroidFindBy(id = "com.owncloud.android:id/dialog_file_already_exists_keep_both")
    private WebElement keepBoth;

    @AndroidFindBy(id = "com.owncloud.android:id/dialog_file_already_exists_replace")
    private WebElement replace;

    @AndroidFindBy(id = "com.owncloud.android:id/dialog_file_already_exists_skip")
    private WebElement skip;

    @AndroidFindBy(id = "com.owncloud.android:id/nav_shared_by_link_files")
    private WebElement linksShortcut;

    @AndroidFindBy(id = "com.owncloud.android:id/nav_available_offline_files")
    private WebElement avOffShortcut;

    @AndroidFindBy(id = "nav_spaces")
    WebElement spacesTab;

    @AndroidFindBy(id = "nav_uploads")
    WebElement uploads;

    @AndroidFindBy(id = "com.owncloud.android:id/list_empty_dataset_title")
    WebElement emptyMessage;

    @AndroidFindBy(id = "com.owncloud.android:id/dialog_file_already_exists_title")
    WebElement conflictTitle;

    public FileListPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void refreshList() {
        Log.log(Level.FINE, "Refresh list");
        waitById(5, bottomBar);
        swipe(0.50, 0.30, 0.50, 0.80);
    }

    public void waitToload(String itemName) {
        Log.log(Level.FINE, "Waiting to load");
        String listFiles_id = "com.owncloud.android:id/list_root";
        try {
            //if list of files is not loaded, we should swipe to get the file list
            waitById(5, listFiles_id);
        } catch (Exception e) {
            Log.log(Level.FINE, "Swipe needed to get the list");
            refreshList();
        }
        Log.log(Level.FINE, "Loaded");
    }

    public void createFolder() {
        Log.log(Level.FINE, "Starts: create folder");
        refreshList();
        fabButton.click();
        createFolder.click();
    }

    public void uploadFiles(String operation) {
        Log.log(Level.FINE, "Starts: upload");
        fabButton.click();
        uploadOption.click();
        if (operation.equals("upload")) {
            uploadFiles.click();
        } else if (operation.equals("picture")) {
            uploadPic.click();
        }
    }

    public void pushFile(String itemName) {
        Log.log(Level.FINE, "Starts: push file: " + itemName);
        File rootPath = new File(System.getProperty("user.dir"));
        File appDir = new File(rootPath, "src/test/resources");
        File app = new File(appDir, "io/cucumber/example-files/" + itemName);
        try {
            driver.pushFile("/sdcard/Download/" + itemName, app);
            Log.log(Level.FINE, "File " + itemName + " pushed");
        } catch (IOException e) {
            Log.log(Level.SEVERE, "IO Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void executeOperation(String operation, String itemName) {
        Log.log(Level.FINE, "Starts: execute operation: " + operation + " " + itemName);
        waitToload(itemName);
        selectItemList(itemName);
        selectOperation(operation);
    }

    public void downloadAction(String itemName) {
        Log.log(Level.FINE, "Starts: download action: " + itemName);
        if (!isItemInList(itemName)) {
            Log.log(Level.FINE, "Searching item... swiping: " + itemName);
            refreshList();
        }
        findUIAutomatorText(itemName).click();
    }

    public boolean isItemInList(String itemName) {
        Log.log(Level.FINE, "Starts: Check if item is in list: " + itemName);
        waitById(5, bottomBar);
        return !findListUIAutomatorText(itemName).isEmpty();
    }

    public boolean isFileListVisible() {
        Log.log(Level.FINE, "Starts: is File list Visible");
        return !toolbar.isEmpty();
    }

    public boolean errorDisplayed(String error) {
        Log.log(Level.FINE, "Starts: Error displayed: " + error);
        return findUIAutomatorSubText(error).isDisplayed();
    }

    public void selectItemList(String path) {
        Log.log(Level.FINE, "Starts: select item from list: " + path);
        String fileName = path.contains("/") ? browseToFile(path) : path;
        WebElement element = getElementFromFileList(fileName);
        refreshList();
        longPress(element);
    }

    public void selectOperation(String operationName) {
        if (operationName.equals("share")) {  //placed in toolbar
            findId(shareoption_id).click();
        } else {
            Log.log(Level.FINE, "Operation: " + operationName + " placed in menu");
            selectOperationMenu(operationName);
        }
    }

    public void longPress(String itemName) {
        longPress(findUIAutomatorText(itemName));
    }

    //Select once multiselection mode is on
    public void selectItem(String itemName) {
        Log.log(Level.FINE, "Starts: select item: " + itemName);
        findUIAutomatorText(itemName).click();
    }

    public void openLinkShortcut() {
        Log.log(Level.FINE, "Starts: open link shortcut");
        linksShortcut.click();
    }

    public void openAvOffhortcut() {
        Log.log(Level.FINE, "Starts: open av offline shortcut");
        waitByTextInvisible(5, "Download enqueued");
        avOffShortcut.click();
    }

    public void openSpaces() {
        spacesTab.click();
    }

    public void openUploadsView() {
        uploads.click();
    }

    public void closeSelectionMode() {
        Log.log(Level.FINE, "Starts: close selection mode");
        closeSelectionMode.click();
    }

    public boolean isFileMarkedAsDownloaded(String path) {
        Log.log(Level.FINE, "Check if file is downloaded: " + path);
        selectItemList(path);
        List<WebElement> downloadOptions = findListId(downloadoption_id);
        List<WebElement> syncOptions = findListId(syncoption_id);
        return downloadOptions.isEmpty() && !syncOptions.isEmpty();
    }

    public boolean isItemMarkedAsAvOffline(String path) {
        selectItemList(path);
        findUIAutomatorDescription("More options").click();
        return findListId(avofflineoption_id).isEmpty();
    }

    public boolean isItemMarkedAsUnAvOffline(String path) {
        selectItemList(path);
        findUIAutomatorDescription("More options").click();
        return findListId(unavofflineoption_id).isEmpty();
    }

    private void openMenuActions() {
        findUIAutomatorDescription("More options").click();
    }

    private void selectOperationMenu(String operationName) {
        Log.log(Level.FINE, "Starts: Select operation from the menu: " + operationName);
        openMenuActions();
        findUIAutomatorText(operationName).click();
    }

    public boolean isOperationAvailable(String operationName) {
        Log.log(Level.FINE, "Starts: Check if operation is available: " + operationName);
        openMenuActions();
        return !findListUIAutomatorText(operationName).isEmpty();
    }

    public boolean isConflictDisplayed() {
        Log.log(Level.FINE, "Starts: Conflict displayed");
        return conflictTitle.isDisplayed();
    }

    public void fixConflict(String option) {
        Log.log(Level.FINE, "Starts: Fix Conflict: " + option);
        if (option.equals("replace")) {
            replace.click();
        } else {
            keepBoth.click();
        }
    }

    public boolean isDisplayedListCorrect(String path, ArrayList<OCFile> listServer) {
        browseToFolder(path); // Mover a la carpeta
        String userName1 = LocProperties.getProperties().getProperty("userName1");
        return listServer.stream().filter(
                        ocfile -> !ocfile.getName().equalsIgnoreCase(userName1) && ocfile.getName().length() <= 15)
                .allMatch(ocfile -> {
                    while (!isItemInList(ocfile.getName()) && !endList(listServer.size())) {
                        refreshList();
                    }
                    return isItemInList(ocfile.getName());
                });
    }

    private boolean endList(int numberItems) {
        return !findListUIAutomatorText(Integer.toString(numberItems - 1) + " files")
                .isEmpty();
    }

    private WebElement getElementFromFileList(String itemName) {
        Log.log(Level.FINE, "Starts: searching item in list: " + itemName);
        if (isItemInList(itemName)) {
            Log.log(Level.FINE, "Item found: " + itemName);
            return findUIAutomatorText(itemName);
        } else {
            Log.log(Level.FINE, "Item not found: " + itemName);
            return null;
        }
    }

    public String getPrivateLink(String scheme, String privateLink) {
        Log.log(Level.FINE, "Starts: Create private link: " + scheme + " " + privateLink);
        String originalScheme = getScheme(privateLink);
        //Scaping the $... will improve with something native
        String linkToOpen = privateLink.replace(originalScheme, scheme)
                .replace("$", "\\$");
        Log.log(Level.FINE, "Link to open: " + linkToOpen);
        return linkToOpen;
    }

    private String getScheme(String originalURL) {
        return originalURL.split("://")[0];
    }

    public void openPrivateLink(String privateLink) {
        Log.log(Level.FINE, "Starts: Open private link: " + privateLink);
        driver.get(privateLink);
    }

    public void openFakePrivateLink() {
        Log.log(Level.FINE, "Starts: Open fake private link");
        String originalScheme = getScheme(System.getProperty("server"));
        String fakeURL = System.getProperty("server").replace(originalScheme, "owncloud")
                + "/f/11111111111";
        Log.log(Level.FINE, "Fake URL: " + fakeURL);
        driver.get(fakeURL);
    }

    public boolean isItemOpened(String itemType, String itemName) {
        Log.log(Level.FINE, "Starts: checking if item is opened: " + itemType + " " + itemName);
        if (itemType.equals("file")) {
            Log.log(Level.FINE, "Opening file");
            boolean fileNameVisible = findUIAutomatorText(itemName).isDisplayed();
            boolean fileTypeIconVisible = findId("com.owncloud.android:id/fdImageDetailFile").isDisplayed();
            return fileNameVisible && fileTypeIconVisible;
        } else if (itemType.equals("folder")) {
            Log.log(Level.FINE, "Opening folder");
            boolean folderNameVisible = findUIAutomatorText(itemName).isDisplayed();
            boolean hamburgerButtonVisible = hamburgerButton.size() > 0;
            return folderNameVisible && !hamburgerButtonVisible;
        }
        return false;
    }

}
