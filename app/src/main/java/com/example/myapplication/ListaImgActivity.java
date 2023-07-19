package com.example.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListaImgActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImgAdapter imgAdapter;
    private ArrayList<MarkerInfo> luoghi;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_imm_layout);

        // Recupero dei luoghi
        db = new DB(this);
        luoghi = db.getAllLuoghi();

        // Configurazione del RecyclerView
        recyclerView = findViewById(R.id.recyclerViewListaImmagini);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Creazione e assegnazione dell'adapter
        imgAdapter = new ImgAdapter(luoghi);
        recyclerView.setAdapter(imgAdapter);

    }

}
