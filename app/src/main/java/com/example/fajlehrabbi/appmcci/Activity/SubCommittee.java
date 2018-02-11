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
import android.widget.Toast;

import com.example.fajlehrabbi.appmcci.Adapter.CustomAdapter;
import com.example.fajlehrabbi.appmcci.Adapter.TaskAdapter;
import com.example.fajlehrabbi.appmcci.Database.MCCIDBTransaction;
import com.example.fajlehrabbi.appmcci.Model.AllCatSubCatFiles;
import com.example.fajlehrabbi.appmcci.Model.FileLists;
import com.example.fajlehrabbi.appmcci.Model.SubComLists;
import com.example.fajlehrabbi.appmcci.R;
import com.example.fajlehrabbi.appmcci.Retrofit.ApiClient;
import com.example.fajlehrabbi.appmcci.Retrofit.ApiInterface;
import com.example.fajlehrabbi.appmcci.Utilities.AppConstant;
import com.example.fajlehrabbi.appmcci.Utilities.PersistData;
import com.example.fajlehrabbi.appmcci.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCommittee extends AppCompatActivity {
    private static final String TAG="SubCommittee";
    ArrayList<SubComLists> comlists;
    private String username, token,fullname;
    TextView tvfullName;
    ListView listView;
    Context con;
    private static TaskAdapter adapter;
    MCCIDBTransaction dbTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcommittee);
        con = this;
        dbTransaction= new MCCIDBTransaction(con);
        listView=(ListView)findViewById(R.id.list);
        tvfullName=(TextView) findViewById(R.id.tvfullName);

        username = PersistData.getStringData(con, AppConstant.user_name);
        token = PersistData.getStringData(con, AppConstant.API_TOKEN);
        fullname = PersistData.getStringData(con, AppConstant.full_name);
        tvfullName.setText(fullname);

        /*checkNetwork*/
        if(Utils.isInternetConnected(con)){
            showOnlineData();
        }else{
            showOfflineData();
        }
    }

    private void showOnlineData(){
        Utils.showLoader(con);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<AllCatSubCatFiles> call = apiService.showlist(username, token);
        call.enqueue(new Callback<AllCatSubCatFiles>() {
            @Override
            public void onResponse(Call<AllCatSubCatFiles> call, Response<AllCatSubCatFiles> response) {
                final AllCatSubCatFiles mr = response.body();
                if (mr.getStatus().equalsIgnoreCase("true")) {
                    adapter= new TaskAdapter(mr.getData().getSub_committee_list(),getApplicationContext());
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            SubComLists subComLists = (SubComLists) adapterView.getItemAtPosition(i);
                            ArrayList<FileLists> file_list=mr.getData().getFile_list();
                            ArrayList<FileLists> new_file_list = new ArrayList<FileLists>();
                            for (FileLists file:file_list){
                                if (file.getCat_id().equals(subComLists.getCat_id()) && file.getSubcat_id().equals(subComLists.getId())){
                                    new_file_list.add(file);
                                }
                            }

                            Intent intent= new Intent(con,Datee.class);
                            intent.putExtra("file_list", new_file_list);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
                    Utils.hideLoader();
                } else {
                    Toast.makeText(con, mr.getStatus(), Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<AllCatSubCatFiles> call, Throwable t) {
                // Log error here since request failed
                Utils.hideLoader();
                Log.e(TAG, t.toString());
                Toast.makeText(con, "Webservice not response", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showOfflineData(){
        adapter= new TaskAdapter(dbTransaction.getSubCommList(),getApplicationContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SubComLists subComLists = (SubComLists) adapterView.getItemAtPosition(i);
                ArrayList<FileLists> file_list=dbTransaction.getFileList();
                ArrayList<FileLists> new_file_list = new ArrayList<FileLists>();
                for (FileLists file:file_list){
                    if (file.getCat_id().equals(subComLists.getCat_id()) && file.getSubcat_id().equals(subComLists.getId())){
                        new_file_list.add(file);
                    }
                }

                Intent intent= new Intent(con,Datee.class);
                intent.putExtra("file_list", new_file_list);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
    public void home1(View view){
        finish();
    }
    public void home(View view){
        Intent intent= new Intent(con,Home.class);
        intent.putExtra("mr", Utils.mr);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logout(View view){
        PersistData.setStringData(con, AppConstant.API_TOKEN, "");
        PersistData.setStringData(con, AppConstant.password, "");
        startActivity(new Intent(con, MainActivity.class));
        finish();
    }
}
