package com.upm.app_peliculas;

import static android.provider.MediaStore.Video.VideoColumns.LANGUAGE;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreManager {

    private final String API_KEY = "0246043f7994bfc1bd073b12fbfb869a";
    private final String LANGUAGE = "es-ES";
    private static GenreManager instance;
    private Map<Integer, String> genreMap = new HashMap<>();

    public static GenreManager getInstance() {
        if (instance == null) {
            instance = new GenreManager();
        }
        return instance;
    }

    public void loadGenres(TMDBApi apiService, String apiKey) {
        apiService.getGenres(API_KEY, LANGUAGE).enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Genre genre : response.body().getGenres()) {
                        genreMap.put(genre.getId(), genre.getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {
                Log.e("GenreManager", "Failed to load genres: " + t.getMessage());
            }
        });
    }

    public String getGenreName(int id) {
        return genreMap.getOrDefault(id, "Unknown");
    }
}
