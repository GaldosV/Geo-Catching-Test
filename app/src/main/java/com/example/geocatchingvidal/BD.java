package com.example.geocatchingvidal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.math.BigDecimal;

public class BD extends SQLiteOpenHelper {
// la base de datos de la aplicacion
    private static final String db_name = "GeocatchingVidal";
    private static final int db_version =1;

    public static final String NombreTablaL = "Localizacion";
    public static final String pkIdL_tab ="pkId";
    public static final String nombre_tab = "nombre";
    public static final String pista_tab ="pista";
    public static final String latitud_tab ="latitud";
    public static final String longitud_tab ="longitud";
    public static final String idplant_tab ="id_usuarioplanter";

    public final String NombreTablaU = "Usuario";
    public final String pkIdU_tab ="pkId";
    public final String nombreU_tab ="nombre";
    public final String apellido1_tab ="apellido1";
    public final String apellido2_tab ="apellido2";
    public final String correo_tab ="correo";
    public final String contrasena_tab ="contrasena";
    public final String bandplantadas_tab = "bandplantadas";
    public final String bandrecogidas_tab = "bandrecogidas";

    public BD(Context context ){ super (context, db_name,null,db_version);}



    @Override
    public void onCreate(SQLiteDatabase BD) {
    //se crea la base de datos de localizacion
       String createTable = "create table Localizacion(" +
               "pkId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
               "nombre VARCHAR(25) not null," +
               "pista VARCHAR(500)," +
               "latitud decimal(15,7)," +
               "longitud decimal(15,7)," +

               "id_usuarioplanter INTEGER not null," +
               "FOREIGN KEY (id_usuarioPlanter) REFERENCES Usuario(id)" +

               ")";
        BD.execSQL(createTable);
// se crea la tabla de usuario
            createTable ="        create table Usuario (" +
                    "                pkId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "                nombre VARCHAR(50) NOT NULL," +
                    "                apellido1 VARCHAR(50)," +
                    "                apellido2 VARCHAR(50)," +
                    "                correo VARCHAR(50)," +
                    "                contrasena VARCHAR(50)," +
                    "                bandplantadas INTEGER," +
                    "                bandrecogidas INTEGER)";
        BD.execSQL(createTable);
       ;
// hacemos inserts para hacer prueba
        //43.472159, -3.785321
 insertUsuario(BD,"a","a","a","a","86f7e437faa5a7fce15d1ddcb9eaeaea377667b8",1,1);
        insertUsuario(BD,"invitado","invi","tado","invitado@gmail.com","86f7e437faa5a7fce15d1ddcb9eaeaea377667b8",0,0);
// metemos varias localizaciones base
        insertLocalizacion(BD,"Cache Marino","esta cerca del mar",43.474675,-3.784275,1);
 insertLocalizacion(BD,"Punta de la Piedra","esta cerca de una roca",43.474309,-3.783325,1);
 insertLocalizacion(BD,"Kebab Vitalicio","El mejor kebab de la zona",43.472458,-3.784001,1);
        insertLocalizacion(BD,"Bola del mundo ","El mundo",43.474173,-3.783478,1);
        insertLocalizacion(BD,"La fuente","Agua para los sedientos",43.472159,-3.785321,1);
// hacemos el usuario invitado
    }

    @Override
    public void onUpgrade(SQLiteDatabase BD, int i, int i1) {
    // al upgradearse se borran las tablas

        BD.execSQL("DROP TABLE IF EXISTS Localizacion");
        BD.execSQL("DROP TABLE IF EXISTS Usuario");
        // si se llama el onupgrade se recrea la base de datos y los inserts
        onCreate(BD);

    }

// metodo para insertar un usuario
    public void insertUsuario(SQLiteDatabase db,String nombre, String apellido1,String apellido2,String correo,String contrasena,int  bandplantadas , int bandrecogidas ){

       // db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(nombreU_tab,nombre);
        values.put(apellido1_tab,apellido1);
        values.put(apellido2_tab,apellido2);
        values.put(correo_tab,correo);
        values.put(contrasena_tab,contrasena);
        values.put(bandplantadas_tab,bandplantadas);
        values.put(bandrecogidas_tab,bandrecogidas);
        db.insert(NombreTablaU,null,values);

    }
    // metodo para insertar una localizzacion
    public static void insertLocalizacion(SQLiteDatabase db,String nombre, String pista, Double latitud,Double longitud,int idplant ){
       // db= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nombre_tab,nombre);
        values.put(pista_tab,pista);
        values.put(latitud_tab,String.valueOf(latitud));
        values.put(longitud_tab,String.valueOf(longitud));
        values.put(idplant_tab,idplant);

        db.insert(NombreTablaL,null,values);
    }

    // metodo para updatear las banderas plantadas
    public void upbanderasplantadas(int pk ,SQLiteDatabase db){
        int num = 0;

        Cursor cursor = db.rawQuery("select bandplantadas from usuario where pkId =?",new String[]{String.valueOf(pk)});

        if(cursor.moveToFirst()){
            num = cursor.getInt(0);

        }
        num = num+1;
        cursor = db.rawQuery("UPDATE usuario SET bandplantadas = num WHERE  pkId =?",new String[]{String.valueOf(pk)});

    }


}