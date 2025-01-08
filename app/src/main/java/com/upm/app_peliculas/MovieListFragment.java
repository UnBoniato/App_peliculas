package com.upm.app_peliculas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MovieListFragment extends Fragment {

    private ListView movieListView;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList = new ArrayList<>();


    public MovieListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);

        // Asignar el ListView
        movieListView = rootView.findViewById(R.id.movie_list);

        // Crear el adaptador y asignarlo al ListView
        movieAdapter = new MovieAdapter(getContext(), movieList);
        movieListView.setAdapter(movieAdapter);

        // Configurar el listener de clicks
        movieListView.setOnItemClickListener((parent, view, position, id) -> {
            Movie selectedMovie = movieList.get(position);
            onMovieSelected(selectedMovie.getMovie_id());
        });

        return rootView;
    }

    public void resetListScroll(){
        movieAdapter.notifyDataSetChanged();
        movieListView.setSelection(0);
    }

    // Actualizar la lista de pelis
    public void updateMoviesList(List<Movie> newMovies) {
        movieList.clear();
        movieList.addAll(newMovies);
        movieAdapter.notifyDataSetChanged(); // Notifica que los datos han cambiado
    }

    private void onMovieSelected(int movie_id) {
        if (getActivity() instanceof OnMovieSelectedListener) {
            ((OnMovieSelectedListener) getActivity()).onMovieSelected(movie_id);
        }
    }

    public interface OnMovieSelectedListener {
        void onMovieSelected(int movie_id);
    }
}