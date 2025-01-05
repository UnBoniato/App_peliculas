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

import java.util.List;


public class HomeActivity extends AppCompatActivity {

    private MovieListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Crea el fragmento
        FragmentManager fragmentManager = getSupportFragmentManager();
        //listFragment = (MovieListFragment) fragmentManager.findFragmentById(R.id.fragmentContainer);
        listFragment = new MovieListFragment();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, listFragment).commit();



        // Thread que se encarga de la conexión con la API
        GetMoviesThread thread = new GetMoviesThread(this);
        thread.run();
    }

    // Se llama desde el thread al recibir la respuesta de la API
    public void finishDownload(List<Movie> response){

        listFragment.updateMovies(response);
    }

}