package com.example.fajlehrabbi.appmcci.Retrofit;

import com.example.fajlehrabbi.appmcci.Utilities.API_URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import com.imaginebd.navigateconnect.Utilities.API_URL;


/**
 * Created by User on 6/6/2017.
 */

public class ApiClient {

    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }


    /*
    * for got json response use this client.
    * use Call<ResponseBody> converted response intead of model class.
    * for view response log use - response.body().string()
    * add dependency- compile 'com.squareup.retrofit2:converter-scalars:2.1.0'
    * */

  /*
    public static Retrofit getClientForSeeresponse() {
        if (retrofit==null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }*/

}