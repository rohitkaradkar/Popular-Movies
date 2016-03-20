package com.example.rnztx.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.rnztx.popularmovies.modules.Constants;

/**
 * Created by rnztx on 18/3/16.
 */
public class MovieProvider extends ContentProvider {
    private static final UriMatcher mUriMatcher = buildUriMatcher();
    private MovieDbHelper mOpenHelper;
    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = mUriMatcher.match(uri);
        /**
         * http://stackoverflow.com/a/7563037/2804351
         *
         * vnd.android.cursor.dir ....for when we expect cursor to contain 0...n items
         * vnd.android.cursor.item ...for when we expect cursor to contain 1 item
         */
        switch (match){
            case Constants.UriMatchCodes.MOVIES:
                return MovieContract.MovieEntry.CONTENT_DIR_TYPE;
            case Constants.UriMatchCodes.MOVIE_WITH_ID:
                return MovieContract.MovieEntry.CONTENT_ITEM_TYPE;
            case Constants.UriMatchCodes.MOVIE_REVIEW:
                return MovieContract.MovieEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown URI: "+uri);
        }

    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }
    /**
     *Uri Matcher will match Uri with our specified type
     */
    public static UriMatcher buildUriMatcher(){
        // code passed to constructor represents code returned for Root.
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authority = MovieContract.CONTENT_AUTHORITY;
        // any char sequence /*
        // any digit /#
        /**
         * for all favourite movies content://authority/movie/all
         * single  movie content://authority/movie/id /#
         * Optional,
         * single movie review content://authority/id/review /#/review
         */
        uriMatcher.addURI(authority,MovieContract.PATH_MOVIE+"/"+MovieContract.PATH_ALL_MOVIES, Constants.UriMatchCodes.MOVIES);
        uriMatcher.addURI(authority,MovieContract.PATH_MOVIE+"/#", Constants.UriMatchCodes.MOVIE_WITH_ID);
        uriMatcher.addURI(authority,MovieContract.PATH_MOVIE+"/#/"+MovieContract.PATH_REVIEW, Constants.UriMatchCodes.MOVIE_REVIEW);
        return uriMatcher;
    }

}
