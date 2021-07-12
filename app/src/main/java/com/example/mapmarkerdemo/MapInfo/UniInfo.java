package com.example.mapmarkerdemo.MapInfo;

import com.google.android.gms.maps.model.LatLng;

public class UniInfo {
    LatLng coord;
    String title;
    String info;
    String url;
    int logo;

    public UniInfo(double lat, double lon, String title, String info, String url, int logo) {
        coord = new LatLng(lat, lon);
        this.title = title;
        this.info = info;
        this.url = url;
        this.logo = logo;
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

    public int getLogo() {
        return logo;
    }
}
