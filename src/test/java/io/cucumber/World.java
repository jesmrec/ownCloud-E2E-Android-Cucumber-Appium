/**
 * ownCloud Android Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package io.cucumber;

import android.DetailsPage;
import android.FileListPage;
import android.FolderPickerPage;
import android.InputNamePage;
import android.PublicLinksPage;
import android.LoginPage;
import android.RemoveDialogPage;
import android.SearchShareePage;
import android.PrivateSharePage;
import android.SharePage;
import android.SpacesPage;

import java.io.IOException;

import utils.api.AuthAPI;
import utils.api.FilesAPI;
import utils.api.GraphAPI;
import utils.api.ShareAPI;
import utils.api.TrashbinAPI;

public class World {

    //Involved pages
    LoginPage loginPage = new LoginPage();
    FileListPage fileListPage = new FileListPage();
    InputNamePage inputNamePage = new InputNamePage();
    FolderPickerPage folderPickerPage = new FolderPickerPage();
    RemoveDialogPage removeDialogPage = new RemoveDialogPage();
    DetailsPage detailsPage = new DetailsPage();
    SharePage sharingPage = new SharePage();
    PublicLinksPage linksPage = new PublicLinksPage();
    SearchShareePage searchShareePage = new SearchShareePage();
    PrivateSharePage sharesPage = new PrivateSharePage();
    SpacesPage spacesPage = new SpacesPage();

    //APIs to call
    ShareAPI shareAPI = new ShareAPI();
    FilesAPI filesAPI = new FilesAPI();
    GraphAPI graphAPI = new GraphAPI();
    TrashbinAPI trashbinAPI = new TrashbinAPI();
    AuthAPI authAPI = new AuthAPI();

    public World() throws IOException {
    }
}
