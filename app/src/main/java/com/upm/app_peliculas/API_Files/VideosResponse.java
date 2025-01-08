package com.upm.app_peliculas.API_Files;

import java.util.List;

public class VideosResponse {

    private List<Video> results;

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }
}
