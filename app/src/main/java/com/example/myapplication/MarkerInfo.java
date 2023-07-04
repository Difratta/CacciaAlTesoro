package com.example.myapplication;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MarkerInfo {
    private String nome;
    private double latitudine;
    private double longitudine;
    private String descrizione;
    private boolean trovato = false;
    private Marker marker;



    public MarkerInfo(String nome, double latitudine, double longitudine, String descrizione) {
        this.nome = nome;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.descrizione = descrizione;
    }

    public String getNome() {
        return nome;
    }
    public double getLatitudine() {
        return latitudine;
    }
    public double getLongitudine() {
        return longitudine;
    }
    public String getDescrizione() { return descrizione; }
    public boolean getTrovato() { return trovato; }

}