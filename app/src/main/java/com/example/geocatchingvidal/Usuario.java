package com.example.geocatchingvidal;

import java.io.Serializable;

public class Usuario implements Serializable {

    public int pkId;
    public String nombre;
    public String apellido1;
    public String apellido2;
    public String correo;
    public String contrasena;
    public int bandplantadas;
    public int bandrecogidas;

    public Usuario() {

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

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getBandplantadas() {
        return bandplantadas;
    }

    public void setBandplantadas(int bandplantadas) {
        this.bandplantadas = bandplantadas;
    }

    public int getBandrecogidas() {
        return bandrecogidas;
    }

    public void setBandrecogidas(int bandrecogidas) {
        this.bandrecogidas = bandrecogidas;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "pkId=" + pkId +
                ", nombre='" + nombre + '\'' +
                ", apellido1='" + apellido1 + '\'' +
                ", apellido2='" + apellido2 + '\'' +
                ", correo='" + correo + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", bandplantadas=" + bandplantadas +
                ", bandrecogidas=" + bandrecogidas +
                '}';
    }
}