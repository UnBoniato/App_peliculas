package com.upm.app_peliculas;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.List;


public class HomeActivity extends AppCompatActivity {

    private final String API_KEY = "0246043f7994bfc1bd073b12fbfb869a";
    private final String LANGUAGE = "es-ES";
    private  TMDBApi apiService;
    private MovieListFragment listFragment;
    private TabLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Crear el servicio para la API
        apiService = RetrofitClient.getRetrofitInstance().create(TMDBApi.class);

        // Crea el fragmento
        FragmentManager fragmentManager = getSupportFragmentManager();
        listFragment = new MovieListFragment();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, listFragment).commit();

        // Asignar acciones a cada apartado del tab
        tab = findViewById(R.id.tabLayout);
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Call<MovieResponse> call = null;
                String tittle = "";

                switch (tab.getPosition()){
                    case 0: // Tendencias
                        call = apiService.getTrendingMovies(API_KEY, LANGUAGE);
                        tittle = "Tendencias de la Semana";
                        break;
                    case 1: // Populares
                        call = apiService.getPopularMovies(API_KEY, LANGUAGE);
                        tittle = "Populares";
                        break;
                    case 2: // En Cines
                        call = apiService.getNowPlayingMovies(API_KEY, LANGUAGE);
                        tittle = "Actualmente en los Cines";
                        break;
                    case 3: // Proximos Estrenos
                        call = apiService.getUpcomingMovies(API_KEY, LANGUAGE);
                        tittle = "Próximos Estrenos";
                        break;
                    case 4: // Mejor Valoradas
                        call = apiService.getTopRatedMovies(API_KEY, LANGUAGE);
                        tittle = "Mejor Valoradas";
                        break;
                }
                downloadMovieInfo(call);
                listFragment.updateListTitle(tittle);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        downloadMovieInfo(apiService.getTrendingMovies(API_KEY, LANGUAGE));

    }// onCreate()

    // Se llama al recibir la respuesta de la API
    public void finishDownload(List<Movie> response){

        listFragment.updateMoviesList(response);
    }

    // Petición a la API
    public void downloadMovieInfo(Call<MovieResponse> call){

        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    finishDownload(response.body().getMovies());

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