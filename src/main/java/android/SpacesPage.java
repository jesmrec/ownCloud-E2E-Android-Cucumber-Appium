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
import java.util.Objects;
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

    @AndroidFindBy(id = "com.owncloud.android:id/create_space_dialog_quota_switch")
    private WebElement quotaSwitch;

    @AndroidFindBy(id = "com.owncloud.android:id/create_space_dialog_quota_value")
    private WebElement quotaValueEdittext;

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
    private final String disabledIconId = "com.owncloud.android:id/spaces_list_item_disabled_icon";

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

    public void openEditSpace(String spaceName){
        Log.log(Level.FINE, "Starts: Open edit space " + spaceName);
        findUIAutomatorDescription(spaceName + " space menu").click();
        findListUIAutomatorText("Edit space").get(0).click();
    }

    public void openDisableSpace(String spaceName){
        Log.log(Level.FINE, "Starts: Open disable space " + spaceName);
        findUIAutomatorDescription(spaceName + " space menu").click();
        findListUIAutomatorText("Disable space").get(0).click();
        findListUIAutomatorText("YES").get(0).click();
    }

    public void editSpace (String name, String subtitle, String quota){
        Log.log(Level.FINE, "Starts: Update space " + name);
        fillSpaceInfo(name, subtitle, quota);
    }

    private void fillSpaceInfo(String spaceName, String subtitle, String quota) {
        Log.log(Level.FINE, "Starts: fill space info: " + spaceName + ", " + subtitle + ", " + quota);
        nameEditText.clear();
        nameEditText.sendKeys(spaceName);
        subtitleEditText.clear();
        subtitleEditText.sendKeys(subtitle);
        setQuota(quota);
        createButton.click();
    }

    private void setQuota(String quota) {
        Log.log(Level.FINE, "Starts: set quota: " + quota);
        boolean withQuota = !"No restriction".equals(quota);
        boolean switchChecked = Boolean.parseBoolean(quotaSwitch.getAttribute("checked"));
        if (withQuota != switchChecked) {
            quotaSwitch.click();
        }
        if (withQuota) {
            waitById(WAIT_TIME, quotaValueEdittext);
            quotaValueEdittext.clear();
            quotaValueEdittext.sendKeys(quota);
        }
    }

    public boolean areAllSpacesVisible(List<List<String>> spaces, String spaceStatus) {
        Log.log(Level.FINE, "Starts: check all spaces are visible");
        HashMap<String, String> spacesInDevice = new HashMap<>();
        waitById(WAIT_TIME, spaceNameId);
        // Fill up the HashMap with spaces in the device that match the enabled/disabled status
        for (WebElement individualSpace : deviceSpacesList) {
            boolean status = individualSpace.findElements(By.id(disabledIconId)).isEmpty();
            Log.log(Level.FINE, "Space status in device: " + status);
            Log.log(Level.FINE, "Space status expected: " + spaceStatus);
            if((spaceStatus.equals("disabled") && !status) ||
                    (spaceStatus.equals("enabled") && status)) {
                String spaceName = individualSpace.findElement(By.id(spaceNameId))
                        .getAttribute("text").trim();
                Log.log(Level.FINE, "Space: " + spaceName + " found in device");
                List<WebElement> spaceDescriptions = individualSpace.findElements(By.id(spaceSubtitleId));
                String spaceDescription = null;
                if (!spaceDescriptions.isEmpty()) {
                    spaceDescription = spaceDescriptions.get(0).getAttribute("text").trim();
                    Log.log(Level.FINE, "spaceDescription: " + spaceDescription);
                }
                Log.log(Level.FINE, "Add: name: " + spaceName + " desc: " + spaceDescription);
                spacesInDevice.put(spaceName, spaceDescription);
            }
        }
        // Check all spaces from the list are in the device
        for (List<String> rows : spaces) {
            String name = rows.get(0);
            String description = rows.get(1);
            if (!spacesInDevice.containsKey(name) ||
                    !Objects.equals(spacesInDevice.get(name), description)) {
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
