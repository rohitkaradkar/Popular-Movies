package com.example.rnztx.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by rnztx on 15/3/16.
 */
public class MovieContract {
    // content authority provide unique path to Content provider on device
    public static final String CONTENT_AUTHORITY = "com.example.rnztx.popularmovies.app";
    // from content authority we create URI for ContentProvider
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies"; // all movies
    public static final String PATH_MOVIE  = "movie";  // single movie followed by ID
    public static final String PATH_REVIEW = "review";

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
