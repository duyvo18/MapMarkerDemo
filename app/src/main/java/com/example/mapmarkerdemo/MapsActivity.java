package com.example.mapmarkerdemo;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    List<Marker> markers = List.of
            (
            new Marker(10.762913,106.6821717,
                    "VNUHCM - University of Science",
                    "227 Nguyen Van Cu, District 5, HCMC",
                    "https://www.hcmus.edu.vn/"),
            new Marker(10.7639647,106.6814351,
                    "Le Hong Phong High School for the Gifted",
                    "235 Nguyen Van Cu, District 5, HCMC",
                    "http://www.thpt-lehongphong-tphcm.edu.vn/"),
            new Marker(10.7613832,106.6821711,
                    "Ho Chi Minh Universiry of Education",
                    "280 An Duong Vuong, District 5, HCMC",
                    "https://hcmue.edu.vn/vi/"),
            new Marker(10.7599171,106.6822583,
                    "Saigon University",
                    "273 An Duong Vuong, District 5, HCMC",
                    "https://sgu.edu.vn/?lang=en")
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toast.makeText(this,
                "Click marker to view info\nClick pop-up window to access website", Toast.LENGTH_LONG).show();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnInfoWindowClickListener(this);
        AddMarkers(markers);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers.get(0).getCoord(), 15f));

    }

    private void AddMarkers(List<Marker> markers) {
        for (Marker marker : markers) {
            mMap.addMarker(new MarkerOptions()
                    .position(marker.getCoord())
                    .title(marker.getTitle())
                    .snippet(marker.getInfo()));
        }
    }

    @Override
    public void onInfoWindowClick(com.google.android.gms.maps.model.Marker marker) {

        for (Marker defMarker : markers) {
            if (marker.getTitle().equalsIgnoreCase(defMarker.getTitle())) {
                Toast.makeText(this,
                        "Opening in browser...", Toast.LENGTH_SHORT).show();
                OpenBrowserToUrl(defMarker.getUrl());
                return;
            }
        }
        Toast.makeText(this,
                "Can not find info for this marker", Toast.LENGTH_SHORT).show();
    }

    private void OpenBrowserToUrl(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}