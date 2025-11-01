package com.example.patsmovieapp;

import java.io.Serializable;

public class Pelicula implements Serializable {
    private String nombre;
    private boolean vista;

    public Pelicula(String nombre, boolean vista) {
        this.nombre = nombre;
        this.vista = vista;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public boolean isVista() { return vista; }
    public void setVista(boolean vista) { this.vista = vista; }
}