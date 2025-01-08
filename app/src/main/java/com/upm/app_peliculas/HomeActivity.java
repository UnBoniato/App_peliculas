package com.upm.app_peliculas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.upm.app_peliculas.API_Files.GenreManager;
import com.upm.app_peliculas.API_Files.Movie;
import com.upm.app_peliculas.API_Files.MovieResponse;
import com.upm.app_peliculas.API_Files.RetrofitClient;
import com.upm.app_peliculas.API_Files.TMDBApi;

import java.util.List;


public class HomeActivity extends AppCompatActivity implements MovieListFragment.OnMovieSelectedListener {

    private final String API_KEY = "0246043f7994bfc1bd073b12fbfb869a";
    private final String LANGUAGE = "es-ES";
    private TMDBApi apiService;
    private MovieListFragment listFragment;
    private TabLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Crear el servicio para la API
        apiService = RetrofitClient.getRetrofitInstance().create(TMDBApi.class);

        // Crea el fragmento de las listas y reemplazar el contenedor por el fragmento
        FragmentManager fragmentManager = getSupportFragmentManager();
        listFragment = new MovieListFragment();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, listFragment).commit();

        //Recoger todos los generos de las peliculas
        GenreManager.getInstance().loadGenres(apiService, API_KEY);

        // Asignar acciones a cada apartado del tab
        tab = findViewById(R.id.tabLayout);
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Call<MovieResponse> call = null;

                switch (tab.getPosition()){
                    case 0: // Tendencias
                        call = apiService.getTrendingMovies(LANGUAGE);
                        break;
                    case 1: // Populares
                        call = apiService.getPopularMovies(LANGUAGE);
                        break;
                    case 2: // En Cines
                        call = apiService.getNowPlayingMovies(LANGUAGE);
                        break;
                    case 3: // Proximos Estrenos
                        call = apiService.getUpcomingMovies(LANGUAGE);
                        break;
                    case 4: // Mejor Valoradas
                        call = apiService.getTopRatedMovies(LANGUAGE);
                        break;
                }
                obtainMovieListInfo(call);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        obtainMovieListInfo(apiService.getTrendingMovies(LANGUAGE));

        //configuracion del boton de logout
        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(v -> {

            //Aqui se borra el estado de inicio de sesión
            SharedPreferences sharedPreferences =  getSharedPreferences("LoginPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("IsLoggedIn", false);
            editor.apply();

            //Y despues se vuelve a la pantalla de login
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        });

    }// onCreate()

    // Se llama al recibir la lista de peliculas de la API
    public void finishListDownload(List<Movie> response){

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

        /* Si el fragmento actual es de detalles de una peli, se reemplaza por el de la lista
           sino, se actualiza la lista
         */
        if(currentFragment instanceof MovieDetailsFragment){

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, listFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }else{
            listFragment.updateMoviesList(response);
            listFragment.resetListScroll();
        }


    }

    // Petición a la API para la lista de pelis
    public void obtainMovieListInfo(Call<MovieResponse> call){

        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    finishListDownload(response.body().getMovies());

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

    // Se llama cuando se selecciona un item de la lista de peliculas
    @Override
    public void onMovieSelected(int movie_id) {

        // Crear el nuevo fragmento con los detalles de la peli
        MovieDetailsFragment detailsFragment = new MovieDetailsFragment();

        // Llamada a la API para obtener la peli y pasarla al fragmento
        Call<Movie> call = apiService.getMovie(movie_id, LANGUAGE);
        call.enqueue(new Callback<Movie>() {

            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful() && response.body() != null) {

                    // Enviar pelicula al fragmento
                    Movie movie = response.body();
                    detailsFragment.setMovie(movie);

                    // Reemplazar el fragmento actual por el de los detalles de la peli
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainer, detailsFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                } else {
                    Toast.makeText(HomeActivity.this, "Error en la respuesta", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e("API Error", t.getMessage());
            }
        });
        detailsFragment.obtainTrailerKey(movie_id);
    }




}

