package com.example.geocatchingvidal;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.geocatchingvidal.databinding.ActivityMapamainBinding;

import java.util.ArrayList;
import java.util.List;

public class Mapamain extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapamainBinding binding;
    Button Colocar ;
    Button Recoger ;
    ImageButton refrescar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapamainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Colocar = (Button) findViewById(R.id.Bplantar);
        Recoger = (Button) findViewById(R.id.Brecoger);
        refrescar = (ImageButton) findViewById(R.id.imrefre);
        //recojo el objeto usuario que me envie de main activity
        Usuario user = (Usuario) getIntent().getSerializableExtra("usuario");

        // si pulsas el boton de colocar simplemente te lleva a la  vista de colocar
        Colocar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Mapamain.this,plantarcontrolador.class);
                // envio el objeto usuario a la nueva pantalla
                intent.putExtra("usuario",user);
                startActivity(intent);


            }
        });
        // si pulsas el boton de recoger te lleva a la vista de recoger
        Recoger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Mapamain.this,recogerControlador.class);
                // envio el objeto usuario a la nueva pantalla
                intent.putExtra("usuario",user);
                startActivity(intent);
            }
        });
        // pulsar un boton de refrescar para repintar banderas
        refrescar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sacarbanderas();
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // ponemos el marcador en santander y movemos la camara

        LatLng inicio = new LatLng(43.47285324820349, -3.785635139260104);
        mMap.addMarker(new MarkerOptions().position(inicio).title("Marcador en Santander"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(inicio));
        CameraPosition camp = new CameraPosition.Builder().target(inicio).zoom(15).build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camp));
        // pintamos la bandera
        sacarbanderas();


    }
    // metodo para pintar banderas en el mapa
    public void sacarbanderas(){
        BD base = new BD(Mapamain.this);
        SQLiteDatabase bd = base.getWritableDatabase();

        List<Object[]> objeto = new ArrayList<>();
        // haces la query de las banderas
        Cursor cursor = bd.rawQuery("select l.nombre,l.latitud,l.longitud from Localizacion l",null);
    // las guardas en un objeto
        Object[] cache;
        while(cursor.moveToNext()){
            cache = new Object[3];
            cache[0] = cursor.getString(0);
            cache[1] = cursor.getDouble(1);
            cache[2] = cursor.getDouble(2);
            objeto.add(cache);


        }
        // las pintas
        for (int i = 0 ; i <objeto.size(); i++){
            String nombre = (String) objeto.get(i)[0];
            double latitud = (double) objeto.get(i)[1];
            double longitud = (double) objeto.get(i)[2];
            // aqui coges los datos y pintas
            // mMap.addMarker(new MarkerOptions().position(new LatLng(latitud,longitud)).title(String.valueOf(pkid)));
            mMap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(String.valueOf(nombre)).icon(BitmapDescriptorFactory.fromResource(R.drawable.cofre))).showInfoWindow();
            mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(LayoutInflater.from(getApplicationContext())));


        }



    }


}