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


    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table Persona( id integer primary key autoincrement, email text,password text, nom text, edat integer, telefon text, descripcio text); ";
    static final String CREATE_ESDEVENIMENT = "create table Esdeveniment( id integer primary key autoincrement, titol text, tipus text, lloc text, data text, interessa integer, numAssistents  integer); ";
    static final String UPDATE_ESDEVENIMENT = "ALTER TABLE Esdeveniment ADD COLUMN pais tex";
//titol, tipus, lloc, data, interessa, numAssistents


    public static SQLiteDatabase db;

    private final Context context;

    private static DataHelper dbHelper;

    public SQLiteManager(Context _context) {
        context = _context;
        dbHelper = new DataHelper(_context, DATABASE_NAME, null, DATABASE_VERSION);
        //esdevenimentsEntries();
    }

    // Method to open the Database writable
    public SQLiteManager openWrite() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }


    public SQLiteManager openRead() throws SQLException {
        //
        db = dbHelper.getReadableDatabase();
        return this;

    }


    public void close() {

        dbHelper.close();

    }


    public SQLiteDatabase getDatabaseInstance() {

        return db;

    }

    // method to get the password  of userName
    public String getPassword(String userName) {
      //  try {
      //      db = dbHelper.getReadableDatabase();
      //  } catch (Exception ex) {

        //}


        Cursor cursor = db.query("Persona", null, Persona.EMAIL + "=?", new String[]{userName}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
            return "NOT EXIST";
        cursor.moveToFirst();
        getPassword = cursor.getString(cursor.getColumnIndex(Persona.PASSWORD));
        return getPassword;


    }


    public Persona getPersona(String userName) {
        Persona per = new Persona();
        // db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Persona", null, Persona.EMAIL + "=?", new String[]{userName}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
            return per;
        cursor.moveToFirst();
        per.setNom(cursor.getString(cursor.getColumnIndex(Persona.NAME)));
        per.setDescripcio(cursor.getString(cursor.getColumnIndex(Persona.DESCRIPTION)));
        per.setTelefon(cursor.getString(cursor.getColumnIndex(Persona.PHONE)));
        per.setPassword(cursor.getString(cursor.getColumnIndex(Persona.PASSWORD)));
        per.setEmail(cursor.getString(cursor.getColumnIndex(Persona.EMAIL)));
        per.setId(cursor.getInt(cursor.getColumnIndex(Persona.ID)));
        per.setEdat(cursor.getInt(cursor.getColumnIndex(Persona.AGE)));
        return per;
    }


    public ArrayList<Esdeveniment> getListEsdeveniments() {
//        db = dbHelper.getReadableDatabase();
        ArrayList<Esdeveniment> list = new ArrayList<Esdeveniment>();

        Cursor c = db.rawQuery("Select * from Esdeveniment", null);

        if (c.moveToFirst()) {
            do {

                Esdeveniment esd = new Esdeveniment();
                esd.setId(c.getInt(c.getColumnIndex(Esdeveniment.ID)));
                esd.setTitol(c.getString(c.getColumnIndex(Esdeveniment.TITOL)));
                esd.setTipus(c.getString(c.getColumnIndex(Esdeveniment.TIPUS)));
                esd.setLloc(c.getString(c.getColumnIndex(Esdeveniment.LLOC)));
                esd.setData(c.getString(c.getColumnIndex(Esdeveniment.DATA)));
                if (c.getInt(c.getColumnIndex(Esdeveniment.INTERESSA)) == 0) {
                    esd.setInteressa(false);
                } else {
                    esd.setInteressa(true);
                }
                esd.setNumAssistens(c.getInt(c.getColumnIndex(Esdeveniment.NUMASSISTENTS)));
                list.add(esd);

            } while (c.moveToNext());
        }

        c.close();
        return list;
    }
    public ArrayList<Persona> getListContactes(String currentUser){
        ArrayList<Persona> list = new ArrayList<Persona>();
        Cursor cursor = db.rawQuery("Select * from Persona where email != ? order by nom", new String[] {currentUser});


        if (cursor.moveToFirst()) {
            do {

                Persona per = new Persona();
                per.setNom(cursor.getString(cursor.getColumnIndex(Persona.NAME)));
                per.setDescripcio(cursor.getString(cursor.getColumnIndex(Persona.DESCRIPTION)));
                per.setTelefon(cursor.getString(cursor.getColumnIndex(Persona.PHONE)));
                per.setPassword(cursor.getString(cursor.getColumnIndex(Persona.PASSWORD)));
                per.setEmail(cursor.getString(cursor.getColumnIndex(Persona.EMAIL)));
                per.setId(cursor.getInt(cursor.getColumnIndex(Persona.ID)));
                per.setEdat(cursor.getInt(cursor.getColumnIndex(Persona.AGE)));

                list.add(per);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return list;
    }

    public ArrayList<Persona> getListPersones() {

        //db = dbHelper.getReadableDatabase();
        ArrayList<Persona> list = new ArrayList<Persona>();
        Cursor cursor = db.rawQuery("Select * from Persona", null);


        if (cursor.moveToFirst()) {
            do {

                Persona per = new Persona();
                per.setNom(cursor.getString(cursor.getColumnIndex(Persona.NAME)));
                per.setDescripcio(cursor.getString(cursor.getColumnIndex(Persona.DESCRIPTION)));
                per.setTelefon(cursor.getString(cursor.getColumnIndex(Persona.PHONE)));
                per.setPassword(cursor.getString(cursor.getColumnIndex(Persona.PASSWORD)));
                per.setEmail(cursor.getString(cursor.getColumnIndex(Persona.EMAIL)));
                per.setId(cursor.getInt(cursor.getColumnIndex(Persona.ID)));
                per.setEdat(cursor.getInt(cursor.getColumnIndex(Persona.AGE)));

                list.add(per);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return list;
    }

    public String insertEsdeveniment(Esdeveniment esd) {

        ContentValues values = new ContentValues();
        values.put(Esdeveniment.ID, esd.getId());
        values.put(Esdeveniment.TITOL, esd.getTitol());
        values.put(Esdeveniment.TIPUS, esd.getTipus());
        values.put(Esdeveniment.LLOC, esd.getLloc());
        values.put(Esdeveniment.DATA, esd.getData());
        if (esd.isInteressa()) {
            values.put(Esdeveniment.INTERESSA, 1);
        } else {
            values.put(Esdeveniment.INTERESSA, 0);
        }
        values.put(Esdeveniment.NUMASSISTENTS, esd.getNumAssistens());

        long a = db.insert("Esdeveniment", null, values);
        return String.valueOf(a);

    }

    public void esdevenimentsEntries() {

        //openWrite();



        Esdeveniment esd = new Esdeveniment();
        esd.setId(0);
        esd.setTitol("Feminism History");
        esd.setTipus("Speech");
        esd.setLloc("Amsterdam");
        esd.setData("25/12/2018");
        esd.setInteressa(false);
        esd.setNumAssistens(150);
        try {
            insertEsdeveniment(esd)  ;
        } catch (Exception ex) {
            Log.e("PDFLoader", "esdeveniment can't be added to db");
        }


        Esdeveniment esd2 = new Esdeveniment();
        esd2.setId(1);
        esd2.setTitol("How to gain self-confidence");
        esd2.setTipus("Speech");
        esd2.setLloc("Tokyo");
        esd2.setData("30/12/2018");
        esd2.setInteressa(false);
        esd2.setNumAssistens(400);

        try {
            insertEsdeveniment(esd2);
        } catch (Exception ex) {
            Log.e("PDFLoader", "esdeveniment can't be added to db");
        }

        Esdeveniment esd3 = new Esdeveniment();
        esd3.setTitol("self-defense");
        esd3.setTipus("Lesson");
        esd3.setLloc("Barcelona");
        esd3.setId(2);
        esd3.setData("29/12/2018");
        esd.setInteressa(false);
        esd3.setNumAssistens(350);

        try {
            insertEsdeveniment(esd3);
        } catch (Exception ex) {
            Log.e("PDFLoader", "esdeveniment can't be added to db");
        }

        //close();


    }

    public void personaEntries() {
        Persona p = new Persona();
        p.setId(100);
        p.setEdat(34);
        p.setEmail("carles@gmail.com");
        p.setPassword("carles");
        p.setTelefon("64815483");
        p.setDescripcio("Bye bye");
        p.setNom("Carles");

        Persona p2 = new Persona();
        p.setId(101);
        p.setEdat(19);
        p.setEmail("marti@gmail.com");
        p.setPassword("marti");
        p.setTelefon("3181531");
        p.setDescripcio("Hi there~");
        p.setNom("Martí");

        Persona p3 = new Persona();
        p.setId(102);
        p.setEdat(56);
        p.setEmail("mario@gmail.com");
        p.setPassword("mario");
        p.setTelefon("65789456");
        p.setDescripcio("Description for mario");
        p.setNom("Mario");

        try {
            insertPersona(p);
            insertPersona(p2);
            insertPersona(p3);
        } catch (Exception ex){
            Log.e("PDFLoader", "Persones can't be added to db");
        }
    }

    public String insertPersona(Persona per) {





        ContentValues values = new ContentValues();
        values.put(Persona.ID, per.getId());
        values.put(Persona.EMAIL, per.getEmail());
        values.put(Persona.PASSWORD, per.getPassword());
        values.put(Persona.NAME, per.getNom());
        values.put(Persona.AGE, per.getEdat());
        values.put(Persona.PHONE, per.getTelefon());
        values.put(Persona.DESCRIPTION, per.getDescripcio());


        long a = db.insert("Persona", null, values);
        return String.valueOf(a);


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
