package com.example.fajlehrabbi.appmcci;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

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
            super.onPreExecute();
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Downloading...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                if (outputFile != null) {
                    progressDialog.dismiss();
                    String fileLocation="NKDROID FILES"+ File.separator +downloadFileName;
                    //Toast.makeText(context, "Downloaded Successfully", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(context, "opening ..."+fileLocation, Toast.LENGTH_SHORT).show();
//                  new FileActivity().openFile(fileLocation);
                    if(flag.equalsIgnoreCase("file")){openFile(fileLocation);}
                } else {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 3000);

                    Log.e(TAG, "Download Failed");

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


                //Get File if SD card is present
                if (new CheckForSDCard().isSDCardPresent()) {

                    apkStorage = new File(
                            Environment.getExternalStorageDirectory() + "/"
                                    + "NKDROID FILES");
                } else
                    Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

                //If File is not present create directory
                if (!apkStorage.exists()) {
                    apkStorage.mkdir();
                    Log.e(TAG, "Directory Created.");
                }

                outputFile = new File(apkStorage, downloadFileName);//Create Output file in Main File

                //Create New File if not present
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                    Log.e(TAG, "File Created");
                }

                FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

                InputStream is = c.getInputStream();//Get InputStream for connection

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
            }

            return null;
        }
    }

    public void openFile(String path) {
        Log.d("filePath ", path);
        File file = new File(Environment.getExternalStorageDirectory()+ File.separator + path);
        Uri uri = Uri.fromFile(file);
        Log.d("uri", uri.toString());

        try {
            /*Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);*/
            //Uri uri = Uri.parse("file://"+file.getAbsolutePath());
            if(extension.equalsIgnoreCase("pdf")){
                Toast.makeText(context, "opening pdf.....", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }else {
                Toast.makeText(context, "opening doc.....", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW);
                String type = "application/msword";
                intent.setDataAndType(uri, type);
                context.startActivity(intent);
            }


        } catch (Exception e) {
            /*Log.e("ERROR", e.getMessage());
            Log.e("ERROR", "No PDF Viewer is found.!!!");
            System.out.println("No PDF Viewer is found.!!!");*/
            Toast.makeText(context,"No Viewer found.",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }



}
