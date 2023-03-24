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
import android.LinksPage;
import android.LoginPage;
import android.RemoveDialogPage;
import android.SearchShareePage;
import android.SharesPage;
import android.SharingPage;
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
    SharingPage sharingPage = new SharingPage();
    LinksPage linksPage = new LinksPage();
    SearchShareePage searchShareePage = new SearchShareePage();
    SharesPage sharesPage = new SharesPage();
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
