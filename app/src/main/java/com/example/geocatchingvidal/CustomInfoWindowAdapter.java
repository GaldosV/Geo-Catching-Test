package com.example.geocatchingvidal;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private static final String TAG = "CustomInfoWindowAdapter";
    private LayoutInflater inflater;

    public CustomInfoWindowAdapter(LayoutInflater inflater){
        this.inflater = inflater;
    }

// esta clase es para poder tener el pequeño pop up cuando pinchas los marcadores y su layout especial

    @Override
    public View getInfoContents(final Marker m) {
        //Carga layout personalizado.
        View v = inflater.inflate(R.layout.infowindow_layout, null);
        String[] info = m.getTitle().split("&");
        String url = m.getSnippet();
        ((TextView)v.findViewById(R.id.info_window_nombre)).setText(m.getTitle());
        ((TextView)v.findViewById(R.id.info_window_pista)).setText(m.getSnippet());
        return v;
    }

    @Override
    public View getInfoWindow(Marker m) {
        return null;
    }
}