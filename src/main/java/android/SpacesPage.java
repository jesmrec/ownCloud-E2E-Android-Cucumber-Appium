/**
 * ownCloud Android Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package android;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.log.Log;

public class SpacesPage extends CommonPage {

    @AndroidFindBy(id = "spaces_list_item_card")
    private List<WebElement> deviceSpacesList;

    @AndroidFindBy(id = "com.owncloud.android:id/fab_create_space")
    private WebElement createSpace;

    @AndroidFindBy(id = "com.owncloud.android:id/create_space_dialog_name_value")
    private WebElement nameEditText;

    @AndroidFindBy(id = "com.owncloud.android:id/create_space_dialog_subtitle_value")
    private WebElement subtitleEditText;

    @AndroidFindBy(id = "com.owncloud.android:id/create_space_dialog_quota_unit")
    private WebElement quotaUnit;

    @AndroidFindBy(id = "com.owncloud.android:id/create_space_button")
    private WebElement createButton;

    @AndroidFindBy(id = "com.owncloud.android:id/root_toolbar_title")
    private WebElement searchBar;

    @AndroidFindBy(id = "com.owncloud.android:id/search_src_text")
    private WebElement searchInput;

    private final String spaceNameId = "com.owncloud.android:id/spaces_list_item_name";
    private final String spaceSubtitleId = "com.owncloud.android:id/spaces_list_item_subtitle";

    public static SpacesPage instance;

    private SpacesPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public static SpacesPage getInstance() {
        if (instance == null) {
            instance = new SpacesPage();
        }
        return instance;
    }

    public void createSpace (String name, String subtitle, String quota){
        Log.log(Level.FINE, "Starts: Create space " + name);
        createSpace.click();
        fillSpaceInfo(name, subtitle, quota);
    }

    public void openSpaceMenu(String spaceName){
        Log.log(Level.FINE, "Starts: Open space " + spaceName);
        findUIAutomatorDescription(spaceName + " space menu").click();
        findListUIAutomatorText("Edit space").get(0).click();
    }

    public void editSpace (String name, String subtitle, String quota){
        Log.log(Level.FINE, "Starts: Update space " + name);
        fillSpaceInfo(name, subtitle, quota);
    }

    private void fillSpaceInfo(String spaceName, String subtitle, String quota) {
        nameEditText.clear();
        nameEditText.sendKeys(spaceName);
        subtitleEditText.clear();
        subtitleEditText.sendKeys(subtitle);
        quotaUnit.click();
        findListUIAutomatorText(quota).get(0).click();
        createButton.click();
    }

    public boolean areAllSpacesVisible(List<List<String>> spaces) {
        Log.log(Level.FINE, "Starts: check all spaces are visible");
        HashMap<String, String> spacesInDevice = new HashMap<>();
        waitById(WAIT_TIME, spaceNameId);
        for (WebElement individualSpace : deviceSpacesList) {
            String spaceName = individualSpace.findElement(By.id(spaceNameId))
                    .getAttribute("text").trim();
            String spaceDescription = individualSpace.findElement(By.id(spaceSubtitleId))
                    .getAttribute("text").trim();
            spacesInDevice.put(spaceName, spaceDescription);
        }
        for (List<String> rows : spaces) {
            String name = rows.get(0);
            String description = rows.get(1);
            if (!description.equals(spacesInDevice.get(name))) {
                return false;
            }
        }
        return true;
    }

    public void typeSearch(String pattern) {
        Log.log(Level.FINE, "Starts: type search " + pattern);
        searchBar.click();
        searchInput.sendKeys(pattern);
    }

    public void openSpace(String spaceName) {
        deviceSpacesList.get(0).click();
    }
}
