package com.upm.app_peliculas;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String BEARER_TOKEN =
            "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwMjQ2MDQzZjc5OTRiZ" +
                    "mMxYmQwNzNiMTJmYmZiODY5YSIsIm5iZiI6MTczNTk3O" +
                    "DE2Mi4yOTMsInN1YiI6IjY3NzhlY2IyMTU1MjFmODNkOT" +
                    "Y2YmQwYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJz" +
                    "aW9uIjoxfQ.0tOiJcpo3XMKOiffasiCSYsnmr4XZCp7ttrHVAqG0Mo";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {

            // Crear un cliente HTTP que añade el encabezado del Bearer Token
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        return chain.proceed(chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer " + BEARER_TOKEN)
                                .build());
                    })
                    .build();

            // Crear la instancia de Retrofit con el cliente configurado
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

}
