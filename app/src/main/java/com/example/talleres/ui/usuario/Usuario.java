package com.example.talleres.ui.usuario;

public class Usuario {

    private String correo;
    private String nombre;
    private String direccion;
    private String barrio;
    private String pass;

    public Usuario() {

    }

    public Usuario(String correo, String pass) {
        this.correo = correo;
        this.pass = pass;
    }

    public Usuario(String correo, String nombre, String direccion, String barrio,String pass) {
        this.correo = correo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.barrio = barrio;
        this.pass=pass;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
