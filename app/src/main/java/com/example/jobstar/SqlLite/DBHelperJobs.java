package com.example.jobstar.SqlLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelperJobs extends SQLiteOpenHelper {

    public static final String DBNAME = "jobs.db";

    public DBHelperJobs(Context context) {
        super(context, "jobs.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("create Table jobs(title TEXT primary key, company TEXT, location TEXT, salary TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {
        myDB.execSQL("drop Table if exists jobs");
    }

    public Boolean insertJobData(String title,String company,String location,String salary){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",title);
        contentValues.put("company",company);
        contentValues.put("location",location);
        contentValues.put("salary",salary);
        long result = DB.insert("jobs",null,contentValues);
        return result != -1;
    }

    public Boolean updateJobData(String title,String company,String location,String salary){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("company",company);
        contentValues.put("location",location);
        contentValues.put("salary",salary);
        Cursor cursor = DB.rawQuery("Select * from jobs where title = ?",new String[]{title});
        if (cursor.getCount()>0){
            long result = DB.update("jobs",contentValues,"title=?", new String[]{title});
            return result != -1;
        }else {
            return false;
        }
    }

    public Boolean deleteJobData(String title){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from jobs where title = ?",new String[]{title});
        if (cursor.getCount()>0){
            long result = DB.delete("jobs","title=?", new String[]{title});
            return result != -1;
        }else {
            return false;
        }
    }

    public Cursor getJobData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from jobs",null);
        return cursor;
    }


}
