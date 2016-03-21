package com.example.rnztx.popularmovies.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.example.rnztx.popularmovies.data.MovieContract.MovieEntry;

import java.util.HashSet;

/**
 * Created by rnztx on 16/3/16.
 */
public class TestDatabase extends AndroidTestCase {
    final static String LOG_TAG = TestDatabase.class.getSimpleName();
    // we want each test to run on fresh database
    private void deleteDatabase(){
        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);
    }

    public void setUp(){
        // this setup is performed each time before executing TestCases
        deleteDatabase();
    }

    public void testCreateDb(){
        // make Set of our tables
        HashSet<String> myTableSet = new HashSet<>();
        myTableSet.add(MovieEntry.TABLE_NAME);

        // get Writable database
        SQLiteDatabase db = new MovieDbHelper(this.mContext).getWritableDatabase();

        // check if database is open
        assertTrue("Error: Database in not Open",db.isOpen());

        // have created required Table !
        Cursor cursor = db.rawQuery("SELECT name from sqlite_master WHERE type='table'",null);
        assertTrue("Error: this means database is not created correctly",cursor.moveToFirst());

        // verify our tablename exists
        do{
            myTableSet.remove(cursor.getString(0));
        }while (cursor.moveToNext());

        assertTrue("Error: table name doesn't exists",myTableSet.isEmpty());

        // get table structure
        cursor = db.rawQuery("PRAGMA table_info("+ MovieEntry.TABLE_NAME+")",null);
        //query database tablename
        assertTrue("ERROR: error getting table info",cursor.moveToFirst());

        // build set columnames to verify
        HashSet<String> myColumnNameSet = new HashSet<>();
        myColumnNameSet.add(MovieEntry._ID);
        myColumnNameSet.add(MovieEntry.COLUMN_TITLE);
        myColumnNameSet.add(MovieEntry.COLUMN_RELEASE_DATE);
        myColumnNameSet.add(MovieEntry.COLUMN_POSTER_PATH);
        myColumnNameSet.add(MovieEntry.COLUMN_VOTE_AVERAGE);
        myColumnNameSet.add(MovieEntry.COLUMN_OVERVIEW);

        // now verify all reqired Column's are present
        int columnIndex = cursor.getColumnIndex("name");
        do {
            String columnName = cursor.getString(columnIndex);
//            Log.e(LOG_TAG,cursor.getString(columnIndex+1));
            myColumnNameSet.remove(columnName);
        }while (cursor.moveToNext());

        assertTrue("Error: Table doesn't contain all required column's",myColumnNameSet.isEmpty());
        cursor.close();
        db.close();
    }

    public void testInsertDb(){
        SQLiteDatabase db = new MovieDbHelper(this.mContext).getWritableDatabase();
        // get dummy data
        ContentValues values = TestUtils.getDummyMovieValue(125);

        // insert data & get row id
        long rowId = db.insert(MovieEntry.TABLE_NAME,null,values);
//        Log.e(LOG_TAG,"row id: "+rowId);
        assertTrue("ERRRR: unable to insert data",rowId!=-1);

        // Query database in Cursor
        Cursor cursor = db.query(MovieEntry.TABLE_NAME,null,null,null,null,null,null);

        // verify that we got valid database row
        assertTrue("ERRR: no records found",cursor.moveToFirst());

        // check cursor values with our content values
        TestUtils.validateCurrentRecord("ERRR: invalid data inserted",cursor,values);
    }

}
