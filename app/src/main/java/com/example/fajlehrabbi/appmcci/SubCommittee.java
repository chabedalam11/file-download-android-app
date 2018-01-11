package com.example.fajlehrabbi.appmcci;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fajlehrabbi.appmcci.Adapter.TaskAdapter;
import com.example.fajlehrabbi.appmcci.Model.AllCatSubCatFiles;
import com.example.fajlehrabbi.appmcci.Model.ComLists;
import com.example.fajlehrabbi.appmcci.Model.FileLists;
import com.example.fajlehrabbi.appmcci.Model.SubComLists;
import com.example.fajlehrabbi.appmcci.Retrofit.ApiClient;
import com.example.fajlehrabbi.appmcci.Retrofit.ApiInterface;
import com.example.fajlehrabbi.appmcci.Utilities.AppConstant;
import com.example.fajlehrabbi.appmcci.Utilities.PersistData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCommittee extends AppCompatActivity {
    private static final String TAG="SubCommittee";
    ArrayList<SubComLists> comlists;
    private String username, token;
    ListView listView;
    Context con;
    private static TaskAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcommittee);
        con = this;
        listView=(ListView)findViewById(R.id.list);
        username = PersistData.getStringData(con, AppConstant.user_name);
        token = PersistData.getStringData(con, AppConstant.API_TOKEN);
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
                } else {
                    Toast.makeText(con, mr.getStatus(), Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<AllCatSubCatFiles> call, Throwable t) {
                // Log error here since request failed
                Log.e("ChatingActivity", t.toString());
            }
        });

    }
}
