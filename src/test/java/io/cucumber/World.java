/**
 * ownCloud Android Scenario Tests
 *
 * @author Jesús Recio Rincón (@jesmrec)
 */

package io.cucumber;

import android.CameraPage;
import android.DetailsPage;
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
import android.SpacesPage;
import android.UploadsPage;

import java.io.IOException;

import utils.api.AuthAPI;
import utils.api.FilesAPI;
import utils.api.GraphAPI;
import utils.api.ShareAPI;
import utils.api.TrashbinAPI;

public class World {

    //Involved pages
    private LoginPage loginPage;
    private FileListPage fileListPage;
    private InputNamePage inputNamePage;
    private FolderPickerPage folderPickerPage;
    private RemoveDialogPage removeDialogPage;
    private DetailsPage detailsPage;
    private SharePage sharePage;
    private PublicLinksPage publicLinksPage;
    private SearchShareePage searchShareePage;
    private PrivateSharePage privateSharePage;
    private SpacesPage spacesPage;
    private DocumentProviderPage documentProviderPage;
    private UploadsPage uploadsPage;
    private CameraPage cameraPage;

    //APIs to call
    private ShareAPI shareAPI;
    private FilesAPI filesAPI;
    private GraphAPI graphAPI;
    private TrashbinAPI trashbinAPI;
    private AuthAPI authAPI;

    public World() {
    }

    public LoginPage getLoginPage() {
        if (loginPage == null)
            loginPage = new LoginPage();
        return loginPage;
    }

    public FileListPage getFileListPage() {
        if (fileListPage == null)
            fileListPage = new FileListPage();
        return fileListPage;
    }

    public InputNamePage getInputNamePage() {
        if (inputNamePage == null)
            inputNamePage = new InputNamePage();
        return inputNamePage;
    }

    public FolderPickerPage getFolderPickerPage() {
        if (folderPickerPage == null)
            folderPickerPage = new FolderPickerPage();
        return folderPickerPage;
    }

    public RemoveDialogPage getRemoveDialogPage() {
        if (removeDialogPage == null)
            removeDialogPage = new RemoveDialogPage();
        return removeDialogPage;
    }

    public DetailsPage getDetailsPage() {
        if (detailsPage == null)
            detailsPage = new DetailsPage();
        return detailsPage;
    }

    public SharePage getSharePage() {
        if (sharePage == null)
            sharePage = new SharePage();
        return sharePage;
    }

    public PublicLinksPage getPublicLinksPage() {
        if (publicLinksPage == null)
            publicLinksPage = new PublicLinksPage();
        return publicLinksPage;
    }

    public SearchShareePage getSearchShareePage() {
        if (searchShareePage == null)
            searchShareePage = new SearchShareePage();
        return searchShareePage;
    }

    public PrivateSharePage getPrivateSharePage() {
        if (privateSharePage == null)
            privateSharePage = new PrivateSharePage();
        return privateSharePage;
    }

    public SpacesPage getSpacesPage() {
        if (spacesPage == null)
            spacesPage = new SpacesPage();
        return spacesPage;
    }

    public DocumentProviderPage getDocumentProviderPage() {
        if (documentProviderPage == null)
            documentProviderPage = new DocumentProviderPage();
        return documentProviderPage;
    }

    public UploadsPage getUploadsPage() {
        if (uploadsPage == null)
            uploadsPage = new UploadsPage();
        return uploadsPage;
    }

    public CameraPage getCameraPage() {
        if (cameraPage == null)
            cameraPage = new CameraPage();
        return cameraPage;
    }

    public ShareAPI getShareAPI()
            throws IOException {
        if (shareAPI == null)
            shareAPI = new ShareAPI();
        return shareAPI;
    }

    public FilesAPI getFilesAPI()
            throws IOException {
        if (filesAPI == null)
            filesAPI = new FilesAPI();
        return filesAPI;
    }

    public GraphAPI getGraphAPI()
            throws IOException {
        if (graphAPI == null)
            graphAPI = new GraphAPI();
        return graphAPI;
    }

    public TrashbinAPI getTrashbinAPI()
            throws IOException {
        if (trashbinAPI == null)
            trashbinAPI = new TrashbinAPI();
        return trashbinAPI;
    }

    public AuthAPI getAuthAPI() {
        if (authAPI == null)
            authAPI = new AuthAPI();
        return authAPI;
    }
}
