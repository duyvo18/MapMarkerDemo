package com.example.mapmarkerdemo;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    Location currentLoc;
    List<UniInfo> uniInfoList;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int request_code = 101;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        AddUniInfo();
        PrepareDropdownMenu();
        CreateMapTask();

        Toast.makeText(this,
                "Click marker to view info\nClick pop-up window to access website",
                Toast.LENGTH_LONG).show();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, request_code);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
               if (location != null) {
                   currentLoc = location;
                   Toast.makeText(getApplicationContext(),currentLoc.getLatitude()+""+currentLoc.getLongitude(),Toast.LENGTH_SHORT).show();
                   SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                   supportMapFragment.getMapAsync(MapsActivity.this);
               }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnInfoWindowClickListener(this);
        AddMarkers(uniInfoList);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uniInfoList.get(0).getCoord(), 10));
    }

    @Override
    public void onInfoWindowClick(com.google.android.gms.maps.model.Marker marker) {
        for (UniInfo uniInfo : uniInfoList) {
            if (marker.getTitle().equalsIgnoreCase(uniInfo.getTitle())) {
                Toast.makeText(this,
                        "Opening in browser...", Toast.LENGTH_SHORT).show();
                OpenBrowserToUrl(uniInfo.getUrl());
                return;
            }
        }
        Toast.makeText(this,
                "Can not find info for this marker", Toast.LENGTH_SHORT).show();
    }

    // TODO: implement onClickButton
    public void onCLickButton(View view) {
        switch (view.getId()) {
            case R.id.Btn_findUni:
                findUniLocation();
                break;
            case R.id.Btn_findCurrentLoc:
                findCurrentLocation();
                break;
            case R.id.Btn_navigateToUni:
                // TODO: navigate to uni
                break;
        }
    }

    private void findUniLocation() {
        AutoCompleteTextView source = (AutoCompleteTextView) findViewById(R.id.Menu_dropdown_textview);
        String Source = source.getText().toString();
        for (UniInfo uniInfo : uniInfoList) {
            if (Source.equalsIgnoreCase(uniInfo.getTitle())) {
                LatLng latLng = uniInfo.getCoord();
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                return;
            }
        }
    }

    private void findCurrentLocation() {
        LatLng latLng = new LatLng(currentLoc.getLatitude(), currentLoc.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title("Current Location");
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
        mMap.addMarker(markerOptions);
    }

    private void AddMarkers(List<UniInfo> uniInfoList) {
        for (UniInfo uniInfo : uniInfoList) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(uniInfo.getCoord())
                    .title(uniInfo.getTitle())
                    .snippet(uniInfo.getInfo()));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case request_code:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    fetchLastLocation();
                break;
        }
    }

    private void OpenBrowserToUrl(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void AddUniInfo() {
        uniInfoList = List.of(
                new UniInfo(10.762913, 106.6821717,
                        "VNUHCM - University of Science, Dist.5 Campus",
                        "227 Nguyen Van Cu, District 5, HCMC",
                        "https://www.hcmus.edu.vn/"),
                new UniInfo(10.7639647, 106.6814351,
                        "Le Hong Phong High School for the Gifted",
                        "235 Nguyen Van Cu, District 5, HCMC",
                        "http://www.thpt-lehongphong-tphcm.edu.vn/"),
                new UniInfo(10.7613832, 106.6821711,
                        "Ho Chi Minh University of Education",
                        "280 An Duong Vuong, District 5, HCMC",
                        "https://hcmue.edu.vn/vi/"),
                new UniInfo(10.7599171, 106.6822583,
                        "Saigon University",
                        "273 An Duong Vuong, District 5, HCMC",
                        "https://sgu.edu.vn/?lang=en"),
                new UniInfo(10.7733743, 106.6606193,
                        "VNUHCM - University of Technology, Dist.10 Campus",
                        "268 Ly Thuong Kiet, District 10, HCMC",
                        "https://www.hcmut.edu.vn/vi"),
                new UniInfo(10.785866, 106.7005199,
                        "VNUHCM - University of Social Sciences and Humanities",
                        "10-12 Dinh Tien Hoang, District 1, HCMC",
                        "https://hcmussh.edu.vn/"),
                new UniInfo(10.8775901, 106.7994309,
                        "VNUHCM - International University",
                        "Quarter 16, Thu Duc City, HCMC",
                        "https://hcmiu.edu.vn/"),
                new UniInfo(10.8699237, 106.8016194,
                        "VNUHCM - University of Information Technology",
                        "Song Hanh, Quarter 16, Thu Duc City, HCMC",
                        "https://www.uit.edu.vn/"),
                new UniInfo(10.8709374, 106.7760666,
                        "VNUHCM - University of Economics and Law",
                        "669 1A Route, Quarter 3, Thu Duc City, HCMC",
                        "https://www.uel.edu.vn/"),
                new UniInfo(10.8703401, 106.7962005,
                        "VNUHCM - School of Medicine",
                        "16/40 1A Route, Thu Duc City, HCMC",
                        "http://www.pdt-medvnu.info/"),
                new UniInfo(10.7969093, 106.6714999,
                        "Academy of Aviation of Vietnam",
                        "104 Nguyen Van Troi, Phu Nhuan District, HCMC",
                        "https://vaa.edu.vn/"),
                new UniInfo(10.8223018, 106.6852621,
                        "Industrial University of Ho Chi Minh City",
                        "12 Nguyen Van Bao, Go Vap District, HCMC",
                        "http://www.iuh.edu.vn/"),
                new UniInfo(10.8065201, 106.626668,
                        "Ho Chi Minh City University of Food Industry",
                        "140 Le Trong Tan, Tan Phu District, HCMC",
                        "https://ts.hufi.edu.vn/"),
                new UniInfo(10.8652492, 106.6166996,
                        "Ho Chi Minh City University of Transport",
                        "70 To Ky, Tan Chanh Hiep, District 12, HCMC",
                        "http://ut.edu.vn/"),
                new UniInfo(10.7823585, 106.6919285,
                        "Ho Chi Minh City Architecture University",
                        "196 Pasteur, District 3, HCMC",
                        "http://www.uah.edu.vn/"),
                new UniInfo(10.7731605, 106.6754147,
                        "University Of Economics Ho Chi Minh City",
                        "91C 3/2 Street, District 10, HCMC",
                        "http://www.ueh.edu.vn/"),
                new UniInfo(10.7675602, 106.7033313,
                        "Ho Chi Minh City University of Law",
                        "2 Nguyen Tat Thanh, District 4, HCMC",
                        "http://www.hcmulaw.edu.vn/"),
                new UniInfo(10.7649115, 106.698634,
                        "Ho Chi Minh City Open University",
                        "97 Vo Van Tan, District 3, HCMC",
                        "http://www.ou.edu.vn/"),
                new UniInfo(10.8024548, 106.6932305,
                        "Ho Chi Minh City Fine Arts University",
                        "5 Phan Dang Luu, Binh Thanh District, HCMC",
                        "http://www.hcmufa.edu.vn/"),
                new UniInfo(10.7648853, 106.7052001,
                        "Ho Chi Minh City Banking University",
                        "35 Nguyen Tat Thanh, District 4, HCMC",
                        "https://buh.edu.vn/"),
                new UniInfo(10.8069083, 106.7108633,
                        "Foreign Trade University, 2nd Campus",
                        "15 D5 Street, Binh Thanh District, HCMC",
                        "http://cs2.ftu.edu.vn/"),
                new UniInfo(10.867906, 106.7856724,
                        "Ho Chi Minh City University of Agriculture and Forestry",
                        "6 1A Route, Di An, Binh Duong Province",
                        "https://www.hcmuaf.edu.vn/"),
                new UniInfo(110.8507267, 106.7697336,
                        "Ho Chi Minh City University of Technology and Education",
                        "1 Vo Van Ngan, Thu Duc City, HCMC",
                        "http://hcmute.edu.vn/"),
                new UniInfo(10.7320073, 106.6968373,
                        "Ton Duc Thang University",
                        "19 Nguyen Huu Tho, District 7, HCMC",
                        "https://www.tdtu.edu.vn/"),
                new UniInfo(10.7627221, 106.6910583,
                        "Van Lang University",
                        "45 Nguyen Khac Nhu, District 1, HCMC",
                        "https://www.vanlanguni.edu.vn/")
        );
    }

    private void PrepareDropdownMenu() {
        String[] stringArray = getResources().getStringArray(R.array.uni_name);
        ArrayAdapter<String> stringArrayAdapter =
                new ArrayAdapter<>(this, R.layout.menu_dropdown, stringArray);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.Menu_dropdown_textview);
        autoCompleteTextView.setAdapter(stringArrayAdapter);
    }

    private void CreateMapTask() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

}