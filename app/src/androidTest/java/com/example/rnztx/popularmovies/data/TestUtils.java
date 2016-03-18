package com.example.rnztx.popularmovies.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

import com.example.rnztx.popularmovies.data.MovieContract.*;

import java.util.Map;
import java.util.Set;

/**
 * Created by rnztx on 16/3/16.
 */
public class TestUtils extends AndroidTestCase {
    public static ContentValues getDummyMovieValue(){
        ContentValues values = new ContentValues();
        values.put(MovieEntry._ID,1111);
        values.put(MovieEntry.COLUMN_TITLE,"Rohit Karadkar");
        values.put(MovieEntry.COLUMN_RELEASE_DATE,"20-12-1992");
        values.put(MovieEntry.COLUMN_POSTER_PATH,"path/to/poster.img");
        values.put(MovieEntry.COLUMN_VOTE_AVERAGE,8.5);
        values.put(MovieEntry.COLUMN_OVERVIEW,"THis is supercool Movie");
        return values;
    }
    // this code snippet is taken from Udacity's Sunshine code repository
    public static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

}
