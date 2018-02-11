package com.example.fajlehrabbi.appmcci.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fajlehrabbi.appmcci.Adapter.DateAdapter;
import com.example.fajlehrabbi.appmcci.Model.FileLists;
import com.example.fajlehrabbi.appmcci.R;
import com.example.fajlehrabbi.appmcci.Utilities.AppConstant;
import com.example.fajlehrabbi.appmcci.Utilities.PersistData;
import com.example.fajlehrabbi.appmcci.Utils;

import java.util.ArrayList;

public class Datee extends AppCompatActivity {
    private static final String TAG="Datee";
    ArrayList<FileLists> comlists;
    private String username, token,fullName;
    ListView listView;
    TextView tvfullName;
    private static DateAdapter adapter;
    Context con;
    ArrayList<FileLists> file_list = new ArrayList<FileLists>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datee);
        con = this;
        listView=(ListView)findViewById(R.id.list);
        tvfullName=(TextView) findViewById(R.id.tvfullName);
        username = PersistData.getStringData(con, AppConstant.user_name);
        token = PersistData.getStringData(con, AppConstant.API_TOKEN);
        fullName = PersistData.getStringData(con, AppConstant.full_name);
        tvfullName.setText(fullName);

        file_list= (ArrayList<FileLists>) getIntent().getSerializableExtra("file_list");
        ArrayList<String> adapterDateList = new ArrayList<String>();
        for (FileLists file : file_list){
            if(!adapterDateList.contains(file.getDate())){
                Log.d(TAG, "value : "+file.getDate());
                adapterDateList.add(file.getDate());
            }
        }
        adapter= new DateAdapter(adapterDateList,getApplicationContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String date = (String) adapterView.getItemAtPosition(i);
                //Toast.makeText(con, "val : "+date.getFile_upload(), Toast.LENGTH_SHORT).show();
                ArrayList<FileLists> new_file_list = new ArrayList<FileLists>();
                for (FileLists file : file_list){
                    if(date != null && date.equals(file.getDate())){
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

    public void home(View view){
        Intent intent= new Intent(con,Home.class);
        intent.putExtra("mr", Utils.mr);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void home1(View view){
        finish();
    }

    public void logout(View view){
        PersistData.setStringData(con, AppConstant.API_TOKEN, "");
        PersistData.setStringData(con, AppConstant.password, "");
        startActivity(new Intent(con, MainActivity.class));
        finish();
    }
}
