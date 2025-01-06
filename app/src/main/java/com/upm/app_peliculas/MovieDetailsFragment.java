package com.upm.app_peliculas;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;


public class MovieDetailsFragment extends Fragment {

    private Movie selectedMovie;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    public void setMovie(Movie movie) {
        this.selectedMovie = movie;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        // Buscar elementos del layout
        ImageView posterView = rootView.findViewById(R.id.movie_poster);
        TextView titleView = rootView.findViewById(R.id.movie_title);
        TextView genresView = rootView.findViewById(R.id.movie_genres);
        TextView releaseDateView = rootView.findViewById(R.id.movie_release_date);
        TextView durationView = rootView.findViewById(R.id.movie_duration);
        RatingBar ratingBar = rootView.findViewById(R.id.movie_rating_bar);
        TextView summaryView = rootView.findViewById(R.id.movie_summary);
        TextView revenueView = rootView.findViewById(R.id.movie_revenue);
        TextView budgetView = rootView.findViewById(R.id.movie_budget);
        VideoView trailerButton = rootView.findViewById(R.id.movie_trailer);
        ScrollView scrollView = rootView.findViewById(R.id.scroll_view);

        // Mostrar los datos de la película en el layout
        if (selectedMovie != null) {
            // Mostrar datos en los widgets
            titleView.setText(selectedMovie.getTitle());
            //genresView.setText(selectedMovie.getGenres());
            releaseDateView.setText(selectedMovie.getReleaseDate());
            durationView.setText(selectedMovie.getDuration());
            ratingBar.setRating(((float) selectedMovie.getVoteAverage())/2);
            summaryView.setText(selectedMovie.getOverview());
            revenueView.setText("$" + Integer.toString(selectedMovie.getRevenue()));
            budgetView.setText("$" + Integer.toString(selectedMovie.getBudget()));

            // Cargar imagen del poster
            String imageUrl = "https://image.tmdb.org/t/p/w500" + selectedMovie.getPosterPath();
            Glide.with(getContext()).load(imageUrl).into(posterView);

            scrollView.fullScroll(ScrollView.FOCUS_UP);

        }

        return rootView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        Button shareButton = view.findViewById(R.id.share_button);
        shareButton.setOnClickListener(v -> {
            if (selectedMovie != null) {
                shareMovieInfo();
            }
        });
    }
    private void shareMovieInfo(){

        String shareText = "¡Echale un vistazo a esta película!\n\n" +
                "Título: " + selectedMovie.getTitle() + "\n" +
                "Resumen: " + selectedMovie.getOverview() + "\n";

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

        startActivity(Intent.createChooser(shareIntent, "Compartir película"));
    }



}