/**
 * ownCloud Android Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package io.cucumber;

import android.CameraPage;
import android.ConflictPage;
import android.DetailsPage;
import android.DevicePage;
import android.DocumentProviderPage;
import android.FileListPage;
import android.FolderPickerPage;
import android.InputNamePage;
import android.LoginPage;
import android.PrivateSharePage;
import android.PublicLinksPage;
import android.RemoveDialogPage;
import android.SearchShareePage;
import android.SharePage;
import android.ShortcutDialogPage;
import android.SpacesPage;
import android.UploadsPage;

import java.io.IOException;

import utils.api.FilesAPI;
import utils.api.GraphAPI;
import utils.api.ShareAPI;
import utils.api.TrashbinAPI;

public class World {

    //Involved pages
    public LoginPage loginPage = LoginPage.getInstance();
    public FileListPage fileListPage = FileListPage.getInstance();
    public InputNamePage inputNamePage = InputNamePage.getInstance();
    public FolderPickerPage folderPickerPage = FolderPickerPage.getInstance();
    public RemoveDialogPage removeDialogPage = RemoveDialogPage.getInstance();
    public DetailsPage detailsPage = DetailsPage.getInstance();
    public SharePage sharePage = SharePage.getInstance();
    public PublicLinksPage publicLinksPage = PublicLinksPage.getInstance();
    public SearchShareePage searchShareePage = SearchShareePage.getInstance();
    public PrivateSharePage privateSharePage = PrivateSharePage.getInstance();
    public SpacesPage spacesPage = SpacesPage.getInstance();
    public DocumentProviderPage documentProviderPage = DocumentProviderPage.getInstance();
    public UploadsPage uploadsPage = UploadsPage.getInstance();
    public CameraPage cameraPage = CameraPage.getInstance();
    public ShortcutDialogPage shortcutDialogPage = ShortcutDialogPage.getInstance();
    public DevicePage devicePage = DevicePage.getInstance();
    public ConflictPage conflictPage = ConflictPage.getInstance();

    //APIs to call
    public ShareAPI shareAPI = ShareAPI.getInstance();
    public FilesAPI filesAPI = FilesAPI.getInstance();
    public GraphAPI graphAPI = GraphAPI.getInstance();
    public TrashbinAPI trashbinAPI = TrashbinAPI.getInstance();

    public World() throws IOException {
    }
}
