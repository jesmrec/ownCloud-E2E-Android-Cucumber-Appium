package android;

import io.appium.java_client.MobileBy;

public class WizardPage extends CommonPage {

    private String skip_id = "skip";

    public WizardPage(){
        super();
    }

    public void skip(){
        waitById(5, skip_id);
        driver.findElement(MobileBy.id(skip_id)).click();
    }

}
