package com.example.myapplication;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListaLuoghiActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LuoghiAdapter luoghiAdapter;
    private List<MarkerInfo> luoghi = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_luoghi_layout);

        // Recupero dei dati dei luoghi (esempio)
        luoghi = getLuoghi();

        // Configurazione del RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Creazione e assegnazione dell'adapter
        luoghiAdapter = new LuoghiAdapter(luoghi);
        recyclerView.setAdapter(luoghiAdapter);
    }

    // Metodo di esempio per ottenere la lista dei luoghi
    public List<MarkerInfo> getLuoghi() {
        List<MarkerInfo> luogo = new ArrayList<>();
        // Aggiungi i tuoi luoghi alla lista
        luogo.add(new MarkerInfo("Luogo 1", 44.41102381169466, 8.969132339177387, "Descrizione 1"));
        luogo.add(new MarkerInfo("Luogo 2", 44.411109, 8.969132339177387, "Descrizione 2"));
        luogo.add(new MarkerInfo("Oggetto 1", 44.412, 8.950, "\"Complimenti hai trovato l'oggetto n.1\""));
        luogo.add(new MarkerInfo("Oggetto 2", 44.410, 8.951, "\"Complimenti hai trovato l'oggetto n.2\""));
        luogo.add(new MarkerInfo("Oggetto 3", 44.408, 8.948, "\"Complimenti hai trovato l'oggetto n.3\""));
        luogo.add(new MarkerInfo("Oggetto 4", 44.409, 8.946, "\"Complimenti hai trovato l'oggetto n.4\""));
        luogo.add(new MarkerInfo("Oggetto 5", 44.406, 8.947, "\"Complimenti hai trovato l'oggetto n.5\""));
        luogo.add(new MarkerInfo("Oggetto 6", 44.407, 8.949, "\"Complimenti hai trovato l'oggetto n.6\""));
        luogo.add(new MarkerInfo("Oggetto 7", 44.404, 8.950, "\"Complimenti hai trovato l'oggetto n.7\""));
        luogo.add(new MarkerInfo("Oggetto 8", 44.402, 8.947, "\"Complimenti hai trovato l'oggetto n.8\""));
        luogo.add(new MarkerInfo("Oggetto 9", 44.403, 8.946, "\"Complimenti hai trovato l'oggetto n.9\""));
        luogo.add(new MarkerInfo("Oggetto 10", 44.400, 8.945, "\"Complimenti hai trovato l'oggetto n.10\""));

        return luogo;
    }
}
