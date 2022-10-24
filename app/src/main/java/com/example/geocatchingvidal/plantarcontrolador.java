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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.geocatchingvidal.databinding.PlantarMapBinding;

import java.util.ArrayList;
import java.util.List;
// Classe para la vista de plantqar banderas
public class plantarcontrolador extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private PlantarMapBinding binding;

  //  Usuario USU ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = PlantarMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // sacarbanderas();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // el punto inicial donde esta la vista
        mMap = googleMap;
        LatLng inicio = new LatLng(43.47285324820349, -3.785635139260104);
        mMap.addMarker(new MarkerOptions().position(inicio).title("Marcador en Santander"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(inicio));
        CameraPosition camp = new CameraPosition.Builder().target(inicio).zoom(15).build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camp));
     //   MarkerOptions marker = new MarkerOptions().position(new LatLng(point.latitude))
        // pintas los marcadores guardados en la base de datos
         sacarbanderas();
        // coges el usuario con el que estas "jugando "
        Usuario user = (Usuario) getIntent().getSerializableExtra("usuario");
        BD base = new BD(plantarcontrolador.this);
        SQLiteDatabase bd = base.getWritableDatabase();
      mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                Button bPlantar = findViewById(R.id.bcolocar);
                bPlantar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // aqui llamas a la base de datos y haces un insert con los datos cuando clikas en el boton de plantar
                        upbanderasplantadas(user,bd);
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        googleMap.addMarker(markerOptions);

                        insertarbandera(latLng.latitude,latLng.longitude);



                    }
                });


            }
        });




    }
    // metodo para insertar la bandera pasandole la longitud y la latitud
    public void insertarbandera(double latitud ,double longitud){
        EditText pista = (EditText) findViewById(R.id.editTPista);
        EditText nombre = (EditText) findViewById(R.id.editTNombre);
        Usuario usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        BD base = new BD (plantarcontrolador.this);
        SQLiteDatabase bd = base.getWritableDatabase();

        int pkid = usuario.getPkId();
        ContentValues cv = new ContentValues();
        cv.put("nombre",nombre.getText().toString());
        cv.put("pista",pista.getText().toString());
        cv.put("latitud",latitud);
        cv.put("longitud",longitud);
        cv.put("id_usuarioplanter",pkid);
        bd.insert("Localizacion",null,cv);
        // ademas pinta las banderas
        sacarbanderas();
    }
    // metodo para pintar banderas
    public void sacarbanderas(){
        BD base = new BD(plantarcontrolador.this);
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
            mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(LayoutInflater.from(getApplicationContext())));


        }



    }
    public void upbanderasplantadas( Usuario user ,SQLiteDatabase db){
        int pk = user.getPkId();
        ContentValues cv = new ContentValues();
        cv.put("bandplantadas",user.getBandplantadas()+1);

        db.update("usuario",cv ,"pkId =?",new String[]{String.valueOf(pk)});
        user.setBandplantadas(user.getBandplantadas()+1);

    }


}