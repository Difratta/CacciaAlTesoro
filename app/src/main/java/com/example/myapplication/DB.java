package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyDatabase.db";
    private static final int DATABASE_VERSION = 1;
    public DB(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        if (!tableExists(sqLiteDatabase)) {
            Log.i("DB", "Creazione tabella luoghi");
            String query = "CREATE TABLE luoghi (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome STRING, " +
                    "latitudine REAL," +
                    "longitudine REAL," +
                    "descrizione STRING," +
                    "suggerimento STRING," +
                    "trovato BOOLEAN DEFAULT 0)" ; //0 = false, 1 = true
            sqLiteDatabase.execSQL(query);
            insertInitialData(sqLiteDatabase);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    //aggiornamento del database se necessario
    }
    //funzione per popolare la lista di luoghi con i dati iniziali
    private void insertInitialData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        //primo luogo
        values.put("nome", "Luogo 1");
        values.put("latitudine", 44.41102381169466);
        values.put("longitudine", 8.969132339177387);
        values.put("descrizione", "Descrizione 1");
        values.put("suggerimento", "scritta bianco su sfondo arancione");
        db.insert("luoghi", null, values);
        //secondo luogo
        values.clear();
        values.put("nome", "Luogo 2");
        values.put("latitudine", 44.411109);
        values.put("longitudine", 8.969132339177387);
        values.put("descrizione", "Descrizione 2");
        values.put("suggerimento", "suggerimetno caso");
        db.insert("luoghi", null, values);
        //oggetto 1
        values.clear();
        values.put("nome", "Oggetto 1");
        values.put("latitudine", 44.412);
        values.put("longitudine", 8.950);
        values.put("descrizione", "\"Complimenti hai trovato l'oggetto n.1\"");
        values.put("suggerimento", "suggerimento 1");
        db.insert("luoghi", null, values);
        //oggetto 2
        values.clear();
        values.put("nome", "Oggetto 2");
        values.put("latitudine", 44.410);
        values.put("longitudine", 8.951);
        values.put("descrizione", "\"Complimenti hai trovato l'oggetto n.2\"");
        values.put("suggerimento", "suggerimento 2");
        db.insert("luoghi", null, values);
        //oggetto 3
        values.clear();
        values.put("nome", "Oggetto 3");
        values.put("latitudine", 44.408);
        values.put("longitudine", 8.948);
        values.put("descrizione", "\"Complimenti hai trovato l'oggetto n.3\"");
        values.put("suggerimento", "suggerimento 3");
        db.insert("luoghi", null, values);
        //oggetto 4
        values.clear();
        values.put("nome", "Oggetto 4");
        values.put("latitudine", 44.409);
        values.put("longitudine", 8.946);
        values.put("descrizione", "\"Complimenti hai trovato l'oggetto n.4\"");
        values.put("suggerimento", "suggerimento 4");
        db.insert("luoghi", null, values);
        //oggetto 5
        values.clear();
        values.put("nome", "Oggetto 5");
        values.put("latitudine", 44.406);
        values.put("longitudine", 8.947);
        values.put("descrizione", "\"Complimenti hai trovato l'oggetto n.5\"");
        values.put("suggerimento", "suggerimento 5");
        db.insert("luoghi", null, values);
        //oggetto 6
        values.clear();
        values.put("nome", "Oggetto 6");
        values.put("latitudine", 44.407);
        values.put("longitudine", 8.949);
        values.put("descrizione", "\"Complimenti hai trovato l'oggetto n.6\"");
        values.put("suggerimento", "suggerimento 6");
        db.insert("luoghi", null, values);
        //oggetto 7
        values.clear();
        values.put("nome", "Oggetto 7");
        values.put("latitudine", 44.404);
        values.put("longitudine", 8.950);
        values.put("descrizione", "\"Complimenti hai trovato l'oggetto n.7\"");
        values.put("suggerimento", "suggerimento 7");
        db.insert("luoghi", null, values);
        //oggetto 8
        values.clear();
        values.put("nome", "Oggetto 8");
        values.put("latitudine", 44.402);
        values.put("longitudine", 8.947);
        values.put("descrizione", "\"Complimenti hai trovato l'oggetto n.8\"");
        values.put("suggerimento", "suggerimento 8");
        db.insert("luoghi", null, values);
        //oggetto 9
        values.clear();
        values.put("nome", "Oggetto 9");
        values.put("latitudine", 44.403);
        values.put("longitudine", 8.946);
        values.put("descrizione", "\"Complimenti hai trovato l'oggetto n.9\"");
        values.put("suggerimento", "suggerimento 9");
        db.insert("luoghi", null, values);
        //oggetto 10
        values.clear();
        values.put("nome", "Oggetto 10");
        values.put("latitudine", 44.400);
        values.put("longitudine", 8.945);
        values.put("descrizione", "\"Complimenti hai trovato l'oggetto n.10\"");
        values.put("suggerimento", "suggerimento 10");
        db.insert("luoghi", null, values);


    }
    private boolean tableExists(SQLiteDatabase db) {
        Cursor cursor =db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", new String[]{"luoghi"});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
    public ArrayList<MarkerInfo> getAllLuoghi(){
        ArrayList<MarkerInfo> luoghi = new ArrayList<>();
        String selectQuery = "SELECT * FROM luoghi";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do{
                String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
                double latitudine = cursor.getDouble(cursor.getColumnIndexOrThrow("latitudine"));
                double longitudine = cursor.getDouble(cursor.getColumnIndexOrThrow("longitudine"));
                String descrizione = cursor.getString(cursor.getColumnIndexOrThrow("descrizione"));
                String suggerimento = cursor.getString(cursor.getColumnIndexOrThrow("suggerimento"));
                boolean trovato = (cursor.getInt(cursor.getColumnIndexOrThrow("trovato"))==1);
                MarkerInfo luogo = new MarkerInfo(nome, latitudine, longitudine, descrizione, suggerimento, trovato);
                luoghi.add(luogo);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return luoghi;
    }
    public void updateTrovato(String nome){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("trovato", 1);
        db.update("luoghi", values, "nome = ?", new String[]{nome});
        db.close();
    }
}
