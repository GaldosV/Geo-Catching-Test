package com.example.geocatchingvidal;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.geocatchingvidal.databinding.MapsRecogerBinding;

import java.util.ArrayList;
import java.util.List;
// clase para recoger las banderas
public class recogerControlador extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapsRecogerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = MapsRecogerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //recojo el objeto usuario que me envie de mapamain
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // donde esta el marcador inicial
        LatLng inicio = new LatLng(43.47285324820349, -3.785635139260104);
        mMap.addMarker(new MarkerOptions().position(inicio).title("Marcador en Santander"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(inicio));
        CameraPosition camp = new CameraPosition.Builder().target(inicio).zoom(15).build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camp));
// pinta las banderas
        sacarbanderas();
        TextView update = (TextView) findViewById(R.id.Textband);
        // tienes un text que te dice cuantos caches has recogido
        update.setText("Numero de caches recogidos :  0 " );

        // hacer listener del mMap
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            int num = 0;
            Usuario user = (Usuario) getIntent().getSerializableExtra("usuario");
            BD base = new BD(recogerControlador.this);
            SQLiteDatabase bd = base.getWritableDatabase();
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                Button bRecoger = findViewById(R.id.BotRecoger);

                bRecoger.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nombreMarcador =  marker.getTitle();
                        // añade + 1 a las banderas recogidas
                        upbanderasrecogidas(user,bd);
                        Cursor cursor = bd.rawQuery("select l.nombre,l.latitud,l.longitud,l.pista from Localizacion l where l.nombre =?", new String[]{nombreMarcador});
                        List<Object[]> objeto = new ArrayList<>();
                        //borro el marcador y lo repinto
                        marker.remove();
                        Object[] cache;
                        while(cursor.moveToNext()){
                            cache = new Object[4];
                            cache[0] = cursor.getString(0);
                            cache[1] = cursor.getDouble(1);
                            cache[2] = cursor.getDouble(2);
                            cache[3]= cursor.getString(3);
                            objeto.add(cache);
                        }
                        for (int i = 0 ; i <objeto.size(); i++){
                            String nombre = (String) objeto.get(i)[0];
                            double latitud = (double) objeto.get(i)[1];
                            double longitud = (double) objeto.get(i)[2];
                            String pista = (String) objeto.get(i)[3];
                            Marker marker;


                            mMap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(String.valueOf(nombre)).snippet(pista).icon(BitmapDescriptorFactory.fromResource(R.drawable.abierto))).showInfoWindow();
                            mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(LayoutInflater.from(getApplicationContext())));

                        }
                         num++;
                        update.setText("Numero de caches recogidos : " +num);
                    }
                });
                return false;

            }
        });
    }
// metodo para pintar banderas
    public void sacarbanderas(){
        BD base = new BD(recogerControlador.this);
        SQLiteDatabase bd = base.getWritableDatabase();

        List<Object[]> objeto = new ArrayList<>();

        Cursor cursor = bd.rawQuery("select l.nombre,l.latitud,l.longitud from Localizacion l",null);

        Object[] cache;
        while(cursor.moveToNext()){
            cache = new Object[3];
            cache[0] = cursor.getString(0);
            cache[1] = cursor.getDouble(1);
            cache[2] = cursor.getDouble(2);

            objeto.add(cache);
        }
        for (int i = 0 ; i <objeto.size(); i++){
            String nombre = (String) objeto.get(i)[0];
            double latitud = (double) objeto.get(i)[1];
            double longitud = (double) objeto.get(i)[2];

            // mMap.addMarker(new MarkerOptions().position(new LatLng(latitud,longitud)).title(String.valueOf(pkid)));
            mMap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(String.valueOf(nombre)).icon(BitmapDescriptorFactory.fromResource(R.drawable.cofre))).showInfoWindow();
            // icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.cofre)));
            // marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.cofre));
            mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(LayoutInflater.from(getApplicationContext())));

        }



    }
    // metodo para updatear las banderas recogidas
    public void upbanderasrecogidas( Usuario user ,SQLiteDatabase db){
        int pk = user.getPkId();

        ContentValues cv = new ContentValues();
        cv.put("bandrecogidas",user.getBandrecogidas()+1);

        db.update("usuario",cv ,"pkId =?",new String[]{String.valueOf(pk)});
        user.setBandrecogidas(user.getBandplantadas()+1);

    }
}