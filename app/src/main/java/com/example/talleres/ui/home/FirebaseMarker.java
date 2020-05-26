package com.example.talleres.ui.home;

public class FirebaseMarker {

    public String nombre;
    public String direccion;
    public int telefono;
    public double latitud;
    public double longitud;


    //required empty constructor
    public FirebaseMarker() {
    }

    public FirebaseMarker(String nombre, String direccion, int telefono, double latitude, double longitude) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.latitud = latitude;
        this.longitud = longitude;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String toString(){
        return this.direccion+","+this.telefono;
    }
}
