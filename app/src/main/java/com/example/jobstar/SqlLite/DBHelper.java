    package com.example.jobstar.SqlLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";

    public DBHelper(Context context) {
        super(context,"Login.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {

        myDB.execSQL("create Table users(userName TEXT primary key, password TEXT,email TEXT,phone TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {
        myDB.execSQL("drop Table if exists users");
    }

    public Boolean insertDate(String userName, String password,String email,String phone){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userName", userName);
        contentValues.put("password",password);
        contentValues.put("email",email);
        contentValues.put("phone",phone);
        long result = MyDB.insert("users",null,contentValues);
        return result != -1;
    }

    public Boolean checkUserName(String userName){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where userName = ?",new String[]{userName});
        return cursor.getCount() > 0;
    }

    public Boolean checkCredentials(String userName,String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where userName = ? and password = ?",new String[] {userName,password});
        return cursor.getCount() > 0;
    }

    public Cursor getUserProfile(String name){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where userName = ?",new String[]{name});
        return cursor;
    }


}
