package com.example.marti.projecte_uf1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteManager {

    static final String DATABASE_NAME = "database.db";
    String ok = "OK";
    static final int DATABASE_VERSION = 12;
    public static String getPassword = "";

    public static final int NAME_COLUMN = 1;


    static final String DATABASE_CREATE = "create table Persona( id integer primary key autoincrement, email text,password text, nom text, edat integer, telefon text, descripcio text); ";
    static final String CREATE_ESDEVENIMENT = "create table Esdeveniment( id integer primary key autoincrement, titol text, tipus text, lloc text, data text, interessa integer, numAssistents  integer); ";
    static final String UPDATE_ESDEVENIMENT = "ALTER TABLE Esdeveniment ADD COLUMN pais tex";


    public static SQLiteDatabase db;

    private final Context context;

    private static DataHelper dbHelper;

    public SQLiteManager(Context _context) {
        context = _context;
        dbHelper = new DataHelper(_context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public SQLiteManager openWrite() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }



    public void close() {

        dbHelper.close();

    }


    public SQLiteDatabase getDatabaseInstance() {

        return db;

    }





    public int deletePersona(String UserName) {

        String selection = "email LIKE ?";
        String[] args = {UserName};
        return db.delete("Persona", selection, args);

    }


    public void updateEsdeveniment(String id, boolean interessa) {





        ContentValues values = new ContentValues();
        values.put("interessa", interessa);
        //values.put("PASSWORD", password);
        String selection = "id LIKE ?";
        String[] args = {id};
        db.update("Esdeveniment", values, selection, args);


    }


}
