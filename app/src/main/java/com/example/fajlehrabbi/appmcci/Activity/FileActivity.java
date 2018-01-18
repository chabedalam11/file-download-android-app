package com.example.fajlehrabbi.appmcci.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fajlehrabbi.appmcci.Adapter.FileAdapter;
import com.example.fajlehrabbi.appmcci.DownloadTask;
import com.example.fajlehrabbi.appmcci.Model.FileLists;
import com.example.fajlehrabbi.appmcci.R;
import com.example.fajlehrabbi.appmcci.Utilities.AppConstant;
import com.example.fajlehrabbi.appmcci.Utilities.PersistData;
import com.example.fajlehrabbi.appmcci.Utils;

import java.io.File;
import java.util.ArrayList;

public class FileActivity extends AppCompatActivity {
    private static final String TAG="FileActivity";
    ArrayList<FileLists> comlists;
    private String username, token,fullName;
    ListView listView;
    TextView tvfullName;
    private static FileAdapter adapter;
    Context con;
    ArrayList<FileLists> file_list = new ArrayList<FileLists>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        con = this;
        listView=(ListView)findViewById(R.id.list);
        tvfullName=(TextView) findViewById(R.id.tvfullName);
        username = PersistData.getStringData(con, AppConstant.user_name);
        token = PersistData.getStringData(con, AppConstant.API_TOKEN);
        fullName = PersistData.getStringData(con, AppConstant.full_name);
        tvfullName.setText(fullName);
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
            String downloadUrl= fileLists.getFile_upload();
            String extension = fileLists.getFile_extension();
            String downloadFileName = downloadUrl.substring(downloadUrl.lastIndexOf( '/' ),downloadUrl.length());//Create file name by picking download file name from URL
            String fileLocation="NKDROID FILES"+ File.separator +downloadFileName;
            if(!openFile(fileLocation,extension)){
                if(Utils.isInternetConnected(con)){
                    new DownloadTask(con,downloadUrl,extension,"file");
                }else {
                    Toast.makeText(con, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
            }
        });
    }


    public void home(View view){
        Intent intent= new Intent(con,Home.class);
        intent.putExtra("mr", Utils.mr);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void logout(View view){
        PersistData.setStringData(con, AppConstant.API_TOKEN, "");
        PersistData.setStringData(con, AppConstant.password, "");
        startActivity(new Intent(con, MainActivity.class));
        finish();
    }

    public boolean openFile(String path,String extension) {
        Log.d(TAG, path);
        File file = new File(Environment.getExternalStorageDirectory()+ File.separator + path);
        if(!file.exists()){
            Log.d(TAG, "File not found");
            return false;
        }
        Uri uri = Uri.fromFile(file);
        Log.d(TAG, uri.toString());

        try {
            /*Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);*/
            //Uri uri = Uri.parse("file://"+file.getAbsolutePath());
            if(extension.equalsIgnoreCase("pdf")){
                Toast.makeText(con, "opening pdf.....", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }else {
                Toast.makeText(con, "opening doc.....", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW);
                String type = "application/msword";
                intent.setDataAndType(uri, type);
                startActivity(intent);
            }


        } catch (Exception e) {
            /*Log.e("ERROR", e.getMessage());
            Log.e("ERROR", "No PDF Viewer is found.!!!");
            System.out.println("No PDF Viewer is found.!!!");*/
            Toast.makeText(con,"No Viewer found.",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return true;
    }
}
