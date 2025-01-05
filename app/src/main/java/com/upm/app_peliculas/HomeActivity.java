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


    private MovieAdapter movieAdapter;
    private ListView movieListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        movieListView = findViewById(R.id.movie_list);

        // Thread que se encarga de la conexión con la API
        GetMoviesThread thread = new GetMoviesThread(this);
        thread.run();
    }

    // Se llama desde el thread al recibir la respuesta de la API
    public void finishDownload(List<Movie> response){

        movieAdapter = new MovieAdapter(HomeActivity.this, response);
        movieListView.setAdapter(movieAdapter);
    }














}