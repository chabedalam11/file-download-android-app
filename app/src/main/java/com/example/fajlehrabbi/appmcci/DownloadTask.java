package com.example.fajlehrabbi.appmcci;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.example.fajlehrabbi.appmcci.Utilities.Permissions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by SONU on 29/10/15.
 */
public class DownloadTask {

    private static final String TAG = "Download Task";
    private Context context;

    private String downloadUrl = "", downloadFileName = "",extension="",flag="";
    private ProgressDialog progressDialog;
    public DownloadTask(Context context, String downloadUrl,String extension,String flag) {
        this.context = context;
        this.downloadUrl = downloadUrl;
        this.extension = extension;
        this.flag = flag;
        downloadFileName = downloadUrl.substring(downloadUrl.lastIndexOf( '/' ),downloadUrl.length());//Create file name by picking download file name from URL
        Log.e(TAG, downloadFileName);
        //Start Downloading Task
        new DownloadingTask().execute();
    }

    private class DownloadingTask extends AsyncTask<Void, Void, Void> {
        File apkStorage = null;
        File outputFile = null;
        @Override
        protected void onPreExecute() {
            if(!flag.equals("")){
                Utils.showLoader(context);
            }
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {
            if(!flag.equals("")){
                Utils.hideLoader();
            }
            try {
                if (outputFile != null) {
                    //progressDialog.dismiss();
                    String fileLocation="NKDROID FILES"+ File.separator +downloadFileName;
                    if(flag.equalsIgnoreCase("file")){
                        openFile(fileLocation);
                    }else if(flag.equalsIgnoreCase("sync")){
                        Toast.makeText(context, "sync complete", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 3000);
                    Log.e(TAG, "Download Failed");
                    if(!flag.equals("")){
                        Toast.makeText(context, "Download Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

                //Change button text if exception occurs

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 3000);
                Log.e(TAG, "Download Failed with Exception - " + e.getLocalizedMessage());
                if(!flag.equals("")){
                    Toast.makeText(context, "Download Failed", Toast.LENGTH_SHORT).show();
                }
            }


            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                URL url = new URL(downloadUrl);//Create Download URl
                HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
                c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
                c.connect();//connect the URL Connection

                //If Connection response is not OK then show Logs
                if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.e(TAG, "Server returned HTTP " + c.getResponseCode()
                            + " " + c.getResponseMessage());
                }

                InputStream connectionIS = c.getInputStream();//Get InputStream for connection
                InputStream is =connectionIS;
                //Get File if SD card is present
                if (new CheckForSDCard().isSDCardPresent()) {
                /*//set run time permission
                new Permissions(context).checkWriteExternalStoragePermission();*/
                apkStorage = new File(
                            Environment.getExternalStorageDirectory() + "/"
                                    + "NKDROID FILES");
                    apkStorage.mkdirs();
                } else
                    Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();
                //If File is not present create directory
                if (!apkStorage.exists()) {
                    apkStorage.mkdirs();
                    Log.e(TAG, "Directory Created.");
                }

                outputFile = new File(apkStorage, downloadFileName);//Create Output file in Main File

                //Create New File if not present
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                    Log.e(TAG, "File Created");
                }

                FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location


                byte[] buffer = new byte[1024];//Set buffer type
                int len1 = 0;//init length
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);//Write new file
                }

                //Close all connection after doing task
                fos.close();
                is.close();

            } catch (Exception e) {
                //Read exception if something went wrong
                e.printStackTrace();
                outputFile = null;
                Log.e(TAG, "Download Error Exception " + e.getMessage());
                if(!flag.equals("")){
                    Toast.makeText(context, "Download Failed", Toast.LENGTH_SHORT).show();
                    Utils.hideLoader();
                }
            }
            return null;
        }
    }

    public void openFile(String path) {
        Log.d("filePath ", path);
        File file = new File(Environment.getExternalStorageDirectory()+ File.separator + path);
        Uri uri = FileProvider.getUriForFile(
                context,
                context.getApplicationContext()
                        .getPackageName() + ".provider", file);
        Log.d("uri", uri.toString());

        try {
            if(extension.equalsIgnoreCase("pdf")){
                /*Toast.makeText(context, "opening pdf.....", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(uri, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);*/

                Toast.makeText(context, "opening pdf.....", Toast.LENGTH_SHORT).show();
                // create new Intent
                Intent intent = new Intent(Intent.ACTION_VIEW);

                // set flag to give temporary permission to external app to use your FileProvider
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                /*// generate URI, I defined authority as the application ID in the Manifest, the last param is file I want to open
                String uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, file);
*/
                // I am opening a PDF file so I give it a valid MIME type
                intent.setDataAndType(uri, "application/pdf");

                // validate that the device can open your File!
                PackageManager pm = context.getPackageManager();
                if (intent.resolveActivity(pm) != null) {
                    context.startActivity(intent);
                }

            }else {
                Toast.makeText(context, "opening doc.....", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW);
                String type = "application/msword";
                intent.setDataAndType(uri, type);
                context.startActivity(intent);
            }


        } catch (Exception e) {
            Toast.makeText(context,"No Viewer found.",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


}
