package com.example.fajlehrabbi.appmcci;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fajlehrabbi.appmcci.Model.SigninResponse;
import com.example.fajlehrabbi.appmcci.Retrofit.ApiClient;
import com.example.fajlehrabbi.appmcci.Retrofit.ApiInterface;
import com.example.fajlehrabbi.appmcci.Utilities.API_URL;
import com.example.fajlehrabbi.appmcci.Utilities.AppConstant;
import com.example.fajlehrabbi.appmcci.Utilities.PersistData;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView tvSignin, tvForgotPassword;
    private EditText etUserName, etPassword;
    private String userName, password, device_type;
    // private CheckBox signCheckBox;
    public static MainActivity signinactivity;
    Context con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        con = this;
        signinactivity = this;
        initUI();
    }
    public void initUI() {
        tvSignin = (TextView) findViewById(R.id.tvSignIn);
        //tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        etUserName = (EditText) findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        //signCheckBox = (CheckBox) findViewById(R.id.signCheckBox);

        /*// checkbox state set
        if (PersistData.getStringData(con, AppConstant.isSignin).equalsIgnoreCase("true")) {
            signCheckBox.setChecked(true);
        } else {
            signCheckBox.setChecked(false);
        }*/


        tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // checkbox state save
               /* if (signCheckBox.isChecked()) {
                    PersistData.setStringData(con, AppConstant.isSignin, "true");
                } else {
                    PersistData.setStringData(con, AppConstant.isSignin, "false");
                }*/

                String firebase_token = PersistData.getStringData(con, AppConstant.firebase_token);
                Log.e("Firebase TOKEN >>", firebase_token);
                if (firebase_token == null && firebase_token == "") {
                    Toast.makeText(con, "Not available fb reg id ! please collect reg id before that !", Toast.LENGTH_LONG).show();
                } else {
                    checkData();
                }

            }
        });
      /*  tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(con, ForgotPasswordActivity.class));
            }
        });*/
    }
    private void checkData() {

        if (TextUtils.isEmpty(etUserName.getText().toString())) {
            // AlertMessage.showMessage(con, getString(R.string.Status),
            //getString(R.string.PleaseEnterUserNam));
        } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
            // AlertMessage.showMessage(con, getString(R.string.Status),
            // getString(R.string.PleaseEnterUserPassword));
        } else {
            userName = etUserName.getText().toString();
            password = etPassword.getText().toString();
            device_type = "android";
            requestSignin();
            // push_id = PersistData.getStringData(con, AppConstant.GCMID);
//            String url = API_URL.SIGNIN_URL;
//            logIn(url);


        }
    }
    public void logIn(final String url) {

       /* if (!NetInfo.isOnline(con)) {
            AlertMessage.showMessage(con, getString(R.string.app_name), "No internet!");
            return;
        }

        Log.e("URL : ", url);
        // final BusyDialog busyNow = new BusyDialog(con, true, false);
        final ProgressDialog busyNow = new ProgressDialog(con);
        busyNow.setMessage("loading");
        // pd.show();
        busyNow.show();
        Executors.newSingleThreadScheduledExecutor().submit(new Runnable() {
            String response = "";

            @Override
            public void run() {

                Map<String, String> param = new HashMap();

                param.put("email", userName);
                param.put("password", password);
                param.put("GCM", PersistData.getStringData(con, AppConstant.firebase_token));

                try {
                    response = MyHttpClient.post(url).form(param).body();
                } catch (Exception e) {
                    // TODO: handle exception
                    Log.e("MYAPP", "exception", e);
                    if (busyNow != null) {
                        busyNow.cancel();
                    }
                }

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if (busyNow != null) {
                            busyNow.cancel();
                        }
                        try {
                            Log.e("Response", ">>" + new String(response));
                            if (!TextUtils.isEmpty(new String(response))) {
                                Gson g = new Gson();
                                SigninResponse sr = g.fromJson(new String(response), SigninResponse.class);
                                String status = sr.getStatus();

                                if (status.equalsIgnoreCase("true")) {
                                    Toast.makeText(con, sr.getMessage(), Toast.LENGTH_SHORT).show();
                                    PersistData.setStringData(con, AppConstant.API_TOKEN, sr.getAPITOKEN());
                                    PersistData.setStringData(con, AppConstant.user_id, sr.getUser().getUser_id());
                                    PersistData.setStringData(con, AppConstant.user_name, sr.getUser().getUsername());
                                    PersistData.setStringData(con, AppConstant.org_id, sr.getUser().getOrg_id());
                                    PersistData.setStringData(con, AppConstant.user_img, sr.getUser().getUser_img());
                                    PersistData.setStringData(con, AppConstant.display_name, sr.getUser().getDisplay_name());
                                    PersistData.setStringData(con, AppConstant.user_email, sr.getUser().getUser_email());
                                    PersistData.setStringData(con, AppConstant.full_name, sr.getUser().getFull_name());
                                    PersistData.setStringData(con, AppConstant.mobile_no, sr.getUser().getMobile_number());
                                    PersistData.setStringData(con, AppConstant.designation, sr.getUser().getDesignation());
                                    PersistData.setStringData(con, AppConstant.address, sr.getUser().getAddress());
                                    PersistData.setStringData(con, AppConstant.dob, sr.getUser().getDob());
                                    finish();

                                    // get all user
                                    AppConstant.chatAlllist.clear();
                                    requestChatListUrl(API_URL.CHAT_LIST);

                                 *//*   if(PersistData.getStringData(con,AppConstant.notificationcClick).equalsIgnoreCase("PUSH")){
                                        startActivity(new Intent(con, ChatingActivity.class));
                                    }else{
                                        startActivity(new Intent(con, NavigationDrawerActivity.class));
                                    }
*//*
                                    startActivity(new Intent(con, NavigationDrawerActivity.class));

                                } else {
                                    Toast.makeText(con, sr.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }


                        } catch (final Exception e) {

                            e.printStackTrace();
                        }


                    }
                });
            }
        });*/

    }
    public void requestChatListUrl(final String url) {

      /*  if (!NetInfo.isOnline(con)) {
            AlertMessage.showMessage(con, getString(R.string.app_name), "No internet!");
            return;
        }

        Log.e("URL : ", url);
        // final BusyDialog busyNow = new BusyDialog(con, true, false);


        Executors.newSingleThreadScheduledExecutor().submit(new Runnable() {*/
            /*String response = "";

            @Override
            public void run() {

                Map<String, String> param = new HashMap();

                param.put("mid", PersistData.getStringData(con, AppConstant.user_id));
                param.put("memail", PersistData.getStringData(con, AppConstant.user_email));
                param.put("org_id", PersistData.getStringData(con, AppConstant.org_id));
                param.put("APITOKEN", PersistData.getStringData(con, AppConstant.API_TOKEN));

                try {
                    response = MyHttpClient.post(url).form(param).body();
                } catch (Exception e) {
                    // TODO: handle exception
                    Log.e("MYAPP", "exception", e);

                }

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                        try {
                            Log.e("Response", ">>" + new String(response));
                            if (!TextUtils.isEmpty(new String(response))) {
                                Gson g = new Gson();
                                ChatListResponse clr = g.fromJson(new String(response), ChatListResponse.class);

                                // String status = sr.getStatus();


                                if (clr.getContacts().size() > 0) {
                                    AppConstant.contactsList = clr.getContacts();
                                    for (int i = 0; clr.getContacts().size() > i; i++) {
                                        ChatListData ob = new ChatListData(clr.getContacts().get(i).getDisplay_name(), clr.getContacts().get(i).getEmail(), clr.getContacts().get(i).getHas_online(), "contact", clr.getContacts().get(i).getImg(), clr.getContacts().get(i).getID());
                                       *//* ChatListData ob=new ChatListData();
                                        ob.setName(clr.getContacts().get(i).getDisplay_name());
                                        ob.setEmail_id(clr.getContacts().get(i).getEmail());
                                        ob.setStatus(clr.getContacts().get(i).getHas_online());*//*
                                        AppConstant.chatAlllist.add(ob);
                                    }

                                }
                                if (clr.getGroup_contacts().size() > 0) {
                                    AppConstant.groupChatList = clr.getGroup_contacts();

                                    for (int i = 0; clr.getGroup_contacts().size() > i; i++) {
                                        ChatListData ob = new ChatListData(clr.getGroup_contacts().get(i).getGroup_name(), clr.getGroup_contacts().get(i).getGroup_id(), "true", "group", "", "");
                                       *//* ChatListData ob=new ChatListData();
                                        ob.setName(clr.getGroup_contacts().get(i).getGroup_name());
                                        ob.setEmail_id(clr.getGroup_contacts().get(i).getGroup_id());
                                        ob.setStatus("true");*//*
                                        AppConstant.chatAlllist.add(ob);

                                    }

                                }
                                if (clr.getProject_chat_list().size() > 0) {
                                    AppConstant.projectChatList = clr.getProject_chat_list();
                                    for (int i = 0; clr.getProject_chat_list().size() > i; i++) {
                                        ChatListData ob = new ChatListData(clr.getProject_chat_list().get(i).getTitle(), clr.getProject_chat_list().get(i).getId(), "true", "project", "", "");
                                       *//* ChatListData ob=new ChatListData();
                                        ob.setName(clr.getProject_chat_list().get(i).getTitle());
                                        ob.setEmail_id(clr.getGroup_contacts().get(i).getGroup_id());
                                        ob.setStatus("true");*//*
                                        AppConstant.chatAlllist.add(ob);

                                    }

                                }

                                Log.e("Contactlist size >>", "" + AppConstant.contactsList.size());
                                Log.e("grouplist size >>", "" + AppConstant.groupChatList.size());
                                Log.e("projeclist size >>", "" + AppConstant.projectChatList.size());


                            }


                        } catch (final Exception e) {

                            e.printStackTrace();
                        }


                    }
                });
            }
        });*/


    }
    // request to leave group using retrofit
    public void requestSignin() {

        // if (!NetInfo.isOnline(con)) {
        //  AlertMessage.showMessage(con, getString(R.string.app_name), "No internet!");
        //  return;
        //}

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
                    // save updated data

                    //Toast.makeText(con, sr.getMessage(), Toast.LENGTH_SHORT).show();
                    // PersistData.setStringData(con, AppConstant.API_TOKEN, sr.getAPITOKEN());
                    //PersistData.setStringData(con, AppConstant.user_id, sr.getUser().getUser_id());
                    // PersistData.setStringData(con, AppConstant.user_name, sr.getUser().getUsername());
                    // PersistData.setStringData(con, AppConstant.org_id, sr.getUser().getOrg_id());
                    // PersistData.setStringData(con, AppConstant.user_img, sr.getUser().getUser_img());
                    // PersistData.setStringData(con, AppConstant.display_name, sr.getUser().getDisplay_name());
                    // PersistData.setStringData(con, AppConstant.user_email, sr.getUser().getUser_email());
                    // PersistData.setStringData(con, AppConstant.full_name, sr.getUser().getFull_name());
                    // PersistData.setStringData(con, AppConstant.mobile_no, sr.getUser().getMobile_number());
                    //  PersistData.setStringData(con, AppConstant.designation, sr.getUser().getDesignation());
                    //PersistData.setStringData(con, AppConstant.address, sr.getUser().getAddress());
                    // PersistData.setStringData(con, AppConstant.dob, sr.getUser().getDob());

                    PersistData.setStringData(con, AppConstant.API_TOKEN, sr.getToken());
                    PersistData.setStringData(con, AppConstant.password, password);
                    PersistData.setStringData(con, AppConstant.user_name, userName);
                    finish();

                    // get all user
                  //  AppConstant.chatAlllist.clear();
                   // requestChatListUrl(API_URL.CHAT_LIST);

                                 /*   if(PersistData.getStringData(con,AppConstant.notificationcClick).equalsIgnoreCase("PUSH")){
                                        startActivity(new Intent(con, ChatingActivity.class));
                                    }else{
                                        startActivity(new Intent(con, NavigationDrawerActivity.class));
                                    }
*/

                    startActivity(new Intent(con, Home.class));

                } else {
                    //Toast.makeText(con, sr.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<SigninResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("SigninActivity ", t.toString());
            }
        });
    }
}
