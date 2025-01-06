package com.upm.app_peliculas;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    // Atributos de una pelicula
    @SerializedName("id")
    private int movie_id;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("title")
    private String title;

    @SerializedName("genre_ids")
    private List<Integer> genreIds;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("runtime")
    private int runtime;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("overview")
    private String overview;

    @SerializedName("revenue")
    private int revenue;

    @SerializedName("budget")
    private int budget;


    // Getters y Setters
    public int getMovie_id() {
        return movie_id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getDuration() {
        return getFormattedDuration();
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getBudget() {
        return budget;
    }

    //formatear el runtime en horas y minutos
    public String getFormattedDuration() {
        int hours = runtime / 60;
        int minutes = runtime % 60;
        return hours + "h " + minutes + "m";
    }
}
