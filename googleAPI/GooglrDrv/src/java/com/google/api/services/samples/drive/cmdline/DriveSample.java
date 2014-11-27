/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.api.services.samples.drive.cmdline;

/**
 *
 * @author salim
 */
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.FileCredentialStore;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.common.base.Preconditions;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Collections;

/**
 * A sample application that runs multiple requests against the Drive API. The
 * requests this sample makes are:
 * <ul>
 * <li>Does a resumable media upload</li>
 * <li>Updates the uploaded file by renaming it</li>
 * <li>Does a resumable media download</li>
 * <li>Does a direct media upload</li>
 * <li>Does a direct media download</li>
 * </ul>
 *
 * @author rmistry@google.com (Ravi Mistry)
 * https://code.google.com/p/google-api-java-client/source/browse/drive-cmdline-sample/src/main/java/com/google/api/services/samples/drive/cmdline/DriveSample.java?repo=samples&r=08555cd2a27be66dc97505e15c60853f47d84b5a
 */
public class DriveSample {

    private static final String UPLOAD_FILE_PATH = "./src/up/pic.jpg";
    private static final String DIR_FOR_DOWNLOADS = "./src/down";//"Enter Download Directory";
    private static final java.io.File UPLOAD_FILE = new java.io.File(UPLOAD_FILE_PATH);

    /**
     * Global instance of the HTTP transport.
     */
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    /**
     * Global Drive API client.
     */
    private static Drive drive;

    /**
     * Salim Build an authorization flow and store it as a static class
     * attribute.
     *
     * @return GoogleAuthorizationCodeFlow instance.
     * @throws IOException Unable to load client_secrets.json.
     */
    static GoogleAuthorizationCodeFlow buildFlow() throws IOException {
        System.out.println("buildFlow...begins");
        GoogleAuthorizationCodeFlow flow;
        InputStream is = new FileInputStream("./src/main/resources/client_secrets.json");
        Reader reader = new InputStreamReader(is);
        HttpTransport httpTransport = new NetHttpTransport();
        JacksonFactory jsonFactory = new JacksonFactory();
        //gets client secrets
        GoogleClientSecrets clientSecrets;
        clientSecrets = GoogleClientSecrets.load(jsonFactory, reader);
        if (clientSecrets.getDetails().getClientId().startsWith("Enter") || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println(
                    "Enter Client ID and Secret from https://code.google.com/apis/console/?api=drive "
                    + "into drive-cmdline-sample/src/main/resources/client_secrets.json");
            System.exit(1);
        }
        flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, clientSecrets, Collections.singleton(DriveScopes.DRIVE_FILE))
                .setAccessType("offline").setApprovalPrompt("force").build();
        System.out.println("buildFlow...Ends");
        return flow;
    }

    private static Credential authorizeV2() throws Exception {
        System.out.println("authorizeV2...begins");
        GoogleAuthorizationCodeFlow flow = buildFlow();
        System.out.println("authorizeV2...ends");
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    /**
     * Authorizes the installed application to access user's protected data.
     */
    private static Credential authorize() throws Exception {
        // load client secrets
        //*Salim
        InputStream is = new FileInputStream("./src/main/resources/client_secrets.json");
        Reader reader = new InputStreamReader(is);
        //GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, DriveSample.class.getResourceAsStream("/client_secrets.json"));  
        //gets client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, reader);
        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println(
                    "Enter Client ID and Secret from https://code.google.com/apis/console/?api=drive "
                    + "into drive-cmdline-sample/src/main/resources/client_secrets.json");
            System.exit(1);
        }
        // set up file credential store
        FileCredentialStore credentialStore = new FileCredentialStore(new java.io.File(
                System.getProperty("user.home"), ".credentials/drive.json"), JSON_FACTORY);
        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets,
                Collections.singleton(DriveScopes.DRIVE_FILE)).setCredentialStore(credentialStore).build();
        // authorize
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public static void main(String[] args) {
        System.out.println("test starts..");
        Preconditions.checkArgument(!UPLOAD_FILE_PATH.startsWith("Enter ")
                && !DIR_FOR_DOWNLOADS.startsWith("Enter "),
                "Please enter the upload file path and download directory in %s", DriveSample.class);

        try {
            try {
                // authorization
                //Credential credential = authorize();//commented by salim
                Credential credential = authorizeV2();
                System.out.println("getting credentials, creating the authorized service..");
                // set up the global Drive instance
                drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(
                        "Google-DriveSample/1.0").build();
                System.out.println("authorized service  created");
                // run commands
                View.header1("Starting Resumable Media Upload");
                File uploadedFile = uploadFile(false);

                View.header1("Updating Uploaded File Name");
                File updatedFile = updateFileWithTestSuffix(uploadedFile.getId());

                View.header1("Starting Resumable Media Download");
                downloadFile(false, updatedFile);

                View.header1("Starting Simple Media Upload");
                uploadedFile = uploadFile(true);

                View.header1("Starting Simple Media Download");
                downloadFile(true, uploadedFile);

                View.header1("Success!");
                System.out.println("finish.");
                return;
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.exit(1);
    }

    /**
     * Uploads a file using either resumable or direct media upload.
     */
    private static File uploadFile(boolean useDirectUpload) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setTitle(UPLOAD_FILE.getName());

        InputStreamContent mediaContent = new InputStreamContent("image/jpeg", new BufferedInputStream(
                new FileInputStream(UPLOAD_FILE)));
        mediaContent.setLength(UPLOAD_FILE.length());

        Drive.Files.Insert insert = drive.files().insert(fileMetadata, mediaContent);
        MediaHttpUploader uploader = insert.getMediaHttpUploader();
        uploader.setDirectUploadEnabled(useDirectUpload);
        uploader.setProgressListener(new FileUploadProgressListener());
        return insert.execute();
    }

    /**
     * Updates the name of the uploaded file to have a "drivetest-" prefix.
     */
    private static File updateFileWithTestSuffix(String id) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setTitle("drivetest-" + UPLOAD_FILE.getName());

        Drive.Files.Update update = drive.files().update(id, fileMetadata);
        return update.execute();
    }

    /**
     * Downloads a file using either resumable or direct media download.
     */
    private static void downloadFile(boolean useDirectDownload, File uploadedFile)
            throws IOException {
        // create parent directory (if necessary)
        java.io.File parentDir = new java.io.File(DIR_FOR_DOWNLOADS);
        if (!parentDir.exists() && !parentDir.mkdirs()) {
            throw new IOException("Unable to create parent directory");
        }
        OutputStream out = new FileOutputStream(new java.io.File(parentDir, uploadedFile.getTitle()));

        Drive.Files.Get get = drive.files().get(uploadedFile.getId());
        MediaHttpDownloader downloader = get.getMediaHttpDownloader();
        downloader.setDirectDownloadEnabled(useDirectDownload);
        downloader.setProgressListener(new FileDownloadProgressListener());
        downloader.download(new GenericUrl(uploadedFile.getDownloadUrl()), out);
    }
}
