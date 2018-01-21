package com.example.fajlehrabbi.appmcci.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fajlehrabbi.appmcci.Database.MCCIDBTransaction;
import com.example.fajlehrabbi.appmcci.DownloadTask;
import com.example.fajlehrabbi.appmcci.Model.AllCatSubCatFiles;
import com.example.fajlehrabbi.appmcci.Model.ComLists;
import com.example.fajlehrabbi.appmcci.Model.FileLists;
import com.example.fajlehrabbi.appmcci.Model.SigninResponse;
import com.example.fajlehrabbi.appmcci.Model.SubComLists;
import com.example.fajlehrabbi.appmcci.R;
import com.example.fajlehrabbi.appmcci.Retrofit.ApiClient;
import com.example.fajlehrabbi.appmcci.Retrofit.ApiInterface;
import com.example.fajlehrabbi.appmcci.Utilities.AppConstant;
import com.example.fajlehrabbi.appmcci.Utilities.PersistData;
import com.example.fajlehrabbi.appmcci.Utils;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="MainActivity";
    private String usernamestore, token;
    private TextView tvSignin, tvForgotPassword,msg1,msg2;
    private EditText etUserName, etPassword;
    ProgressBar progressBar;
    LinearLayout lLayout;
    private String userName, password, device_type;
    // private CheckBox signCheckBox;
    public static MainActivity signinactivity;
    Context con;
    MCCIDBTransaction dbTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        con = this;
        signinactivity = this;
        dbTransaction= new MCCIDBTransaction(con);
        initUI();
    }
    public void initUI() {
        tvSignin = (TextView) findViewById(R.id.tvSignIn);
        msg1 = (TextView) findViewById(R.id.msg1);
        msg2 = (TextView) findViewById(R.id.msg2);
        //tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        etUserName = (EditText) findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        lLayout = (LinearLayout) findViewById(R.id.lLayout);
        progressBar.setVisibility(View.GONE);
        lLayout.setVisibility(View.GONE);

        usernamestore = PersistData.getStringData(con, AppConstant.user_name);
        token = PersistData.getStringData(con, AppConstant.API_TOKEN);

        Log.d(TAG,"val >> "+usernamestore+"   and   "
                +token);
        if(usernamestore != null &&  !usernamestore.equals("") &&
                token != null &&  !token.equals("")){
            if(Utils.isInternetConnected(con)){
                progressBar.setVisibility(View.VISIBLE);
                checkToken(usernamestore,token);
            }else {
                Toast.makeText(con, "No Internet Connection", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(con,Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }

        }else {
            lLayout.setVisibility(View.VISIBLE);
        }

        tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.isInternetConnected(con)){
                    String firebase_token = PersistData.getStringData(con, AppConstant.firebase_token);
                    Log.e("Firebase TOKEN >>", firebase_token);
                    if (firebase_token == null && firebase_token == "") {
                        Toast.makeText(con, "Not available fb reg id ! please collect reg id before that !", Toast.LENGTH_LONG).show();
                    } else {
                        checkData();
                    }
                }else {
                    Toast.makeText(con, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void checkData() {
        msg1.setText("");
        msg2.setText("");
        if (TextUtils.isEmpty(etUserName.getText().toString())) {
            msg1.setText("The user name field is required");
        } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
            msg2.setText("The password field is required");
        } else {
            userName = etUserName.getText().toString();
            password = etPassword.getText().toString();
            device_type = "android";
            requestSignin();
        }
    }

    // request to leave group using retrofit
    public void requestSignin() {
        progressBar.setVisibility(View.VISIBLE);
        // retfit code
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<SigninResponse> call = apiService.signin(userName, password);
        call.enqueue(new Callback<SigninResponse>() {
            @Override
            public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                SigninResponse sr = response.body();
                Log.e("Response >>>", new GsonBuilder().setPrettyPrinting().create().toJson(response).toString());
                if (sr.getStatus().equalsIgnoreCase("true")) {
                    PersistData.setStringData(con, AppConstant.API_TOKEN, sr.getToken());
                    PersistData.setStringData(con, AppConstant.full_name, sr.getData().getName());
                    PersistData.setStringData(con, AppConstant.password, password);
                    PersistData.setStringData(con, AppConstant.user_name, userName);
                    if(!dbTransaction.isUserExist(sr)){
                        dbTransaction.insertLOGIN(sr);
                    }
                    checkToken(userName,sr.getToken());
                } else {
                    progressBar.setVisibility(View.GONE);
                    msg1.setText("Error in User Name or Password");
                }
            }
            @Override
            public void onFailure(Call<SigninResponse> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(con, "Webservice not response", Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });
    }


    private void checkToken(String un,String tkn){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<AllCatSubCatFiles> call = apiService.showlist(un, tkn);
        call.enqueue(new Callback<AllCatSubCatFiles>() {
            @Override
            public void onResponse(Call<AllCatSubCatFiles> call, Response<AllCatSubCatFiles> response) {
                final AllCatSubCatFiles mr = response.body();
                if (mr.getStatus().equalsIgnoreCase("true")) {
                    //store data in local db
                    dbTransaction.deleteCOMMITTEE_LISTInfo();
                    ArrayList<ComLists> committee_list = mr.getData().getCommittee_list();
                    for(ComLists commitee : committee_list){
                        dbTransaction.insertCOMMITTEE_LIST(commitee);
                    }
                    dbTransaction.deleteSUB_COMMITTEE_LISTInfo();
                    ArrayList<SubComLists> sub_committee_list = mr.getData().getSub_committee_list();
                    for(SubComLists subCom : sub_committee_list){
                        dbTransaction.insertSUB_COMMITTEE(subCom);
                    }

                    dbTransaction.deleteFILE_LISTInfo();
                    ArrayList<FileLists> file_list = mr.getData().getFile_list();
                    for(FileLists file : file_list){
                        dbTransaction.insertFILE(file);
                        String downloadUrl= file.getFile_upload();
                        String downloadFileName = downloadUrl.substring(downloadUrl.lastIndexOf( '/' ),downloadUrl.length());//Create file name by picking download file name from URL
                        String fileLocation="NKDROID FILES"+ File.separator +downloadFileName;
                        File f = new File(Environment.getExternalStorageDirectory()+ File.separator + fileLocation);
                        if(!f.exists()){
                            new DownloadTask(con,file.getFile_upload(),"","");
                        }
                    }
                    Utils.mr = mr;
                    progressBar.setVisibility(View.GONE);
                    Intent intent= new Intent(con,Home.class);
                    intent.putExtra("mr", mr);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }else {
                    lLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<AllCatSubCatFiles> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(con, "Webservice not response", Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
