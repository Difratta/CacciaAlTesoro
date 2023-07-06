package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final double GENOA_LATITUDE = 44.4056;
    private static final double GENOA_LONGITUDE = 8.9463;
    private static final int RADIUS = 5000;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private List<Marker> visibleMarkers;
    private Circle searchCircle;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private final ListaLuoghiActivity listaLuoghiActivity = new ListaLuoghiActivity();
    private List<MarkerInfo> markerInfoList = new ArrayList<>();
    private LuoghiAdapter luoghiAdapter;
    private RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        visibleMarkers = new ArrayList<>();

        drawerLayout = findViewById(R.id.drawer_layout);
        findViewById(R.id.ic_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        LatLng genoa = new LatLng(GENOA_LATITUDE, GENOA_LONGITUDE);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(genoa, 12));
        markerInfoList = listaLuoghiActivity.getLuoghi();
        mMap.setOnMyLocationChangeListener(location -> {
            if (location != null) {
                LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                updateSearchCircle(currentLatLng);

                for (MarkerInfo markerInfo : markerInfoList) {
                    float[] distance = new float[1];
                    Location.distanceBetween(currentLatLng.latitude, currentLatLng.longitude,
                            markerInfo.getLatitudine(), markerInfo.getLongitudine(), distance);

                    if (distance[0] <= RADIUS) {
                        LatLng point = new LatLng(markerInfo.getLatitudine(), markerInfo.getLongitudine());
                        showHiddenPoint(point, markerInfo);
                    }
                }
            }
        });

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            enableMyLocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            } else {
                Toast.makeText(this, "L'autorizzazione alla posizione è necessaria per utilizzare l'app", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
    }

    private void updateSearchCircle(LatLng center) {
        int strokeColor = Color.argb(100, 255, 0, 0);
        int fillColor = Color.argb(50, 255, 0, 0);
        if (searchCircle == null) {
            CircleOptions circleOptions = new CircleOptions()
                    .center(center)
                    .radius(RADIUS)
                    .strokeWidth(2)
                    .strokeColor(strokeColor)
                    .fillColor(fillColor);
            searchCircle = mMap.addCircle(circleOptions);
        } else {
            searchCircle.setCenter(center);
        }
    }

    private void showHiddenPoint(LatLng point, MarkerInfo markerInfo) {
        int index = visibleMarkers.size();
        if (index < listaLuoghiActivity.getLuoghi().size()) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(point)
                    .title(markerInfo.getNome());
            Marker marker = mMap.addMarker(markerOptions);
            visibleMarkers.add(marker);
        }
    }

    public void scanQRCode(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null) {
            String scannedQRCode = result.getContents();
            markerInfoList = listaLuoghiActivity.getLuoghi();

            for (Marker marker : visibleMarkers) {
                String qrCode = marker.getTitle();
                if (qrCode != null && qrCode.equals(scannedQRCode)) {
                    for(MarkerInfo markinfo : markerInfoList) {
                        Log.i("Prima dell' if: ", "Nome: " +markinfo.getNome()+""+markinfo.getTrovato());
                        if(markinfo.getNome().equals(qrCode)){
                            markinfo.setTrovato(true);
                        }
                        Log.i("Dopo dell' if: ", "Nome: " +markinfo.getNome()+""+markinfo.getTrovato());
                    }
                    marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    Toast.makeText(this, qrCode, Toast.LENGTH_LONG).show();
                    break;
                }
            }
            if (luoghiAdapter != null) {
                luoghiAdapter.notifyDataSetChanged();
            }
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.qr) {
            scanQRCode(null);
        }else if (id == R.id.lista) {
            Intent intent = new Intent(this, ListaLuoghiActivity.class);
            startActivity(intent);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
