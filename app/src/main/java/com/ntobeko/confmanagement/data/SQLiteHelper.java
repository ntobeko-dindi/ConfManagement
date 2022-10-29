package com.ntobeko.confmanagement.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ntobeko.confmanagement.models.Credential;

public class SQLiteHelper extends SQLiteOpenHelper{

    public SQLiteHelper(Context context) {
        super(context, "Credentials.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Credentials(siteName TEXT primary key, userName TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists Credentials");
    }

    public boolean deleteData(String key) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Credentials where siteName = ?", new String[]{key});
        if(cursor.getCount()>0) {
            long result = DB.delete("Credentials", "siteName=?", new String[]{key});

            return result != -1;
        }
        else { return false; }
    }

    public boolean writeData(Credential data) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("siteName", data.getSiteName());
        contentValues.put("userName", data.getUserName());
        contentValues.put("password", data.getPassword());
        long result = DB.insert("Credentials", null, contentValues);

        return result != -1;
    }

    public Cursor getData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("Select * from Credentials", null);
    }

    public Cursor getDataById(String id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("Select * from Credentials where siteName = ?", new String[]{id});
    }
}