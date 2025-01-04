package com.upm.app_peliculas;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class HomeActivity extends AppCompatActivity {

    private static final String API_KEY = "0246043f7994bfc1bd073b12fbfb869a";
    private MovieAdapter movieAdapter;
    private ListView movieListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        movieListView = findViewById(R.id.movie_list);


        // Creación de Retrofit y la petición a la API
        TMDBApi apiService = RetrofitClient.getRetrofitInstance().create(TMDBApi.class);
        Call<MovieResponse> call = apiService.getPopularMovies(API_KEY, "es-ES");

        // Petición a la API
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








    }














}