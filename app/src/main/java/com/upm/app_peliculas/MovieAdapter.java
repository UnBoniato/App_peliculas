package com.upm.app_peliculas;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.bumptech.glide.Glide;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MovieAdapter extends BaseAdapter {
    private Context context;
    private List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies){
        this.context=context;
        this.movies=movies;

    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_movie_list, parent, false);
        }

        Movie currentMovie = movies.get(position);

        // Enlazar vistas
        ImageView movieImage = convertView.findViewById(R.id.movie_image);
        TextView movieTitle = convertView.findViewById(R.id.movie_title);
        TextView movieGenre = convertView.findViewById(R.id.movie_genre);
        TextView movieOverview = convertView.findViewById(R.id.movie_overview);

        // Configurar contenido
        movieTitle.setText(currentMovie.getTitle());
        movieOverview.setText(currentMovie.getOverview());
        movieGenre.setText("Genre IDs: " + currentMovie.getGenreIds().toString()); // Simplificación

        // Cargar imagen con Glide
        String imageUrl = "https://image.tmdb.org/t/p/w500" + currentMovie.getPosterPath();
        Glide.with(context).load(imageUrl).into(movieImage);

        return convertView;
    }
}
