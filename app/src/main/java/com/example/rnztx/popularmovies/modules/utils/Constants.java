package com.example.rnztx.popularmovies.modules.utils;

/**
 * Created by rnztx on 18/3/16.
 */
public final class Constants {
    public static final String ARG_MOVIE_DETAIL = "movie_detail";
    public static final String POPULAR_MOVIES_DIR = "PopularMovies";

    public static final class UriMatchCodes{
        // single movie insert
        public static final int MOVIE = 19;
        // fetch all movie
        public static final int MOVIES = 20;
        // fetch movie with id
        public static final int MOVIE_WITH_ID = 21;
        // fetch movie review
        public static final int MOVIE_REVIEW = 22;
    }
    public static final class ColIndices{
        public static final int MOV_ID = 0;
        public static final int MOV_TITLE = 1;
        public static final int MOV_RELEASE_DATE = 2;
        public static final int MOV_POSTER_PATH = 3;
        public static final int MOV_VOTE = 4;
        public static final int MOV_OVERVIEW = 5;
    }
}
