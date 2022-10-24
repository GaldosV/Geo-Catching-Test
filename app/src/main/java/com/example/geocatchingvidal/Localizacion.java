package com.example.geocatchingvidal;

import java.io.Serializable;
import java.math.BigDecimal;

public class Localizacion implements Serializable {
// clase que guarda objetos de localizacion

    public int pkId;
    public String nombre;
    public String pista;
    public Double latitud;
    public Double longitud;
    public int dificultad;
    public int terreno;
    public int id_usuarioplanter;

    public Localizacion() {
    }

    public int getPkId() {
        return pkId;
    }

    public void setPkId(int pkId) {
        this.pkId = pkId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPista() {
        return pista;
    }

    public void setPista(String pista) {
        this.pista = pista;
    }

    public Double getlatitud() {
        return latitud;
    }

    public void setlatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public int getDificultad() {
        return dificultad;
    }

    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }

    public int getTerreno() {
        return terreno;
    }

    public void setTerreno(int terreno) {
        this.terreno = terreno;
    }

    public int getId_usuarioplanter() {
        return id_usuarioplanter;
    }

    public void setId_usuarioplanter(int id_usuarioplanter) {
        this.id_usuarioplanter = id_usuarioplanter;
    }

    @Override
    public String toString() {
        return "Localizacion{" +
                "pkId=" + pkId +
                ", nombre='" + nombre + '\'' +
                ", pista='" + pista + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", dificultad=" + dificultad +
                ", terreno=" + terreno +
                ", id_usuarioplanter=" + id_usuarioplanter +
                '}';
    }
}