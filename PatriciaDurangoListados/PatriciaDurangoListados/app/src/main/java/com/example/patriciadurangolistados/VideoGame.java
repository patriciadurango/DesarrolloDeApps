package com.example.patriciadurangolistados;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class VideoGame implements Serializable {
    private int id;
    private String titulo;
    private String genero;
    private int anoLanzamiento;
    private float calificacion;
    private Date fechaEstreno;

    private static int contadorId = 0;

    public VideoGame(String titulo, String genero, int anoLanzamiento, float calificacion, Date fechaEstreno) {
        this.id = ++contadorId;
        this.titulo = titulo;
        this.genero = genero;
        this.anoLanzamiento = anoLanzamiento;
        this.calificacion = calificacion;
        this.fechaEstreno = fechaEstreno;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    public int getAnoLanzamiento() {
        return anoLanzamiento;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public Date getFechaEstreno() {
        return fechaEstreno;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setAnoLanzamiento(int anoLanzamiento) {
        this.anoLanzamiento = anoLanzamiento;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public void setFechaEstreno(Date fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }

    public String getFechaEstrenoFormateada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(fechaEstreno);
    }

    public static void setContadorId(int contador) {
        contadorId = contador;
    }
}