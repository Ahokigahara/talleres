package com.example.talleres.ui.servicios;

public class Servicio {


    private int grupo;
    private String nombre;
    private int telefono;

    public Servicio(){

    }
    public Servicio(int grupo, String nombre, int telefono) {
        this.grupo = grupo;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}
