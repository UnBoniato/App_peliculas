package com.upm.app_peliculas;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieResponse {

    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }


    // Clase interna que representa una película
    public static class Movie {

        // Atributos de una pelicula
        @SerializedName("title")
        private String title;

        @SerializedName("overview")
        private String overview;

        @SerializedName("poster_path")
        private String posterPath;

        @SerializedName("release_date")
        private String releaseDate;

        @SerializedName("vote_average")
        private double voteAverage;

        @SerializedName("genres")
        private List<Genre> genres;

        // Getters y Setters
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getPosterPath() {
            return posterPath;
        }

        public void setPosterPath(String posterPath) {
            this.posterPath = posterPath;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public double getVoteAverage() {
            return voteAverage;
        }

        public void setVoteAverage(double voteAverage) {
            this.voteAverage = voteAverage;
        }

        public List<String> getGenres() {
            List<String> genreNames = new ArrayList<>();
            if (genres != null) {
                for (Genre genre : genres) {
                    genreNames.add(genre.getName());
                }
            }
            return genreNames;
        }

        public void setGenres(List<Genre> genres) {
            this.genres = genres;
        }
    }

    public static class Genre{

        // Atributos de un Genero
        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        // Getters y Setters
        public int getId(){
            return id;
        }

        public void setId(int id){
            this.id = id;
        }

        public String getName(){
            return name;
        }

        public void setName(String name){
            this.name = name;
        }

    }
}
