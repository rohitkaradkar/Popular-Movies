package com.example.rnztx.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.rnztx.popularmovies.modules.Constants;

/**
 * Created by rnztx on 18/3/16.
 */
public class MovieProvider extends ContentProvider {
    private static final UriMatcher mUriMatcher = buildUriMatcher();
    private MovieDbHelper mOpenHelper;
    @Override
    public boolean onCreate() {
        // creates database instance
        mOpenHelper = new MovieDbHelper(getContext());
        return true;
    }
    @Override
    public void shutdown() {
        // closes database instance
        super.shutdown();
        mOpenHelper.close();
    }

    /**
     * returns type of URI provided for ContentProvider
     */
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
            case Constants.UriMatchCodes.MOVIE: // for single movie insert
                return MovieContract.MovieEntry.CONTENT_DIR_TYPE;
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
        uriMatcher.addURI(authority,MovieContract.PATH_MOVIE, Constants.UriMatchCodes.MOVIE);//for single movie insert
        uriMatcher.addURI(authority,MovieContract.PATH_MOVIE+"/"+MovieContract.PATH_ALL_MOVIES, Constants.UriMatchCodes.MOVIES);
        uriMatcher.addURI(authority,MovieContract.PATH_MOVIE+"/#", Constants.UriMatchCodes.MOVIE_WITH_ID);
        uriMatcher.addURI(authority,MovieContract.PATH_MOVIE+"/#/"+MovieContract.PATH_REVIEW, Constants.UriMatchCodes.MOVIE_REVIEW);
        return uriMatcher;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = mUriMatcher.match(uri);
        Uri returnUri;
        switch (match){
            case Constants.UriMatchCodes.MOVIE:{
                long _id = db.insert(MovieContract.MovieEntry.TABLE_NAME,null,values);
                if (_id > 0)
                    returnUri = MovieContract.MovieEntry.buildMovieWithIdUri(_id);

                else
                    throw new android.database.SQLException("Failed to insert row into "+uri);
            }
            break;
            default:
                throw new UnsupportedOperationException("Unknow uri got: "+match+" for"+uri);
        }
        try {
            getContext().getContentResolver().notifyChange(uri,null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnUri;
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

}
