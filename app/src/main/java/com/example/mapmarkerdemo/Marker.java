package com.example.mapmarkerdemo;

import com.google.android.gms.maps.model.LatLng;

public class Marker {
    LatLng coord;
    String title;
    String info;
    String url;

    public Marker(double lat, double lon, String title, String info, String url) {
        coord = new LatLng(lat, lon);
        this.title = title;
        this.info = info;
        this.url = url;
    }

    public LatLng getCoord() {
        return coord;
    }

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    public String getUrl() {
        return url;
    }
}
