package com.upm.app_peliculas;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    // Atributos de una pelicula
    @SerializedName("title")
    private String title;
    /*
    @SerializedName("overview")
    private String overview;
    */
    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("genre_ids")
    private List<Integer> genreIds;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("vote_average")
    private double voteAverage;

    // Getters y Setters
    public String getTitle() {
        return title;
    }

    /*
    public String getOverview() {
        return overview;
    }
    */
    public String getPosterPath() {
        return posterPath;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }
    public String getReleaseDate() {
        return releaseDate;
    }


    public double getVoteAverage() {
        return voteAverage;
    }

}
