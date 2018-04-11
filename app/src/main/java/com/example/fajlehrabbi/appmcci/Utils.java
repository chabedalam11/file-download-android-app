package com.example.fajlehrabbi.appmcci;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.fajlehrabbi.appmcci.Model.AllCatSubCatFiles;

/**
 * Created by medisys on 12-Jan-18.
 */

public class Utils {
    public static AllCatSubCatFiles mr;
    static ProgressDialog dialog;

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static void showLoader(Context context){
        dialog = ProgressDialog.show(context, "",
                "Loading. Please wait...", true);
    }

    public static void hideLoader(){
        dialog.dismiss();
    }

}
