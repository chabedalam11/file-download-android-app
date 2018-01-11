package com.example.fajlehrabbi.appmcci;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fajlehrabbi.appmcci.Adapter.DateAdapter;
import com.example.fajlehrabbi.appmcci.Model.FileLists;
import com.example.fajlehrabbi.appmcci.Utilities.AppConstant;
import com.example.fajlehrabbi.appmcci.Utilities.PersistData;

import java.util.ArrayList;

public class Datee extends AppCompatActivity {
    private static final String TAG="Datee";
    ArrayList<FileLists> comlists;
    private String username, token;
    ListView listView;
    private static DateAdapter adapter;
    Context con;
    ArrayList<FileLists> file_list = new ArrayList<FileLists>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datee);
        con = this;
        listView=(ListView)findViewById(R.id.list);
        username = PersistData.getStringData(con, AppConstant.user_name);
        token = PersistData.getStringData(con, AppConstant.API_TOKEN);
        file_list= (ArrayList<FileLists>) getIntent().getSerializableExtra("file_list");
        for (FileLists file : file_list){
            Log.d(TAG, "value : "+file.getDate());
        }
        adapter= new DateAdapter(file_list,getApplicationContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FileLists fileLists = (FileLists) adapterView.getItemAtPosition(i);
                //Toast.makeText(con, "val : "+fileLists.getFile_upload(), Toast.LENGTH_SHORT).show();
                ArrayList<FileLists> new_file_list = new ArrayList<FileLists>();
                for (FileLists file : file_list){
                    if(fileLists.getDate() != null && fileLists.getDate().equals(file.getDate())){
                        new_file_list.add(file);
                    }
                }

                Intent intent= new Intent(con,FileActivity.class);
                intent.putExtra("file_list", new_file_list);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
