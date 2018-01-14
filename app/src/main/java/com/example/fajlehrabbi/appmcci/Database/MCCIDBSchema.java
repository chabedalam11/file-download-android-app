package com.example.fajlehrabbi.appmcci.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MCCIDBSchema extends SQLiteOpenHelper {

    //database name
    public static final String DATABASE_NAME = "mcci.db";

    //========================== LOGIN table ======================================

    //LOGIN table
    public static final String TABLE_LOGIN = "LOGIN";

    //LOGIN TABLE COLUMN NAME
    public static final String LOGIN_COLUMN_STATUS = "status";
    public static final String LOGIN_COLUMN_STATUS_CODE = "status_code";
    public static final String LOGIN_COLUMN_TOKEN = "token";
    public static final String LOGIN_COLUMN_USERID = "userid";
    public static final String LOGIN_COLUMN_USERNAME = "username";
    public static final String LOGIN_COLUMN_USTATUS = "ustatus";
    public static final String LOGIN_COLUMN_NAME = "name";
    public static final String LOGIN_COLUMN_CLIENT_ID = "client_id";

    // LOGIN  table create statement
    private static final String CREATE_TABLE_LOGIN = "CREATE TABLE "
            + TABLE_LOGIN + "("
            + LOGIN_COLUMN_STATUS + " TEXT ,"
            + LOGIN_COLUMN_STATUS_CODE + " TEXT,"
            + LOGIN_COLUMN_TOKEN + " TEXT,"
            + LOGIN_COLUMN_USERID + " TEXT,"
            + LOGIN_COLUMN_USERNAME + " TEXT,"
            + LOGIN_COLUMN_USTATUS + " TEXT,"
            + LOGIN_COLUMN_NAME + " TEXT,"
            + LOGIN_COLUMN_CLIENT_ID + " TEXT"
            + ")";

//========================== COMMITTEE_LIST table ======================================

    //COMMITTEE_LIST table
    public static final String TABLE_COMMITTEE_LIST = "COMMITTEE_LIST";

    //COMMITTEE_LIST TABLE COLUMN NAME
    public static final String COMMITTEE_LIST_COLUMN_ID = "id";
    public static final String COMMITTEE_LIST_COLUMN_CAT_NAME = "cat_name";
    public static final String COMMITTEE_LIST_COLUMN_STATUS = "status";
    public static final String COMMITTEE_LIST_COLUMN_POSITION = "position";
    public static final String COMMITTEE_LIST_COLUMN_TYPE = "type";
    public static final String COMMITTEE_LIST_COLUMN_SHORT_CODE = "short_code";


    // COMMITTEE_LIST  table create statement
    private static final String CREATE_TABLE_COMMITTEE_LIST = "CREATE TABLE "
            + TABLE_COMMITTEE_LIST + "("
            + COMMITTEE_LIST_COLUMN_ID + " TEXT ,"
            + COMMITTEE_LIST_COLUMN_CAT_NAME + " TEXT,"
            + COMMITTEE_LIST_COLUMN_STATUS + " TEXT,"
            + COMMITTEE_LIST_COLUMN_POSITION + " TEXT,"
            + COMMITTEE_LIST_COLUMN_TYPE + " TEXT,"
            + COMMITTEE_LIST_COLUMN_SHORT_CODE + " TEXT"
            + ")";

//========================== SUB_COMMITTEE_LIST table ======================================

    //SUB_COMMITTEE_LIST table
    public static final String TABLE_SUB_COMMITTEE_LIST = "SUB_COMMITTEE_LIST";

    //SUB_COMMITTEE_LIST TABLE COLUMN NAME
    public static final String SUB_COMMITTEE_LIST_COLUMN_ID = "id";
    public static final String SUB_COMMITTEE_LIST_COLUMN_SUBCAT_NAME = "subcat_name";
    public static final String SUB_COMMITTEE_LIST_COLUMN_CAT_ID = "cat_id";
    public static final String SUB_COMMITTEE_LIST_COLUMN_STATUS = "status";
    public static final String SUB_COMMITTEE_LIST_COLUMN_POSITION = "position";
    public static final String SUB_COMMITTEE_LIST_COLUMN_TYPE = "type";
    public static final String SUB_COMMITTEE_LIST_COLUMN_SHORT_CODE = "short_code";


    // SUB_COMMITTEE_LIST  table create statement
    private static final String CREATE_TABLE_SUB_COMMITTEE_LIST = "CREATE TABLE "
            + TABLE_SUB_COMMITTEE_LIST + "("
            + SUB_COMMITTEE_LIST_COLUMN_ID + " TEXT ,"
            + SUB_COMMITTEE_LIST_COLUMN_SUBCAT_NAME + " TEXT,"
            + SUB_COMMITTEE_LIST_COLUMN_CAT_ID + " TEXT,"
            + SUB_COMMITTEE_LIST_COLUMN_STATUS + " TEXT,"
            + SUB_COMMITTEE_LIST_COLUMN_POSITION + " TEXT,"
            + SUB_COMMITTEE_LIST_COLUMN_TYPE + " TEXT,"
            + SUB_COMMITTEE_LIST_COLUMN_SHORT_CODE + " TEXT"
            + ")";

//========================== FILE_LIST table ======================================

    //FILE_LIST table
    public static final String TABLE_FILE_LIST = "FILE_LIST";

    //FILE_LIST TABLE COLUMN NAME
    public static final String FILE_LIST_COLUMN_ID = "id";
    public static final String FILE_LIST_COLUMN_NAME = "name";
    public static final String FILE_LIST_COLUMN_CAT_ID = "cat_id";
    public static final String FILE_LIST_COLUMN_SUBCAT_ID = "subcat_id";
    public static final String FILE_LIST_COLUMN_FILE_UPLOAD = "file_upload";
    public static final String FILE_LIST_COLUMN_FILE_EXTENSION = "file_extension";
    public static final String FILE_LIST_COLUMN_SATATUS = "satatus";
    public static final String FILE_LIST_COLUMN_DATE = "date";
    public static final String FILE_LIST_COLUMN_COMMITTEE = "committee";
    public static final String FILE_LIST_COLUMN_SUB_COMMITTEE = "sub_committee";


    // FILE_LIST  table create statement
    private static final String CREATE_TABLE_FILE_LIST = "CREATE TABLE "
            + TABLE_FILE_LIST + "("
            + FILE_LIST_COLUMN_ID + " TEXT ,"
            + FILE_LIST_COLUMN_NAME + " TEXT,"
            + FILE_LIST_COLUMN_CAT_ID + " TEXT,"
            + FILE_LIST_COLUMN_SUBCAT_ID + " TEXT,"
            + FILE_LIST_COLUMN_FILE_UPLOAD + " TEXT,"
            + FILE_LIST_COLUMN_FILE_EXTENSION + " TEXT,"
            + FILE_LIST_COLUMN_SATATUS + " TEXT,"
            + FILE_LIST_COLUMN_DATE + " TEXT,"
            + FILE_LIST_COLUMN_COMMITTEE + " TEXT,"
            + FILE_LIST_COLUMN_SUB_COMMITTEE + " TEXT"
            + ")";



    public MCCIDBSchema(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREATE_TABLE_LOGIN);
        db.execSQL(CREATE_TABLE_COMMITTEE_LIST);
        db.execSQL(CREATE_TABLE_SUB_COMMITTEE_LIST);
        db.execSQL(CREATE_TABLE_FILE_LIST);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_COMMITTEE_LIST);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_SUB_COMMITTEE_LIST);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_FILE_LIST);
        onCreate(db);
    }

}