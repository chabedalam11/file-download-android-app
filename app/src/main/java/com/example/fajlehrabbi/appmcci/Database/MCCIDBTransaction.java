package com.example.fajlehrabbi.appmcci.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.fajlehrabbi.appmcci.Model.ComLists;
import com.example.fajlehrabbi.appmcci.Model.FileLists;
import com.example.fajlehrabbi.appmcci.Model.SigninResponse;
import com.example.fajlehrabbi.appmcci.Model.SubComLists;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by medisys on 5/2/2017.
 */

public class MCCIDBTransaction extends MCCIDBSchema {
    final String TAG = "MCCIDBTransaction";

    public MCCIDBTransaction(Context context) {
        super(context);
    }

    //========================== LOGIN table ======================================

    //LOGIN Table transaction
    public  boolean insertLOGIN (SigninResponse signinResponse) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(LOGIN_COLUMN_STATUS, signinResponse.getStatus());
        contentValues.put(LOGIN_COLUMN_STATUS_CODE, signinResponse.getStatus_code());
        contentValues.put(LOGIN_COLUMN_TOKEN, signinResponse.getToken());
        contentValues.put(LOGIN_COLUMN_USERID, signinResponse.getData().getUserid());
        contentValues.put(LOGIN_COLUMN_USERNAME, signinResponse.getData().getUsername());
        contentValues.put(LOGIN_COLUMN_USTATUS, signinResponse.getData().getUstatus());
        contentValues.put(LOGIN_COLUMN_NAME, signinResponse.getData().getName());
        contentValues.put(LOGIN_COLUMN_CLIENT_ID, signinResponse.getData().getClient_id());

        db.insert(TABLE_LOGIN, null, contentValues);

        Log.d(TAG,"User ( "+signinResponse.getData().getUserid()+" "+signinResponse.getData().getUsername()+" ) insert successfully");
        return true;
    }


    public boolean isUserExist(SigninResponse signinResponse) {
        //Log.d(TAG,"checkMedNameExisting parameter "+PatNo+" and "+appNo);
        ArrayList<SigninResponse> list = new ArrayList<SigninResponse>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery ="select * from "+ TABLE_LOGIN
                +"  where "+LOGIN_COLUMN_USERID+" = "+signinResponse.getData().getUserid()+" AND "
                +LOGIN_COLUMN_USERNAME + " = " + "'"+signinResponse.getData().getUsername()+"'";
        Log.d(TAG,"sql > "+sqlQuery);
        Cursor res =  db.rawQuery( sqlQuery, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            SigninResponse model = new SigninResponse();
            model.setToken(res.getString(res.getColumnIndex(LOGIN_COLUMN_TOKEN)));
            list.add(model);
            res.moveToNext();
        }
        if (list.size()>0){
            return true;
        }else {
            return false;
        }
    }

//========================== COMMITTEE_LIST table ======================================

    //COMMITTEE_LIST Table transaction
    public  boolean insertCOMMITTEE_LIST (ComLists comLists) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COMMITTEE_LIST_COLUMN_ID, comLists.getId());
        contentValues.put(COMMITTEE_LIST_COLUMN_CAT_NAME, comLists.getCat_name());
        contentValues.put(COMMITTEE_LIST_COLUMN_STATUS, comLists.getStatus());
        contentValues.put(COMMITTEE_LIST_COLUMN_POSITION, comLists.getPosition());
        contentValues.put(COMMITTEE_LIST_COLUMN_TYPE, comLists.getType());
        contentValues.put(COMMITTEE_LIST_COLUMN_SHORT_CODE, comLists.getShort_code());

        db.insert(TABLE_COMMITTEE_LIST, null, contentValues);

        Log.d(TAG,"COMMITTEE ( "+comLists.getId()+" "+comLists.getCat_name()+" ) insert successfully");
        return true;
    }


    public boolean deleteCOMMITTEE_LISTInfo() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete  from " + TABLE_COMMITTEE_LIST);
        return true;
    }

    public ArrayList<ComLists> getCommList() {
        ArrayList<ComLists> list = new ArrayList<ComLists>();
        ComLists model =null;
        String sqlQuery ="select * from "+ TABLE_COMMITTEE_LIST;
        Log.d(TAG,"sql > "+sqlQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( sqlQuery, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            model = new ComLists();

            model.setId(res.getString(res.getColumnIndex(COMMITTEE_LIST_COLUMN_ID)));
            model.setCat_name(res.getString(res.getColumnIndex(COMMITTEE_LIST_COLUMN_CAT_NAME)));
            model.setStatus(res.getString(res.getColumnIndex(COMMITTEE_LIST_COLUMN_STATUS)));
            model.setPosition(res.getString(res.getColumnIndex(COMMITTEE_LIST_COLUMN_POSITION)));
            model.setType(res.getString(res.getColumnIndex(COMMITTEE_LIST_COLUMN_TYPE)));
            model.setShort_code(res.getString(res.getColumnIndex(COMMITTEE_LIST_COLUMN_SHORT_CODE)));

            list.add(model);
            res.moveToNext();
        }
        return list;
    }

//========================== SUB_SUB_COMMITTEE_LIST table ======================================

    //SUB_COMMITTEE_LIST Table transaction
    public  boolean insertSUB_COMMITTEE (SubComLists comLists) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(SUB_COMMITTEE_LIST_COLUMN_ID, comLists.getId());
        contentValues.put(SUB_COMMITTEE_LIST_COLUMN_SUBCAT_NAME, comLists.getSubcat_name());
        contentValues.put(SUB_COMMITTEE_LIST_COLUMN_CAT_ID, comLists.getCat_id());
        contentValues.put(SUB_COMMITTEE_LIST_COLUMN_STATUS, comLists.getStatus());
        contentValues.put(SUB_COMMITTEE_LIST_COLUMN_POSITION, comLists.getPosition());
        contentValues.put(SUB_COMMITTEE_LIST_COLUMN_TYPE, comLists.getType());
        contentValues.put(SUB_COMMITTEE_LIST_COLUMN_SHORT_CODE, comLists.getShort_code());

        db.insert(TABLE_SUB_COMMITTEE_LIST, null, contentValues);

        Log.d(TAG,"SUB_COMMITTEE ( "+comLists.getId()+" "+comLists.getSubcat_name()+" ) insert successfully");
        return true;
    }


    public boolean deleteSUB_COMMITTEE_LISTInfo() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete  from " + TABLE_SUB_COMMITTEE_LIST);
        return true;
    }

    public ArrayList<SubComLists> getSubCommList() {
        ArrayList<SubComLists> list = new ArrayList<SubComLists>();
        SubComLists model =null;
        String sqlQuery ="select * from "+ TABLE_SUB_COMMITTEE_LIST;
        Log.d(TAG,"sql > "+sqlQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( sqlQuery, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            model = new SubComLists();

            model.setId(res.getString(res.getColumnIndex(SUB_COMMITTEE_LIST_COLUMN_ID)));
            model.setSubcat_name(res.getString(res.getColumnIndex(SUB_COMMITTEE_LIST_COLUMN_SUBCAT_NAME)));
            model.setCat_id(res.getString(res.getColumnIndex(SUB_COMMITTEE_LIST_COLUMN_CAT_ID)));
            model.setStatus(res.getString(res.getColumnIndex(SUB_COMMITTEE_LIST_COLUMN_STATUS)));
            model.setPosition(res.getString(res.getColumnIndex(SUB_COMMITTEE_LIST_COLUMN_POSITION)));
            model.setType(res.getString(res.getColumnIndex(SUB_COMMITTEE_LIST_COLUMN_TYPE)));
            model.setShort_code(res.getString(res.getColumnIndex(SUB_COMMITTEE_LIST_COLUMN_SHORT_CODE)));

            list.add(model);
            res.moveToNext();
        }
        return list;
    }


   //========================== FILE_LIST table ======================================

    //FILE_LIST Table transaction
    public  boolean insertFILE (FileLists fileList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(FILE_LIST_COLUMN_ID, fileList.getId());
        contentValues.put(FILE_LIST_COLUMN_NAME, fileList.getName());
        contentValues.put(FILE_LIST_COLUMN_CAT_ID, fileList.getCat_id());
        contentValues.put(FILE_LIST_COLUMN_SUBCAT_ID, fileList.getSubcat_id());
        contentValues.put(FILE_LIST_COLUMN_FILE_UPLOAD, fileList.getFile_upload());
        contentValues.put(FILE_LIST_COLUMN_FILE_EXTENSION, fileList.getFile_extension());
        contentValues.put(FILE_LIST_COLUMN_SATATUS, fileList.getSatatus());
        contentValues.put(FILE_LIST_COLUMN_DATE, fileList.getDate());
        contentValues.put(FILE_LIST_COLUMN_COMMITTEE, fileList.getCommittee());
        contentValues.put(FILE_LIST_COLUMN_SUB_COMMITTEE, fileList.getSub_committee());

        db.insert(TABLE_FILE_LIST, null, contentValues);

        Log.d(TAG,"FILE ( "+fileList.getId()+" "+fileList.getName()+" ) insert successfully");
        return true;
    }


    public boolean deleteFILE_LISTInfo() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete  from " + TABLE_FILE_LIST);
        return true;
    }

    public ArrayList<FileLists> getFileList() {
        ArrayList<FileLists> list = new ArrayList<FileLists>();
        FileLists model =null;
        String sqlQuery ="select * from "+ TABLE_FILE_LIST;
        Log.d(TAG,"sql > "+sqlQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( sqlQuery, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            model = new FileLists();

            model.setId(res.getString(res.getColumnIndex(FILE_LIST_COLUMN_ID)));
            model.setName(res.getString(res.getColumnIndex(FILE_LIST_COLUMN_NAME)));
            model.setCat_id(res.getString(res.getColumnIndex(FILE_LIST_COLUMN_CAT_ID)));
            model.setSubcat_id(res.getString(res.getColumnIndex(FILE_LIST_COLUMN_SUBCAT_ID)));
            model.setFile_upload(res.getString(res.getColumnIndex(FILE_LIST_COLUMN_FILE_UPLOAD)));
            model.setFile_extension(res.getString(res.getColumnIndex(FILE_LIST_COLUMN_FILE_EXTENSION)));
            model.setSatatus(res.getString(res.getColumnIndex(FILE_LIST_COLUMN_SATATUS)));
            model.setDate(res.getString(res.getColumnIndex(FILE_LIST_COLUMN_DATE)));
            model.setCommittee(res.getString(res.getColumnIndex(FILE_LIST_COLUMN_COMMITTEE)));
            model.setSub_committee(res.getString(res.getColumnIndex(FILE_LIST_COLUMN_SUB_COMMITTEE)));

            list.add(model);
            res.moveToNext();
        }
        return list;
    }


   


}
