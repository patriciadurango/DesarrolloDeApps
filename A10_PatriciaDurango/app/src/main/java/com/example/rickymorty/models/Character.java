package com.example.rickymorty.models;

public class Character {
    private int id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private String image;

    // Constructor vacío
    public Character() {}

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getSpecies() {
        return species;
    }

    public String getType() {
        return type != null && !type.isEmpty() ? type : "Desconocido";
    }

    public String getGender() {
        return gender;
    }

    public String getImage() {
        return image;
    }
}