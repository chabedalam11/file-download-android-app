package com.example.fajlehrabbi.appmcci;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fajlehrabbi.appmcci.Adapter.FileAdapter;
import com.example.fajlehrabbi.appmcci.Model.FileLists;
import com.example.fajlehrabbi.appmcci.Utilities.AppConstant;
import com.example.fajlehrabbi.appmcci.Utilities.PersistData;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.io.*;

import cz.msebera.android.httpclient.util.ByteArrayBuffer;

public class FileActivity extends AppCompatActivity {
    private static final String TAG="FileActivity";
    ArrayList<FileLists> comlists;
    private String username, token;
    ListView listView;
    private static FileAdapter adapter;
    Context con;
    ArrayList<FileLists> file_list = new ArrayList<FileLists>();
    String URL="http://www.iiswc.org/iiswc2009/sample.doc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        con = this;
        listView=(ListView)findViewById(R.id.list);
        username = PersistData.getStringData(con, AppConstant.user_name);
        token = PersistData.getStringData(con, AppConstant.API_TOKEN);

        file_list= (ArrayList<FileLists>) getIntent().getSerializableExtra("file_list");
        /*for (FileLists file : file_list){
            Log.d(TAG, "value : "+file.getFile_upload());
        }*/

        adapter= new FileAdapter(file_list,getApplicationContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            FileLists fileLists = (FileLists) adapterView.getItemAtPosition(i);
            new DownloadTask(con,fileLists.getFile_upload());
            }
        });
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
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            String type = "application/msword";
            intent.setDataAndType(uri, type);
            startActivity(intent);

        } catch (Exception e) {
            /*Log.e("ERROR", e.getMessage());
            Log.e("ERROR", "No PDF Viewer is found.!!!");
            System.out.println("No PDF Viewer is found.!!!");
            Toast.makeText(con,"No PDF Viewer is found.",Toast.LENGTH_LONG).show();*/
            e.printStackTrace();
        }
    }
}
