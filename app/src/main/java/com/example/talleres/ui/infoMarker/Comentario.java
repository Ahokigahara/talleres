package com.example.talleres.ui.infoMarker;

public class Comentario
{

    public String comentario;
    public String nombreTaller;
    public String nombreUsuario;

    public Comentario(){

    }
    public Comentario(String comentario, String nombreTaller, String nombreUsuario) {
        this.comentario = comentario;
        this.nombreTaller = nombreTaller;
        this.nombreUsuario = nombreUsuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getNombreTaller() {
        return nombreTaller;
    }

    public void setNombreTaller(String nombreTaller) {
        this.nombreTaller = nombreTaller;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
