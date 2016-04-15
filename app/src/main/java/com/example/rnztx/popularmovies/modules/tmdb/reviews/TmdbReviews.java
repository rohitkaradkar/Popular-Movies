package com.example.rnztx.popularmovies.modules.tmdb.reviews;

import java.util.List;

/**
 * Created by rnztx on 15/4/16.
 */
public class TmdbReviews {
    private String id ;
    private List<ReviewResult> results;

    public String getId() {
        return id;
    }

    public List<ReviewResult> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "TmdbReviews{\n" +
                "id='" + id + '\'' +
                ",\nresults=" + results +
                '}';
    }
}
