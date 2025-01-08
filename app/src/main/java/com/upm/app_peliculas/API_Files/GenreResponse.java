package com.upm.app_peliculas.API_Files;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenreResponse {

    @SerializedName("genres")
    private List<Genre> genres;

    // Getters y Setters
    public List<Genre> getGenres() {
        return genres;
    }
}
