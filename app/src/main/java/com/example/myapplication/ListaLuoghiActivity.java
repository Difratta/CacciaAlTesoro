package com.example.myapplication;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListaLuoghiActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LuoghiAdapter luoghiAdapter;
    private ArrayList<MarkerInfo> luoghi;
    private DB db;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_luoghi_layout);

        // Recupero dei luoghi
        db = new DB(this);
        luoghi = db.getAllLuoghi();

        // Configurazione del RecyclerView
        recyclerView = findViewById(R.id.recyclerViewListaLuoghi);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Creazione e assegnazione dell'adapter
        luoghiAdapter = new LuoghiAdapter(luoghi);
        recyclerView.setAdapter(luoghiAdapter);

    }
}
