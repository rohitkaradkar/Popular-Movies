package com.example.rnztx.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.rnztx.popularmovies.data.MovieContract.*;

/**
 * Created by rnztx on 15/3/16.
 */
public class MovieDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 2;
    public MovieDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // database is created if it is not present
        final String SQL_CREATE_FAVOURITE_MOVIE_TABLE = "CREATE TABLE "+ FavouriteMovieEntry.TABLE_NAME+" ("+
                FavouriteMovieEntry._ID + " INTEGER PRIMARY KEY, "+
                FavouriteMovieEntry.COLUMN_TITLE + " TEXT NOT NULL, "+
                FavouriteMovieEntry.COLUMN_RELEASE_DATE+" TEXT NOT NULL, "+
                FavouriteMovieEntry.COLUMN_POSTER_PATH+" TEXT NOT NULL, "+
                FavouriteMovieEntry.COLUMN_VOTE_AVERAGE+" REAL NOT NULL, "+
                FavouriteMovieEntry.COLUMN_OVERVIEW+" TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_FAVOURITE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // when database version is upgraded
    }
}
