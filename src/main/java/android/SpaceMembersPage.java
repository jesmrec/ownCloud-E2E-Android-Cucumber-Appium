package android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.logging.Level;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.date.DateUtils;
import utils.log.Log;

public class SpaceMembersPage extends CommonPage {

    @AndroidFindBy(id = "com.owncloud.android:id/add_member_button")
    private WebElement addMember;

    @AndroidFindBy(id = "com.owncloud.android:id/search_src_text")
    private WebElement searchMember;

    @AndroidFindBy(id = "com.owncloud.android:id/member_name")
    private List<WebElement> searchMemberList;

    @AndroidFindBy(id = "com.owncloud.android:id/expiration_date_switch")
    private WebElement expirationDateSwitch;

    @AndroidFindBy(id = "com.owncloud.android:id/invite_member_button")
    private WebElement inviteMember;

    @AndroidFindBy(id = "com.owncloud.android:id/member_item_layout")
    private List<WebElement> memberList;

    @AndroidFindBy(id = "android:id/next")
    private WebElement nextButton;

    @AndroidFindBy(id = "android:id/button1")
    private WebElement okButton;

    public static SpaceMembersPage instance;

    private SpaceMembersPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public static SpaceMembersPage getInstance() {
        if (instance == null) {
            instance = new SpaceMembersPage();
        }
        return instance;
    }

    public void addMember(String userName) {
        Log.log(Level.FINE, "Starts: Add Member " + userName);
        addMember.click();
        searchMember.sendKeys(userName);
        searchMemberList.get(0).click();
    }

    public void setPermission(String permission) {
        findUIAutomatorText(permission).click();
    }

    public void setExpirationDate(String days) {
        Log.log(Level.FINE, "Starts: Add expiration date in days " + days);
        expirationDateSwitch.click();;
        String dateToSet = DateUtils.dateInDaysAndroidFormat(days);
        Log.log(Level.FINE, "Days: " + days + " Date to set: " + dateToSet);
        if (findListAccesibility(dateToSet).isEmpty()) {
            Log.log(Level.FINE, "Date not found, next page");
            nextButton.click();
        }
        findAccesibility(dateToSet).click();
        okButton.click();
    }

    public void inviteMember() {
        Log.log(Level.FINE, "Starts: Invite member");
        inviteMember.click();
    }

    public boolean isUserMember(String userName, String permission) {
        Log.log(Level.FINE, "Starts: check membership: " + userName);
        // For every member, check if name and role match
        for (WebElement member : memberList) {
            boolean memberFound = member.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"" + userName + "\")")).isDisplayed();
            boolean roleFound = member.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"" + permission + "\")")).isDisplayed();
            if (memberFound && roleFound) {
                Log.log(Level.FINE, userName + " found");
                return true;
            } else {
                Log.log(Level.FINE, userName + " not found");
            }
        }
        return false;
    }

    public boolean isExpirationDateCorrect(String days) {
        Log.log(Level.FINE, "Starts: check expiration date: " + days);
        boolean dateCorrect;
        int expiration = Integer.parseInt(days);
        // Get date from number of days
        String expDate = DateUtils.formatDate(Integer.toString(expiration), DateUtils.DateFormatType.NUMERIC);
        WebElement expirationDate = findId("com.owncloud.android:id/expiration_date");
        Log.log(Level.FINE, "Date to check: " + expDate + " Expiration: " + expiration);
        Log.log(Level.FINE, "Expiration date: " + expirationDate.getText());
        dateCorrect = expirationDate.getText().equals(expDate);
        return dateCorrect;
    }

}
