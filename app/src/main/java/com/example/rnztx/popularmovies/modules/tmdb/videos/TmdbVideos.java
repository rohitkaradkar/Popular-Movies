package com.example.rnztx.popularmovies.modules.tmdb.videos;

import java.util.List;

/**
 * Created by rnztx on 15/4/16.
 */
public class TmdbVideos {
    private String id;
    private List<VideoResult> results;

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "TmdbVideos{" +
                "id='" + id + '\'' +
                ", results=" + results +
                '}';
    }

    public List<VideoResult> getResults() {
        return results;
    }
}
