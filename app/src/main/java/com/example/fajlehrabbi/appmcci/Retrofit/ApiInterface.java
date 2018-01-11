package com.example.fajlehrabbi.appmcci.Retrofit;

import com.example.fajlehrabbi.appmcci.Model.AllCatSubCatFiles;
import com.example.fajlehrabbi.appmcci.Model.AllListResponse;
import com.example.fajlehrabbi.appmcci.Model.SigninResponse;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

//import com.imaginebd.navigateconnect.Model.CommonResponse;
//import com.imaginebd.navigateconnect.Model.MessageSendResponse;
//import com.imaginebd.navigateconnect.Model.SigninResponse;


/**
 * Created by User on 6/6/2017.
 */

public interface ApiInterface {

    // update profile
    @FormUrlEncoded
    @POST("mapps/api/login.php")
    Call<SigninResponse> signin(
            @Field("username") String username,
            @Field("password") String password
            //@Field("GCM") String gcm_id);


            // leave from group chat
            // @FormUrlEncoded
            //@POST("chat/leaveConversation")
            // Call<MessageSendResponse> groupChatLeave(@Field("my_email") String my_email,
    );

    // delete a group chat
    @FormUrlEncoded

    @POST("mapps/api/list_file.php")
    Call<AllCatSubCatFiles> showlist(
            @Field("username") String username,
            @Field("token") String api_token);

    // delete a group chat
    @FormUrlEncoded

    @POST("mapps/api/list_file.php")
    Call<AllListResponse> showlist1(
            @Field("username") String username,
            @Field("token") String api_token);



}

