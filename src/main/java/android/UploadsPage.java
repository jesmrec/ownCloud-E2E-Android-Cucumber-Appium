package android;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class UploadsPage extends CommonPage {

    @AndroidFindBy(id = "com.owncloud.android:id/uploadListGroupButtonClear")
    private MobileElement clear;

    public UploadsPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void clearList(){
        clear.click();
    }

    public boolean isFileUploaded (String fileName){
        boolean fileInList = !findListUIAutomatorText(fileName).isEmpty();
        boolean uploadedListVisible = !findListUIAutomatorText("UPLOADED").isEmpty();
        boolean failedListNotVisible = findListUIAutomatorText("FAILED").isEmpty();
        return fileInList && uploadedListVisible && failedListNotVisible;
    }

}
