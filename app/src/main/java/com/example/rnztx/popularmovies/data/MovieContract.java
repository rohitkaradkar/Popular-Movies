package com.example.rnztx.popularmovies.data;

import android.content.ContentResolver;
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

    public static final String PATH_ALL_MOVIES = "all";
    public static final String PATH_MOVIE  = "movie";  // single movie followed by ID
    public static final String PATH_REVIEW = "review";

    public static final class MovieEntry implements BaseColumns{
        // return all movies
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/"+CONTENT_AUTHORITY + "/"+ PATH_MOVIE+"/"+PATH_ALL_MOVIES ;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"+CONTENT_AUTHORITY + "/"+ PATH_MOVIE;

        public static final String TABLE_NAME = "favouriteMovies";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_OVERVIEW = "overview";

        // Create Uri for all needs
        public static Uri buildAllMoviesUri(){
            return CONTENT_URI.buildUpon().
                    appendPath(PATH_ALL_MOVIES)
                    .build();
        }
        public static Uri buildMovieWithIdUri(long id){
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .build();
        }
        public static Uri buildMovieWithIdForReviewUri(long id){
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(id))
                    .appendPath(PATH_REVIEW)
                    .build();
        }

    }
}
