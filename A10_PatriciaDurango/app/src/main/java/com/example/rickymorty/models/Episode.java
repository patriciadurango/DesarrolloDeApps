package com.example.rickymorty.models;

public class Episode {
    private int id;
    private String name;
    private String air_date;
    private String episode;

    // Constructor vacío
    public Episode() {}

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAir_date() {
        return air_date;
    }

    public String getEpisode() {
        return episode;
    }
}