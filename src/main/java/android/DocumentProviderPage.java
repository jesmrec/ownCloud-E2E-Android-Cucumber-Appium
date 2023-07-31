package android;

import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.log.Log;

public class DocumentProviderPage extends CommonPage {

    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc=\"Show roots\"]")
    private MobileElement hamburger;

    public DocumentProviderPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void openDownloadsInHamburger(){
        Log.log(Level.FINE, "Starts: Open Hamburguer in documents provider");
        //For any unknown reason, first time files. Needs to do a second one.
        hamburger.click();
        findUIAutomatorText("Downloads").click();
        hamburger.click();
        findUIAutomatorText("Downloads").click();
    }

    public void selectFileToUpload(String fileName){
        Log.log(Level.FINE, "Starts: Select File to Upload: " + fileName);
        openDownloadsInHamburger();
        findUIAutomatorText(fileName).click();
        //Give some seconds the file to be uploaded
        wait(3);
    }

}
