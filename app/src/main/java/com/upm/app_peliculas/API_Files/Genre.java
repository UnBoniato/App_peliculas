package com.upm.app_peliculas.API_Files;

import com.google.gson.annotations.SerializedName;

public class Genre{

    // Atributos de un Genero
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    // Getters y Setters
    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }


}
