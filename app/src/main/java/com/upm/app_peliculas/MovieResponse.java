package com.upm.app_peliculas;

import com.google.gson.annotations.SerializedName;
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

    /* Más métodos para obtener los datos de la peli*/

    // Clase interna que representa una película
    public static class Movie {

        // Datos de una pelicula
        @SerializedName("title")
        private String title;

        @SerializedName("overview")
        private String overview;

        @SerializedName("poster_path")
        private String posterPath;

        @SerializedName("genres")
        private String[] genres;

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

        public String getGenres() {
            return posterPath;
        }
    }
}
