package com.example.rnztx.popularmovies.data;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Created by rnztx on 20/3/16.
 */
public class TestProvider extends AndroidTestCase {
    private static final String LOG_TAG = TestProvider.class.getSimpleName();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecordsOfDB();
    }

    /**
     * For fresh start... Delete All records from database
     */
    public void deleteAllRecordsOfDB(){
        SQLiteDatabase db = new MovieDbHelper(mContext).getWritableDatabase();
        db.delete(MovieContract.MovieEntry.TABLE_NAME,null,null);
        db.close();
    }

    /**
     * to make sure ContentProvider is registered Properly
     */
    public void testIsProvidersRegistered(){
        PackageManager pm  = mContext.getPackageManager();
        ComponentName componentName = new ComponentName(mContext.getPackageName(),MovieProvider.class.getName());
        try {
            //this throws an Exception if provider is not registered
            ProviderInfo providerInfo = pm.getProviderInfo(componentName,0);
            // verify that registered authority matches from contract
            assertEquals("ERROR: authority didn't mach. Found "+providerInfo.authority+" instead of "+MovieContract.CONTENT_AUTHORITY,
                    providerInfo.authority,MovieContract.CONTENT_AUTHORITY);
        }
        catch (Exception e){
            Log.e(LOG_TAG, e.toString());
            //Exception is thrown that means .. MovieProvider is not registered.
            assertTrue("ERROR: MovieProvider is not registered @: "+mContext.getPackageName(),
                    false);
        }
    }

    /**
     * this doesn't touch the database.
     * verifies that ContentProvider is returning Correct type for each URI
     */

    public void testContentType(){
        //type check main Uri

        // type check for all Movies
        String allMovieType = mContext.getContentResolver().getType(MovieContract.MovieEntry.buildAllMoviesUri());
        assertEquals("ERROR: invalid movie content type",MovieContract.MovieEntry.CONTENT_DIR_TYPE,allMovieType);
//        Log.e(LOG_TAG,allMovieType+"\n"+MovieContract.MovieEntry.CONTENT_DIR_TYPE);

        // type check for single movie
        long fakeMovieId = 25643;
        String singleMovieType = mContext.getContentResolver().getType(MovieContract.MovieEntry.buildMovieWithIdUri(fakeMovieId));
        assertEquals("ERROR: invalid single movie type",singleMovieType, MovieContract.MovieEntry.CONTENT_ITEM_TYPE);
//        Log.e(LOG_TAG,singleMovieType);

        // type check for single movie review
        String singleMovieReviewType = mContext.getContentResolver().getType(MovieContract.MovieEntry.buildMovieWithIdForReviewUri(fakeMovieId));
        assertEquals("ERROR: invalid single movie review type",singleMovieReviewType, MovieContract.MovieEntry.CONTENT_ITEM_TYPE);
//        Log.e(LOG_TAG,singleMovieReviewType);
    }
    /**
     * test insert & update & query operations
     */
    public void testInsertUpdateMovie(){
        long movId = 1992;

        ContentValues dummyValues = TestUtils.getDummyMovieValue(movId);
        Uri movieUri = mContext.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI,dummyValues);
        long rowId = ContentUris.parseId(movieUri);
        assertTrue("ERROR: failed to insert",rowId!=-1);

        ContentValues updatedValues = new ContentValues(dummyValues);
        updatedValues.put(MovieContract.MovieEntry._ID,movId);
        updatedValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH,"new/path/poster.png");

        int rowsUpdated = mContext.getContentResolver().update(MovieContract.MovieEntry.CONTENT_URI,
                                                                updatedValues,
                                                                MovieContract.MovieEntry._ID+"= ?",
                                                                new String[]{Long.toString(movId)});
        assertEquals("ERROR: failed to update ",1,rowsUpdated);

        Cursor cursor = mContext.getContentResolver().query(MovieContract.MovieEntry.buildMovieWithIdUri(movId),
                null,
                MovieContract.MovieEntry._ID + " = "+movId,null,null);

        cursor.moveToFirst();
        String colName[] = cursor.getColumnNames();
        for (int i=0;i<colName.length;i++){
            Log.e("ROW ",colName[i]+": "+cursor.getString(i));
        }
        cursor.close();
    }

}
