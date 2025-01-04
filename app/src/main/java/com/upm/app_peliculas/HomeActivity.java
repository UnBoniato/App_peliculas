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

public class HomeActivity extends AppCompatActivity {

    private static final String API_KEY = "0246043f7994bfc1bd073b12fbfb869a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Creación de Retrofit y la petición a la API
        TMDBApi apiService = RetrofitClient.getRetrofitInstance().create(TMDBApi.class);
        Call<MovieResponse> call = apiService.getPopularMovies(API_KEY, "es-ES");

        // Petición a la API
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (MovieResponse.Movie movie : response.body().getMovies()) {
                        Log.d("Movie", "Title: " + movie.getTitle());
                        Log.d("Movie", "Overview: " + movie.getOverview());
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("API Error", t.getMessage());
            }
        });








    }














}