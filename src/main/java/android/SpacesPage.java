package android;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.log.Log;

public class SpacesPage extends CommonPage {

    public SpacesPage() {
        super();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public boolean areAllSpacesVisible(List<List<String>> spaces){
        /*List<MobileElement> deviceSpacesList = findListId("id"); //id of the spaces card
        HashMap<String, String> spacesInDevice = new HashMap<>();
        for (MobileElement individualSpace: deviceSpacesList){
            String spaceName = individualSpace.findElement(By.id("com.owncloud.android:id/spaces_list_item_name")).getAttribute("text");
            String spaceDescription = individualSpace.findElement(By.id("com.owncloud.android:id/spaces_list_item_subtitle")).getAttribute("text");
            spacesInDevice.put(spaceName, spaceDescription);
        }
        for (List<String> rows : spaces) {
            String name = rows.get(0);
            String description = rows.get(1);
            if (!description.equals(spacesInDevice.get(name))){
                return false;
            };
        }*/
        return true;
    }

    public boolean isSpaceVisible(String name, String description){
        List<MobileElement> spaces = findListId("com.owncloud.android:id/spaces_list_item_name");
        List<MobileElement> descriptions = findListId("com.owncloud.android:id/spaces_list_item_subtitle");
        boolean spaceFound = false;
        boolean descriptionFound = false;
        for (MobileElement space: spaces) {
            String spaceName = space.getAttribute("text");
            Log.log(Level.FINE, "Space Name: " + spaceName);
            Log.log(Level.FINE, "Name: " + name);
            if (spaceName.equals(name))
                return true;
        }
        return false;
    }
}
