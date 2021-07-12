package com.example.mapmarkerdemo.MapInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapmarkerdemo.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    Context context;

    public CustomInfoWindowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View contentView = LayoutInflater.from(context)
                .inflate(R.layout.infowindow_content, null);

        UniInfo uniInfo = (UniInfo)marker.getTag();

        if (uniInfo == null) {
            return null;
        }

        ImageView imageView = (ImageView)contentView.findViewById(R.id.logo);
        imageView.setImageDrawable(context.getResources().getDrawable(uniInfo.getLogo()));

        TextView titleView = (TextView)contentView.findViewById(R.id.title);
        titleView.setText(uniInfo.getTitle());

        TextView snippetView = (TextView)contentView.findViewById(R.id.snippet);
        snippetView.setText(uniInfo.getInfo());

        return contentView;
    }
}
