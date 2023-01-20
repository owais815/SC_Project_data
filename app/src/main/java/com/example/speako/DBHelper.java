package com.example.speako;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

public static final String dbName="Login.db";
    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
db.execSQL("create Table users(mail TEXT primary key,username TEXT,password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String mail,String username,String password){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("mail",mail);
        contentValues.put("username",username);
        contentValues.put("password",password);
        long result=db.insert("users",null,contentValues);
        if (result==-1){
            return false;
        }
        else{
            return true;}
    }

    public Boolean checkmail(String mail){

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from users where mail = ?",new String[]{mail});
    if(cursor.getCount()>0){
        return true;
    } else{
        return false;
    }
    }

    public Boolean checkLogin(String mail,String password){

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from users where mail= ? and password= ?",new String[]{mail,password});
        if(cursor.getCount()>0){
            return true;
        } else{
            return false;
        }
    }

}
