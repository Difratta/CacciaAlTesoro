package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplication.R;
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
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final double GENOA_LATITUDE = 44.4056;
    private static final double GENOA_LONGITUDE = 8.9463;
    private static final int RADIUS = 5000;

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private List<LatLng> hiddenPoints;
    private List<Marker> visibleMarkers;
    private Circle searchCircle;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private List<String> qrCodes = Arrays.asList(
            "Complimenti hai trovato l'oggetto n.1",
            "Complimenti hai trovato l'oggetto n.2",
            "Complimenti hai trovato l'oggetto n.3",
            "Complimenti hai trovato l'oggetto n.4",
            "Complimenti hai trovato l'oggetto n.5",
            "Complimenti hai trovato l'oggetto n.6",
            "Complimenti hai trovato l'oggetto n.7",
            "Complimenti hai trovato l'oggetto n.8",
            "Complimenti hai trovato l'oggetto n.9",
            "Complimenti hai trovato l'oggetto n.10"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        hiddenPoints = new ArrayList<>();
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

        generateHiddenPoints();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng genoa = new LatLng(GENOA_LATITUDE, GENOA_LONGITUDE);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(genoa, 12));

        mMap.setOnMyLocationChangeListener(location -> {
            if (location != null) {
                LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                updateSearchCircle(currentLatLng);

                for (LatLng point : hiddenPoints) {
                    float[] distance = new float[1];
                    Location.distanceBetween(currentLatLng.latitude, currentLatLng.longitude,
                            point.latitude, point.longitude, distance);

                    if (distance[0] <= RADIUS) {
                        showHiddenPoint(point);
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

    private void generateHiddenPoints() {
        List<LatLng> fixedPoints = Arrays.asList(
                new LatLng(44.412, 8.950),
                new LatLng(44.410, 8.951),
                new LatLng(44.408, 8.948),
                new LatLng(44.409, 8.946),
                new LatLng(44.406, 8.947),
                new LatLng(44.407, 8.949),
                new LatLng(44.404, 8.950),
                new LatLng(44.402, 8.947),
                new LatLng(44.403, 8.946),
                new LatLng(44.400, 8.945)
        );



        for (LatLng point : fixedPoints) {
            hiddenPoints.add(point);
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

    private void showHiddenPoint(LatLng point) {
        int index = visibleMarkers.size();
        if (index < qrCodes.size()) {
            String qrCode = qrCodes.get(index);

            MarkerOptions markerOptions = new MarkerOptions()
                    .position(point)
                    .title(getRandomPhrase())
                    .snippet(qrCode);
            Marker marker = mMap.addMarker(markerOptions);
            visibleMarkers.add(marker);
        }
    }

    private String getRandomPhrase() {
        String[] phrases = {
                "L'OGGETTO SI TROVA VICINO A UNA SCRITTA BLU",
                "L'OGGETTO SI TROVA SOTTO A UNA MACCHINA",
                "L'OGGETTO SI TROVA SOPRA A UN PALAZZO",
                "L'OGGETTO SI TROVA ALLA DESTRA DI UNO DEI NEGOZI PIÚ IMPORTANTI DELLA ZONA",
                "L'OGGETTO SI TROVA ALLA SINISTRA DI UNA STATUA"
        };

        Random random = new Random();
        int index = random.nextInt(phrases.length);
        return phrases[index];
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

            for (Marker marker : visibleMarkers) {
                String qrCode = marker.getSnippet();
                if (qrCode != null && qrCode.equals(scannedQRCode)) {
                    marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    Toast.makeText(this, qrCode, Toast.LENGTH_SHORT).show();
                    break;
                }
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
