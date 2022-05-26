package com.example.jobstar.SqlLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelperApplication extends SQLiteOpenHelper {

    public static final String DBNAME = "application.db";

    public DBHelperApplication(Context context) {
        super(context, "application.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("create Table application(ID INTEGER primary key AUTOINCREMENT, applicantName TEXT, jobTitle TEXT,company TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {
        myDB.execSQL("drop Table if exists application");
    }

    public Boolean checkResubmission(String applicantName,String jobTitle){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from application where applicantName = ? and jobTitle = ?",new String[] {applicantName,jobTitle});
        return cursor.getCount() > 0;
    }

    public Boolean addApplication(String applicantName,String jobTitle,String company){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("applicantName",applicantName);
        contentValues.put("jobTitle",jobTitle);
        contentValues.put("company",company);
        long result = DB.insert("application",null,contentValues);
        return result != -1;
    }

    public Cursor getAllApplicationByUser(String applicantName){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from application where applicantName = ?",new String[] {applicantName});
        return cursor;
    }

}
