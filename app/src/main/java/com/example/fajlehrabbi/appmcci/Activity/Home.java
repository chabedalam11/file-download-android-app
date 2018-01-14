package com.example.fajlehrabbi.appmcci.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fajlehrabbi.appmcci.Adapter.CustomAdapter;
import com.example.fajlehrabbi.appmcci.Database.MCCIDBTransaction;
import com.example.fajlehrabbi.appmcci.Model.AllCatSubCatFiles;
import com.example.fajlehrabbi.appmcci.Model.ComLists;
import com.example.fajlehrabbi.appmcci.Model.FileLists;
import com.example.fajlehrabbi.appmcci.R;
import com.example.fajlehrabbi.appmcci.Utilities.AppConstant;
import com.example.fajlehrabbi.appmcci.Utilities.PersistData;
import com.example.fajlehrabbi.appmcci.Utils;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private static final String TAG="Home";
    boolean hardwareBackControll;
    ArrayList<ComLists> comlists;
    private String username, token,fullname;
    ListView listView;
    TextView usernameFullName,home,logout;
    //private ImageView imgContinue,imgContinue1,imgContinue2,imgContinue3;
    private static CustomAdapter adapter;
    Context con;
    AllCatSubCatFiles mr;
    MCCIDBTransaction dbTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        con = this;
        dbTransaction= new MCCIDBTransaction(con);
        listView=(ListView)findViewById(R.id.list);
        home=(TextView)findViewById(R.id.home);
        logout=(TextView)findViewById(R.id.logout);
        usernameFullName=(TextView)findViewById(R.id.usernameFullName);

        username = PersistData.getStringData(con, AppConstant.user_name);
        token = PersistData.getStringData(con, AppConstant.API_TOKEN);
        fullname = PersistData.getStringData(con, AppConstant.full_name);
        usernameFullName.setText(fullname);

        /*checkNetwork*/
        //

        if(Utils.isInternetConnected(con)){
            showOnlineData();
        }else {
            showOfflineData();
        }


        /*home button*/
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(con, "Click saHome", Toast.LENGTH_SHORT).show();
            }
        });

        /*logout button*/
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersistData.setStringData(con, AppConstant.API_TOKEN, "");
                PersistData.setStringData(con, AppConstant.password, "");
                startActivity(new Intent(con, MainActivity.class));
                finish();
            }
        });
    }

    //in backpress if drawer is open close it
    @Override
    public void onBackPressed() {
        if (hardwareBackControll) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            hardwareBackControll = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    hardwareBackControll = false;
                }
            }, 3 * 1000);

        }
    }

    private void showOnlineData(){
        mr = (AllCatSubCatFiles) getIntent().getSerializableExtra("mr");
        adapter= new CustomAdapter(mr.getData().getCommittee_list(),getApplicationContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ComLists comLists = (ComLists) adapterView.getItemAtPosition(i);
                //Toast.makeText(Home.this, comLists.getId(), Toast.LENGTH_SHORT).show();
                if(comLists.getId() != null && comLists.getId().equals("1")){
                    ArrayList<FileLists> file_list=mr.getData().getFile_list();
                    ArrayList<FileLists> new_file_list = new ArrayList<FileLists>();
                    for (FileLists file:file_list){
                        if (file.getCat_id().equals("1")){
                            new_file_list.add(file);
                        }
                    }
                    Intent intent= new Intent(con,Datee.class);
                    intent.putExtra("file_list", new_file_list);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);


                }else if(comLists.getId() != null && comLists.getId().equals("2")){
                    Intent intent= new Intent(con,SubCommittee.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }


    private void showOfflineData(){
        ArrayList<ComLists> comLists = dbTransaction.getCommList();
        adapter= new CustomAdapter(comLists,getApplicationContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ComLists comLists = (ComLists) adapterView.getItemAtPosition(i);
                //Toast.makeText(Home.this, comLists.getId(), Toast.LENGTH_SHORT).show();
                if(comLists.getId() != null && comLists.getId().equals("1")){
                    ArrayList<FileLists> file_list=dbTransaction.getFileList();
                    ArrayList<FileLists> new_file_list = new ArrayList<FileLists>();
                    for (FileLists file:file_list){
                        if (file.getCat_id().equals("1")){
                            new_file_list.add(file);
                        }
                    }
                    Intent intent= new Intent(con,Datee.class);
                    intent.putExtra("file_list", new_file_list);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }else if(comLists.getId() != null && comLists.getId().equals("2")){
                    Intent intent= new Intent(con,SubCommittee.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }

}
