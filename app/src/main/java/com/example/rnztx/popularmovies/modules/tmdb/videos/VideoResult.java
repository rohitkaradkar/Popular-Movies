package com.example.rnztx.popularmovies.modules.tmdb.videos;

/**
 * Created by rnztx on 15/4/16.
 */
public class VideoResult {
    private String key;
    private String id;

    public String getKey() {
        return key;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "VideoResult{" +
                "key='" + key + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
