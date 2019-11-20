package android;

import org.openqa.selenium.By;

public class WizardPage extends CommonPage {

    private String skip_id = "skip";

    public WizardPage() {
        super();
    }

    public void skip(){
        driver.findElement(By.id(skip_id)).click();
    }

}
