package com.upm.app_peliculas;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetailsFragment extends Fragment {

    private Movie selectedMovie;
    private String trailerKey;
    private boolean isTrailerPlaying = false;

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
        YouTubePlayerView youTubePlayerView = rootView.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        // Crear el Listener para que cuando el trailer esté listo y se pulse en él, se reproduzca
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                // Prepara el video pero no lo reproduce
                youTubePlayer.cueVideo(trailerKey, 0);

                // Configurar click para reproducir el video
                youTubePlayerView.setOnClickListener(v -> {
                    if (!isTrailerPlaying) {
                        youTubePlayer.loadVideo(trailerKey, 0); // Inicia la reproducción
                        isTrailerPlaying = true;
                    }
                });
            }
        });

        //Crear la cadena con los generos
            List<Genre> genreList = selectedMovie.getGenre();

            StringBuilder genres = new StringBuilder();
            for(Genre genre : genreList){
                String genreName = GenreManager.getInstance().getGenreName(genre.getId());
                genres.append(genreName).append(", ");
            }

            if (genres.length() > 0) {
                genres.setLength(genres.length() - 2); // Eliminar la última coma
            }

        // Mostrar los datos de la película en el layout
        if (selectedMovie != null) {
            // Mostrar datos en los widgets
            titleView.setText(selectedMovie.getTitle());
            genresView.setText(genres.toString());
            releaseDateView.setText(selectedMovie.getReleaseDate());
            durationView.setText(selectedMovie.getDuration());
            ratingBar.setRating(((float) selectedMovie.getVoteAverage())/2);
            summaryView.setText(selectedMovie.getOverview());
            revenueView.setText("$" + Integer.toString(selectedMovie.getRevenue()));
            budgetView.setText("$" + Integer.toString(selectedMovie.getBudget()));

            // Cargar imagen del poster
            String imageUrl = "https://image.tmdb.org/t/p/w500" + selectedMovie.getPosterPath();
            Glide.with(getContext()).load(imageUrl).into(posterView);

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

    // Llamada a la API para obtener la clave del trailer de la peli a raiz de su id
    public void obtainTrailerKey(int movieId) {
        TMDBApi movieApi = RetrofitClient.getRetrofitInstance().create(TMDBApi.class);
        Call<VideosResponse> call = movieApi.getMovieVideos(movieId);

        call.enqueue(new Callback<VideosResponse>() {
            @Override
            public void onResponse(Call<VideosResponse> call, Response<VideosResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Video> videos = response.body().getResults();
                    for (Video video : videos) {
                        if ("Trailer".equalsIgnoreCase(video.getType()) && "YouTube".equalsIgnoreCase(video.getSite())) {
                            trailerKey=video.getKey();
                            break;
                        }
                    }
                } else {
                    Log.e("Trailer", "No se encontró un tráiler válido");
                }
            }

            @Override
            public void onFailure(Call<VideosResponse> call, Throwable t) {
                Log.e("Trailer", "Error al obtener el tráiler: " + t.getMessage());
            }
        });
    }




}