package com.example.rnztx.popularmovies.data;

import android.content.ContentValues;
import android.test.AndroidTestCase;

import com.example.rnztx.popularmovies.data.MovieContract.MovieEntry;

/**
 * Created by rnztx on 16/3/16.
 */
public class TestUtils extends AndroidTestCase {
    public static ContentValues getDummyMovieValue(long id){
        ContentValues values = new ContentValues();
        values.put(MovieEntry._ID,id);
        values.put(MovieEntry.COLUMN_TITLE,"nightmare of Gradle");
        values.put(MovieEntry.COLUMN_RELEASE_DATE,"20-12-1992");
        values.put(MovieEntry.COLUMN_POSTER_PATH,"path/to/poster.img");
        values.put(MovieEntry.COLUMN_VOTE_AVERAGE,8.5);
        values.put(MovieEntry.COLUMN_OVERVIEW,"THis is supercool Movie");
        return values;
    }

}
