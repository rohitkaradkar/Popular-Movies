package com.example.rnztx.popularmovies.data;

import android.provider.BaseColumns;

/**
 * Created by rnztx on 15/3/16.
 */
public class MovieContract {
    public static final class FavouriteMovieEntry implements BaseColumns{

        //table name
        public static final String TABLE_NAME = "favouriteMovies";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_OVERVIEW = "overview";
    }
}
