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
    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public boolean onCreate() {
        return false;
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
        // any char String /*
        // any digit /#
        /**
         * for all favourite movies content://authority/movies
         * single  movie content://authority/movie/id /#
         * single movie review content://authority/id/review /#/review
         */
        uriMatcher.addURI(authority,MovieContract.PATH_MOVIES, Constants.UriMatchCodes.MOVIES);
        uriMatcher.addURI(authority,MovieContract.PATH_MOVIE+"/#", Constants.UriMatchCodes.MOVIE_WITH_ID);
        uriMatcher.addURI(authority,MovieContract.PATH_MOVIE+"/#"+MovieContract.PATH_REVIEW, Constants.UriMatchCodes.MOVIE_REVIEW);
        return uriMatcher;
    }

}
