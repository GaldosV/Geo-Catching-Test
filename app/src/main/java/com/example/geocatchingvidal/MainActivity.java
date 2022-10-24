package com.example.geocatchingvidal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity {
    // metodo para leer datos en hexadecimal
    static String enHexadecimal (byte[] inf){

        String hexadecimal ="";

        for (int i=0; i<inf.length; i++){

            String hx = Integer.toHexString(inf[i] & 0xFF);

            if (hx.length()==1){

                hexadecimal+="0";

            }

            hexadecimal+=hx;

        }

        return hexadecimal;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);


        // inicializamos la base de datos en el main
       // BD inicio = new BD(MainActivity.this);

     //  inicio.onUpgrade(inicio.getWritableDatabase(),1,2);
        // ahora llamamos al boton del login y se le añade el listener que lo que hara sera recoger los datos

        Button bConexion = findViewById(R.id.buttonConexion);
        EditText nombre = (EditText) findViewById(R.id.editNombre);
        EditText contr = (EditText) findViewById(R.id.editPassword);
        Button binvitado = findViewById(R.id.bInvitado);
        TextView error = (TextView) findViewById(R.id.textView);
        // cuando se pulsa el boton conexion coge tus datos y los chekea con la base de datos
        bConexion.setOnClickListener(new View.OnClickListener() {
    @SuppressLint("Range")
    @Override
    public void onClick(View view) {
        //llamo a la base de datos
        BD inicio = new BD(MainActivity.this);
        inicio.onUpgrade(inicio.getWritableDatabase(),1,2);

        SQLiteDatabase db = inicio.getWritableDatabase();
        // hasheamos la contraseña para comprobarla con la contraseña guardada
        MessageDigest mensajedigest;
        try {
            mensajedigest = MessageDigest.getInstance("SHA-1");
            byte hash [] = contr.getText().toString().getBytes();
            mensajedigest.update(hash);
            byte res[] = mensajedigest.digest();
            Cursor cursor = db.rawQuery("SELECT u.*  from usuario u where u.nombre  =? ", new String[]{nombre.getText().toString()});


            if(cursor.moveToFirst()){
               // cogemos la contraseña y la guardamos en parametro
                String hesh = cursor.getString(5);
                //creamos un usuario nuevo y le guardamos los resultados de la query
                Usuario usuario = new Usuario();
                usuario.setPkId(cursor.getInt(cursor.getColumnIndex("pkId")));
                usuario.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                usuario.setApellido1(cursor.getString(cursor.getColumnIndex("apellido1")));
                usuario.setApellido2(cursor.getString(cursor.getColumnIndex("apellido2")));
                usuario.setCorreo(cursor.getString(cursor.getColumnIndex("correo")));
                usuario.setContrasena(cursor.getString(cursor.getColumnIndex("contrasena")));
                usuario.setBandplantadas(cursor.getInt(cursor.getColumnIndex("bandplantadas")));
                usuario.setBandrecogidas(cursor.getInt(cursor.getColumnIndex("bandrecogidas")));

                // aqui compruebo que la contraseña introducida y la de la base de datos sean iguales y que los datos introducidos no sean nulos
                if(hesh.equalsIgnoreCase(enHexadecimal(res)) && !nombre.getText().toString().equals("") && !cursor.getString(0).equals("")){
                    // si todo es correcto cambia de vista
                    Intent intent = new Intent(MainActivity.this,Mapamain.class);
                    // ademas le envio el usuario guardado antes
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);


                }else{
                    //si hay un error se limpia el nombre y contraseña y se lo indica al usuario
                    System.out.println("Error en contraseña o usuario");
                    error.setText("Has introducido un usuario o contraseña incorrecto por favor reintroduce los Datos: ");
                    nombre.setText("");
                    contr.setText("");


                }
            }
        }catch (Exception e){

            System.out.println(e.getMessage());

        }


    }

});

        // si pulsas el boton de invitado te lleva a la vista directamente metiendote en la cuenta de invitado
        binvitado.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View view) {

                BD inicio = new BD(MainActivity.this);
                inicio.onUpgrade(inicio.getWritableDatabase(),1,2);

                SQLiteDatabase db = inicio.getWritableDatabase();
                // me guardo la query para coger el usuario invitado
                Cursor cursor = db.rawQuery("SELECT u.*  from usuario u where u.nombre  =? ", new String[]{"invitado"});

                if(cursor.moveToFirst()){
                    // cogemos la contraseña y la guardamos en parametro
                    String hesh = cursor.getString(5);
                    //creamos un usuario nuevo y le guardamos los resultados de la query
                    Usuario usuario = new Usuario();
                    usuario.setPkId(cursor.getInt(cursor.getColumnIndex("pkId")));
                    usuario.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                    usuario.setApellido1(cursor.getString(cursor.getColumnIndex("apellido1")));
                    usuario.setApellido2(cursor.getString(cursor.getColumnIndex("apellido2")));
                    usuario.setCorreo(cursor.getString(cursor.getColumnIndex("correo")));
                    usuario.setContrasena(cursor.getString(cursor.getColumnIndex("contrasena")));
                    usuario.setBandplantadas(cursor.getInt(cursor.getColumnIndex("bandplantadas")));
                    usuario.setBandrecogidas(cursor.getInt(cursor.getColumnIndex("bandrecogidas")));
                    Intent intent = new Intent(MainActivity.this,Mapamain.class);
                    // ademas le envio el usuario guardado antes
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);


            }
            }
        });

    }








}