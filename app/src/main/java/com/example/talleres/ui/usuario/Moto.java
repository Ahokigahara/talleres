package com.example.talleres.ui.usuario;

public class Moto {
    private String usuario;
    private int modelo;
    private String marca;
    private int cilindraje;

    public Moto() {
    }

    public Moto(String usuario, int modelo, String marca, int cilindraje) {
        this.usuario = usuario;
        this.modelo = modelo;
        this.marca = marca;
        this.cilindraje = cilindraje;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getModelo() {
        return modelo;
    }

    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getCilindraje() {
        return cilindraje;
    }

    public void setCilindraje(int cilindraje) {
        this.cilindraje = cilindraje;
    }
}
