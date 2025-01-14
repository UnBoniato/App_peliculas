package com.upm.app_peliculas.API_Files;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBApi {

    /* ---------------PELICULAS--------------- */

    // pelis populares
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("language") String language);

    // pelis trending de la semana
    @GET("trending/movie/week")
    Call<MovieResponse> getTrendingMovies(@Query("language") String language);

    // top rated pelis
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("language") String language);

    // pelis que están actualmente en los cines
    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies(@Query("language") String language);

    // pelis que se estrenan pronto
    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(@Query("language") String language);

    // obtiene una peli por su id
    @GET("movie/{movie_id}")
    Call<Movie> getMovie(@Path("movie_id") int movie_id, @Query("language") String language);

    // obtiene los videos de una peli
    @GET("movie/{id}/videos")
    Call<VideosResponse> getMovieVideos(@Path("id") int movieId);


    /*--------------GENEROS---------------*/

    @GET("genre/movie/list")
    Call<GenreResponse> getGenres(@Query("api_key") String apiKey, @Query("language") String language);

    /* ---------------SERIES--------------- */

    /*
    // series populares
    @GET("trending/tv/popular")
    Call<MovieResponse> getPopularSeries(@Query("api_key") String apiKey, @Query("language") String language);

    // series trending de la semana
    @GET("trending/tv/week")
    Call<MovieResponse> getTrendingSeries(@Query("api_key") String apiKey, @Query("language") String language);

    // top rated series
    @GET("trending/tv/top_rated")
    Call<MovieResponse> getTopRatedSeries(@Query("api_key") String apiKey, @Query("language") String language);

    // series en emisión
    @GET("trending/tv/airing_today")
    Call<MovieResponse> getAiringTodaySeries(@Query("api_key") String apiKey, @Query("language") String language);
    */

}
