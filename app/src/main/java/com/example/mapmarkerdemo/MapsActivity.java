package com.example.mapmarkerdemo;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    List<MarkerInfo> markerInfoList = List.of
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
                            "https://sgu.edu.vn/?lang=en"),
                    new Marker(10.7733743,106.6606193,
                            "VNUHCM - Bach Khoa University District 10",
                            "268 Ly Thuong Kiet, District 10, HCMC",
                            "https://www.hcmut.edu.vn/vi"),
                    new Marker(10.785866,106.7005199,
                            "VNUHCM - University of Social Sciences and Humanities",
                            "10-12 Dinh Tien Hoang, District 1, HCMC",
                            "https://hcmussh.edu.vn/"),
                    new Marker(10.8775901,106.7994309,
                            "VNUHCM - International University",
                            "Quarter 16, Thu Duc City, HCMC",
                            "https://hcmiu.edu.vn/"),
                    new Marker(10.8699237,106.8016194,
                            "VNUHCM - University of Information Technology",
                            "Song Hanh, Quarter 16, Thu Duc City, HCMC",
                            "https://www.uit.edu.vn/"),
                    new Marker(10.8709374,106.7760666,
                            "VNUHCM - University of Economics and Law",
                            "669 1A Route, Quarter 3, Thu Duc City, HCMC",
                            "https://www.uel.edu.vn/"),
                    new Marker(10.8703401,106.7962005,
                            "VNUHCM - School of Medicine",
                            "16/40 1A Route, Thu Duc City, HCMC",
                            "http://www.pdt-medvnu.info/"),
                    new Marker(10.7969093,106.6714999,
                            "Academy of Aviation of Vietnam",
                            "104 Nguyen Van Troi, Phu Nhuan District, HCMC",
                            "https://vaa.edu.vn/"),
                    new Marker(10.8223018,106.6852621,
                            "Ho Chi Minh City University of Industry",
                            "12 Nguyen Van Bao, Go Vap District, HCMC",
                            "http://www.iuh.edu.vn/"),
                    new Marker(10.8065201,106.626668,
                            "Ho Chi Minh City University of Food Industry ",
                            "140 Le Trong Tan, Tan Phu District, HCMC",
                            "https://ts.hufi.edu.vn/"),
                    new Marker(10.8652492,106.6166996,
                            "Ho Chi Minh City University of Transportation",
                            "70 To Ky, Tan Chanh Hiep, District 12, HCMC",
                            "http://ut.edu.vn/"),
                    new Marker(10.7823585,106.6919285,
                            "Ho Chi Minh City Architecture University",
                            "196 Pasteur, District 3, HCMC",
                            "http://www.uah.edu.vn/"),
                    new Marker(10.7731605,106.6754147,
                            "VNUHCM - University of Economics",
                            "91C 3/2 Street, District 10, HCMC",
                            "http://www.ueh.edu.vn/"),
                    new Marker(10.7675602,106.7033313,
                            "Ho Chi Minh City University of Law",
                            "2 Nguyen Tat Thanh, District 4, HCMC",
                            "http://www.hcmulaw.edu.vn/"),
                    new Marker(10.7649115,106.698634,
                            "Ho Chi Minh City Open University",
                            "97 Vo Van Tan, District 3, HCMC",
                            "http://www.ou.edu.vn/"),
                    new Marker(10.8024548,106.6932305,
                            "Ho Chi Minh City Fine Arts University",
                            "5 Phan Dang Luu, Binh Thanh District, HCMC",
                            "http://www.hcmufa.edu.vn/"),
                    new Marker(10.7648853,106.7052001,
                            "Ho Chi Minh City Banking University",
                            "35 Nguyen Tat Thanh, District 4, HCMC",
                            "https://buh.edu.vn/"),
                    new Marker(10.8069083,106.7108633,
                            "Second Campus of University of Foreign Trade",
                            "15 D5 Street, Binh Thanh District, HCMC",
                            "http://cs2.ftu.edu.vn/"),
                    new Marker(10.867906,106.7856724,
                            "Ho Chi Minh City University of Agriculture and Sylviculture",
                            "6 1A Route, Di An, Binh Duong Province",
                            "https://www.hcmuaf.edu.vn/"),
                    new Marker(10.8024548,106.6932305,
                            "Ho Chi Minh City Fine Arts University",
                            "5 Phan Dang Luu, Binh Thanh District, HCMC",
                            "http://hcmute.edu.vn/"),
                    new Marker(110.8507267,106.7697336,
                            "Ho Chi Minh City University of Technology and Education",
                            "1 Vo Van Ngan, Thu Duc City, HCMC",
                            "http://hcmute.edu.vn/"),
                    new Marker(10.7320073,106.6968373,
                            "Ton Duc Thang University",
                            "19 Nguyen Huu Tho, District 7, HCMC",
                            "https://www.tdtu.edu.vn/"),
                    new Marker(10.7627221,106.6910583,
                            "Van Lang University",
                            "45 Nguyen Khac Nhu, District 1, HCMC",
                            "https://www.vanlanguni.edu.vn/")
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
                "Click marker to view info\nClick pop-up window to access website",
                Toast.LENGTH_LONG).show();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markerInfoList or lines, add listeners or move the camera.
     * In this case, we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnInfoWindowClickListener(this);
        AddMarkers(markerInfoList);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers.get(0).getCoord(), 10));

    }

    private void AddMarkers(List<MarkerInfo> markerInfoList) {
        for (MarkerInfo markerInfo : markerInfoList) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(markerInfo.getCoord())
                    .title(markerInfo.getTitle())
                    .snippet(markerInfo.getInfo()));
        }
    }

    @Override
    public void onInfoWindowClick(com.google.android.gms.maps.model.Marker marker) {

        for (MarkerInfo markerInfo : markerInfoList) {
            if (marker.getTitle().equalsIgnoreCase(markerInfo.getTitle())) {
                Toast.makeText(this,
                        "Opening in browser...", Toast.LENGTH_SHORT).show();
                OpenBrowserToUrl(markerInfo.getUrl());
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