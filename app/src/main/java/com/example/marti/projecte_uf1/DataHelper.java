package com.example.marti.projecte_uf1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHelper extends SQLiteOpenHelper {

    public DataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(SQLiteManager.DATABASE_CREATE);
            db.execSQL(SQLiteManager.CREATE_ESDEVENIMENT);


        } catch (Exception ex) {
            Log.e("Error: ", ex.toString());
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i("database version: ", String.valueOf(oldVersion));

        db.execSQL("DROP TABLE IF EXISTS Persona");
       // db.execSQL("DROP TABLE IF EXISTS Esdeveniment");
        db.execSQL(SQLiteManager.DATABASE_CREATE);
       // db.execSQL(SQLiteManager.CREATE_ESDEVENIMENT);
        db.execSQL(SQLiteManager.UPDATE_ESDEVENIMENT);


    }
}


