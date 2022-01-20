package android;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import javax.xml.parsers.ParserConfigurationException;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.api.FilesAPI;
import utils.date.DateUtils;
import utils.entities.OCCapability;
import utils.log.Log;

public class LinksPage extends CommonPage {

    @AndroidFindBy(id = "com.owncloud.android:id/shareViaLinkExpirationValue")
    private MobileElement expirationDate;

    @AndroidFindBy(id = "com.owncloud.android:id/shareViaLinkNameValue")
    private MobileElement namePublicLink;

    @AndroidFindBy(id = "com.owncloud.android:id/shareViaLinkPasswordValue")
    private MobileElement textPassword;

    @AndroidFindBy(id = "com.owncloud.android:id/shareViaLinkEditPermissionReadOnly")
    private MobileElement downloadViewOption;

    @AndroidFindBy(id = "com.owncloud.android:id/shareViaLinkEditPermissionReadAndWrite")
    private MobileElement downloadViewUploadOption;

    @AndroidFindBy(id = "com.owncloud.android:id/shareViaLinkEditPermissionUploadFiles")
    private MobileElement uploadOnlyOption;

    @AndroidFindBy(id = "com.owncloud.android:id/cancelButton")
    private MobileElement cancelButton;

    @AndroidFindBy(id = "com.owncloud.android:id/saveButton")
    private MobileElement saveButton;

    @AndroidFindBy(id = "android:id/button1")
    private MobileElement okButton;

    @AndroidFindBy(id = "android:id/next")
    private MobileElement nextButton;

    private OCCapability ocCapability;

    //Non-static UI components. Must be matched in specific scenarios
    private MobileElement switchPassword;
    private String switchPasswordId = "com.owncloud.android:id/shareViaLinkPasswordSwitch";
    private String switchExpirationId = "com.owncloud.android:id/shareViaLinkExpirationSwitch";

    public LinksPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        ocCapability = OCCapability.getInstance();
    }

    public void addLinkName(String linkName) {
        Log.log(Level.FINE, "Starts: Add link name: " + linkName);
        namePublicLink.clear();
        namePublicLink.sendKeys(linkName);
    }

    public void addPassword(String itemName, String password) throws IOException, SAXException, ParserConfigurationException {
        Log.log(Level.FINE, "Starts: Add link password: " + password);
        //To avoid password keyboard to appear
        driver.hideKeyboard();
        if (!isPasswordEnforced(itemName)) {
            switchPassword = (MobileElement) driver.findElement(By.id(switchPasswordId));
            switchPassword.click();
        }
        textPassword.sendKeys(password);
        swipe(0.50, 0.45, 0.50, 0.30);
    }

    public void setPermission(String permission) {
        Log.log(Level.FINE, "Starts: Set link permission: " + permission);
        switch (permission) {
            case ("1"): {
                downloadViewOption.click();
                break;
            }
            case ("15"): {
                downloadViewUploadOption.click();
                break;
            }
            case ("4"): {
                uploadOnlyOption.click();
                break;
            }
        }
    }

    public void setExpiration(String days) {
        Log.log(Level.FINE, "Starts: Set Expiration date in days: " + days);
        List<MobileElement> switchExpiration =
                (List<MobileElement>) driver.findElements(By.id(switchExpirationId));
        if (!switchExpiration.isEmpty()) {
            if (parseIntBool(switchExpiration.get(0).getAttribute("checked"))) {
                //if it's enforced, only default
                expirationDate.click();
            } else {
                // Neither enforced nor default
                switchExpiration.get(0).click();
            }
        } else { //if it is enforced
            expirationDate.click();
        }
        int defaultExpiration = DateUtils.minExpirationDate(
                OCCapability.getInstance().expirationDateDays(),
                Integer.valueOf(days)
        );
        String dateToSet = DateUtils.dateInDaysAndroidFormat(Integer.toString(defaultExpiration));
        Log.log(Level.FINE, "default: " + OCCapability.getInstance().expirationDateDays()
                + ". Days: " + days + ". Days to set: " + defaultExpiration + " Date to set: " + dateToSet);
        if (driver.findElements(new MobileBy.ByAccessibilityId(dateToSet)).isEmpty()) {
            Log.log(Level.FINE, "Date not found, next page");
            nextButton.click();
        }
        driver.findElement(new MobileBy.ByAccessibilityId(dateToSet)).click();
        okButton.click();
    }

    public boolean isPasswordEnabled(String itemName)
            throws IOException, SAXException, ParserConfigurationException {
        boolean switchEnabled = true;
        boolean passVisible;
        if (!isPasswordEnforced(itemName)) {
            switchPassword = (MobileElement) driver.findElement(By.id(switchPasswordId));
            switchEnabled = parseIntBool(switchPassword.getAttribute("checked"));
        }
        passVisible = textPassword.isDisplayed();
        return switchEnabled && passVisible;
    }

    public void selectDownloadView() {
        Log.log(Level.FINE, "Starts: Select Download / View");
        downloadViewOption.click();
    }

    public void selectDownloadViewUpload() {
        Log.log(Level.FINE, "Starts: Select Download / View / Upload");
        downloadViewUploadOption.click();
    }

    public void selectUploadOnly() {
        Log.log(Level.FINE, "Starts: Select Upload Only (File drop)");
        uploadOnlyOption.click();
    }

    public boolean checkPermissions(String permissions) {
        Log.log(Level.FINE, "Starts: Check permissions: " + permissions);
        switch (permissions) {
            case ("1"): {
                if (parseIntBool(downloadViewOption.getAttribute("checked"))) {
                    Log.log(Level.FINE, "Download / View is selected");
                    return true;
                }
            }
            case ("15"): {
                if (parseIntBool(downloadViewUploadOption.getAttribute("checked"))) {
                    Log.log(Level.FINE, "Download / View / Upload is selected");
                    return true;
                }
            }
            case ("4"): {
                if (parseIntBool(uploadOnlyOption.getAttribute("checked"))) {
                    Log.log(Level.FINE, "Upload only is selected");
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkExpiration(String days) {
        Log.log(Level.FINE, "Starts: Check expiration in days: " + days);
        List<MobileElement> switchExpiration =
                (List<MobileElement>) driver.findElements(By.id(switchExpirationId));
        boolean switchEnabled = false;
        boolean dateCorrect = false;
        int expiration = DateUtils.minExpirationDate(
                OCCapability.getInstance().expirationDateDays(),
                Integer.parseInt(days)
        );
        String shortDate = DateUtils.shortDate(Integer.toString(expiration));
        Log.log(Level.FINE, "Date to check: " + shortDate + " Expiration: " + expiration);
        if (switchExpiration.isEmpty()) {
            switchEnabled = true;
        } else {
            switchEnabled = parseIntBool(switchExpiration.get(0).getAttribute("checked"));
        }
        Log.log(Level.FINE, "SwitchEnabled -> " + switchEnabled);
        if (switchEnabled) {
            dateCorrect = expirationDate.getText().equals(shortDate);
        }
        Log.log(Level.FINE, "Date Correct -> " + dateCorrect);
        return switchEnabled && dateCorrect;
    }

    public void close() {
        Log.log(Level.FINE, "Starts: Cancel public link view");
        cancelButton.click();
    }

    public void submitLink() {
        Log.log(Level.FINE, "Starts: Submit public link");
        saveButton.click();
    }

    //To check if password is enforced in capabilities
    public boolean isPasswordEnforced(String itemName)
            throws ParserConfigurationException, SAXException, IOException {
        Log.log(Level.FINE, "Starts: Check if password is enforced");
        FilesAPI filesAPI = new FilesAPI();
        boolean isFolder = filesAPI.isFolder(itemName);
        Log.log(Level.FINE, "isFolder: " + isFolder);
        if (isFolder) { //in case of folder, we have to check every permission individually
            if (parseIntBool(downloadViewOption.getAttribute("checked")) &&
                    ocCapability.isPasswordEnforcedReadOnly()) {
                Log.log(Level.FINE, "Download/View selected and pass enforced");
                return true;
            }
            if (parseIntBool(downloadViewUploadOption.getAttribute("checked")) &&
                    ocCapability.isPasswordEnforcedReadWriteDelete()) {
                Log.log(Level.FINE, "Download/View/Upload selected and pass enforced");
                return true;
            }
            if (parseIntBool(uploadOnlyOption.getAttribute("checked")) &&
                    ocCapability.isPasswordEnforcedUploadOnly()) {
                Log.log(Level.FINE, "Upload only selected and pass enforced");
                return true;
            }
            return false;
        } else { //in case of file, only the capability is checked
            return ocCapability.isPasswordEnforced();
        }
    }

    private boolean parseIntBool(String s) {
        return Boolean.parseBoolean(s);
    }
}
