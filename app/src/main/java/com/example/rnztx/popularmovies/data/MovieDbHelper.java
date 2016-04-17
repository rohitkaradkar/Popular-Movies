package com.example.rnztx.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.example.rnztx.popularmovies.data.MovieContract.MovieEntry;
import com.example.rnztx.popularmovies.modules.utils.Constants;

import java.io.File;

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
        final String SQL_CREATE_FAVOURITE_MOVIE_TABLE = "CREATE TABLE "+ MovieEntry.TABLE_NAME+" ("+
                MovieEntry._ID + " INTEGER PRIMARY KEY, "+  //0
                MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, "+//1
                MovieEntry.COLUMN_RELEASE_DATE+" TEXT NOT NULL, "+//2
                MovieEntry.COLUMN_POSTER_PATH+" TEXT NOT NULL, "+//3
                MovieEntry.COLUMN_VOTE_AVERAGE+" REAL NOT NULL, "+//4
                MovieEntry.COLUMN_OVERVIEW+" TEXT NOT NULL);";//5

        db.execSQL(SQL_CREATE_FAVOURITE_MOVIE_TABLE);

        // create Dir for Image Storage
        File imageStorageDir = new File(Environment.getExternalStorageDirectory()+"/"+ Constants.POPULAR_MOVIES_DIR);
        if (!imageStorageDir.exists())
            imageStorageDir.mkdir();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // when database version is upgraded
    }
}
