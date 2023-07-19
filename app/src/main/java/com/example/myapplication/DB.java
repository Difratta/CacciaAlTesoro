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
        //oggetto 1 Opera Pia
        values.clear();
        values.put("nome", "Oggetto 1");
        values.put("latitudine", 44.40300312136162);
        values.put("longitudine", 8.95874172447634);
        values.put("descrizione", "\"Complimenti hai trovato Opera Pia\"");
        values.put("suggerimento", "suggerimento 1");
        db.insert("luoghi", null, values);
        //oggetto 2 De Ferrari
        values.clear();
        values.put("nome", "Oggetto 2");
        values.put("latitudine", 44.407166184906025);
        values.put("longitudine", 8.934047754599241);
        values.put("descrizione", "\"Complimenti hai trovato De Ferrari\"");
        values.put("suggerimento", "suggerimento 2");
        db.insert("luoghi", null, values);
        //oggetto 3 Brignole
        values.clear();
        values.put("nome", "Oggetto 3");
        values.put("latitudine", 44.406509517234674);
        values.put("longitudine", 8.94665942143637);
        values.put("descrizione", "\"Complimenti hai trovato Brignole\"");
        values.put("suggerimento", "suggerimento 3");
        db.insert("luoghi", null, values);
        //oggetto 4 Stadio
        values.clear();
        values.put("nome", "Oggetto 4");
        values.put("latitudine", 44.416445545940384);
        values.put("longitudine", 8.952582884030504);
        values.put("descrizione", "\"Complimenti hai trovato Stadio\"");
        values.put("suggerimento", "suggerimento 4");
        db.insert("luoghi", null, values);
        //oggetto 5 F Santa Tecla
        values.clear();
        values.put("nome", "Oggetto 5");
        values.put("latitudine", 44.412949524715536);
        values.put("longitudine", 8.977010117568442);
        values.put("descrizione", "\"Complimenti hai trovato F. Santa Tecla\"");
        values.put("suggerimento", "suggerimento 5");
        db.insert("luoghi", null, values);
        //oggetto 6 Acquario
        values.clear();
        values.put("nome", "Oggetto 6");
        values.put("latitudine", 44.41105494216552);
        values.put("longitudine", 8.926743177093758);
        values.put("descrizione", "\"Complimenti hai trovato Acquario\"");
        values.put("suggerimento", "suggerimento 6");
        db.insert("luoghi", null, values);
        //oggetto 7 Lanterna
        values.clear();
        values.put("nome", "Oggetto 7");
        values.put("latitudine", 44.40639497370395);
        values.put("longitudine", 8.90428824049446);
        values.put("descrizione", "\"Complimenti hai trovato Lanterna\"");
        values.put("suggerimento", "suggerimento 7");
        db.insert("luoghi", null, values);
        //oggetto 8 Fiumara
        values.clear();
        values.put("nome", "Oggetto 8");
        values.put("latitudine", 44.413743216845795);
        values.put("longitudine", 8.881833303985164);
        values.put("descrizione", "\"Complimenti hai trovato Fiumara\"");
        values.put("suggerimento", "suggerimento 8");
        db.insert("luoghi", null, values);
        //oggetto 9 Opera Pia 2
        values.clear();
        values.put("nome", "Oggetto 9");
        values.put("latitudine", 44.40314593337858);
        values.put("longitudine", 8.959742173363937);
        values.put("descrizione", "\"Complimenti hai trovato Opera Pia 2\"");
        values.put("suggerimento", "suggerimento 9");
        db.insert("luoghi", null, values);
        //oggetto 10 Villa Imperiale
        values.clear();
        values.put("nome", "Oggetto 10");
        values.put("latitudine", 44.40850595451148);
        values.put("longitudine", 8.961645014141085);
        values.put("descrizione", "\"Complimenti hai trovato Villa Imperiale\"");
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
