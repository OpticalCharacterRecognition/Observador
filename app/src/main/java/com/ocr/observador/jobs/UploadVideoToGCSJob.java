package com.ocr.observador.jobs;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.storage.StorageScopes;
import com.ocr.observador.MainActivity;
import com.ocr.observador.events.UploadVideoEvent;
import com.ocr.observador.utilities.Priority;
import com.orhanobut.logger.Logger;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.UUID;

/**
 * call to upload a picture from the camera to Google Cloud Storage
 * TODO: SECURITY PROBLEM remove the key from assets, find a way to do it
 */
public class UploadVideoToGCSJob extends Job {
    boolean responseOk = false;
    boolean retry = true;

    Context mContext;
    String mBucketName;
    byte[] mByteArray;
    int mCategory;

    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();


    public UploadVideoToGCSJob(byte[] byteArray, Context context, String bucketName, int category) {
        super(new Params(Priority.LOW).requireNetwork().groupBy("upload-file"));
        mByteArray = byteArray;
        mContext = context;
        mBucketName = bucketName;
        mCategory = category;
    }

    @Override
    public void onAdded() {
    }

    @Override
    public void onRun() throws Throwable {
        // convert key into class File. from inputStream to file. in an aux class.
        File file = createFileFromInputStream();

        NetHttpTransport httpTransport = new NetHttpTransport();

        String emailAddress = "382197999605-nc5h44q7v54mgn915eqvtd71is4r46jg@developer.gserviceaccount.com";

        // Google credentials
        GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId(emailAddress)
                .setServiceAccountScopes(Collections.singleton(StorageScopes.DEVSTORAGE_FULL_CONTROL))
                .setServiceAccountPrivateKeyFromP12File(file)
                .build();

        String videoName = generateIdentifierForImage();

        String URI = "https://storage.googleapis.com/" + mBucketName + "/" + videoName + ".3gp";
        HttpRequestFactory requestFactory = httpTransport.createRequestFactory(credential);

        GenericUrl url = new GenericUrl(URI);

        byte[] mVideoBytes = mByteArray;

        HttpContent contentSend = new ByteArrayContent("video/3gp", mVideoBytes);

        HttpRequest putRequest = requestFactory.buildPutRequest(url, contentSend);

        HttpResponse response = putRequest.execute();
        if (response.isSuccessStatusCode()) {
            Logger.i(response.getStatusMessage() + "Uploading video");
            responseOk = true;
            MainActivity.bus.post(new UploadVideoEvent(UploadVideoEvent.Type.COMPLETED, 1, videoName, mCategory));
        }

    }

    /**
     * Creates a temporary file that stores the .p12 GCS authentication services
     * TODO: security concern about the file being on assets.
     *
     * @return a p12 file
     */
    private File createFileFromInputStream() {
        File file = null;
        try {
            // we take the key and put it in a temporary file
            AssetManager am = mContext.getAssets();
            InputStream inputStream = am.open("privatekey.p12"); //you should not put the key in assets in prod version.

            file = File.createTempFile("tempKeyFile", "p12");

            OutputStream outputStream = new FileOutputStream(file);
            byte buffer[] = new byte[1024];
            int length = 0;

            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            Logger.e("Can't create a file from input stream");
        }
        return file;
    }

    /**
     * Generates a random Identifier for the image
     *
     * @return random id
     */
    public String generateIdentifierForImage() {
        UUID id = UUID.randomUUID();
        return id.toString();
    }

    @Override
    protected void onCancel() {
        Logger.d("Error in UploadVideoEvent uploading");
        MainActivity.bus.post(new UploadVideoEvent(UploadVideoEvent.Type.COMPLETED, 99, null, mCategory));
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return retry;
    }
}
