package android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Level;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.log.Log;

public class DocumentProviderPage extends CommonPage {

    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc=\"Show roots\"]")
    private WebElement hamburger;

    public static DocumentProviderPage instance;

    private DocumentProviderPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public static DocumentProviderPage getInstance() {
        if (instance == null) {
            instance = new DocumentProviderPage();
        }
        return instance;
    }

    public void openDownloadsInHamburger() {
        Log.log(Level.FINE, "Starts: Open Hamburguer in documents provider");
        //For any unknown reason, first time fails. Needs to do a second one.
        hamburger.click();
        findUIAutomatorText("Downloads").click();
    }

    public void selectFileToUpload(String fileName) {
        Log.log(Level.FINE, "Starts: Select File to Upload: " + fileName);
        openDownloadsInHamburger();
        findUIAutomatorText(fileName).click();
    }
}
