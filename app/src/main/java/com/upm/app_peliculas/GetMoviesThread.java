package com.upm.app_peliculas;

import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetMoviesThread implements Runnable {

    private final String API_KEY = "0246043f7994bfc1bd073b12fbfb869a";
    private final HomeActivity activity;

    public GetMoviesThread(HomeActivity activity){
        this.activity = activity;
    }

    @Override
    public void run(){

        // Creación de Retrofit y la petición a la API
        TMDBApi apiService = RetrofitClient.getRetrofitInstance().create(TMDBApi.class);
        Call<MovieResponse> call = apiService.getPopularMovies(API_KEY, "es-ES");

        // Petición a la API
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    activity.finishDownload(response.body().getMovies());
                } else {
                    Toast.makeText(activity, "Error en la respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("API Error", t.getMessage());
            }
        });
    }


    // Creación de Retrofit y la petición a la API
        /*
        TMDBApi apiService = RetrofitClient.getRetrofitInstance().create(TMDBApi.class);
        Call<MovieResponse> call = apiService.getPopularMovies(API_KEY, "es-ES");
         */

    // Petición a la API
        /*
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getMovies();
                    Log.d("API Response", "Películas encontradas: " + movies.size());

                    movieAdapter = new MovieAdapter(HomeActivity.this, movies);
                    movieListView.setAdapter(movieAdapter);
                } else {
                    Toast.makeText(HomeActivity.this, "Error en la respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("API Error", t.getMessage());
            }
        });
        */
}
