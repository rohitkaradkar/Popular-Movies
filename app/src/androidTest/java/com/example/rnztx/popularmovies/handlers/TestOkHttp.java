package com.example.rnztx.popularmovies.handlers;

import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Created by rnztx on 14/4/16.
 */
public class TestOkHttp extends AndroidTestCase{
    private static final String LOG_TAG = TestOkHttp.class.getSimpleName();
    public static final String TEST_URL = "https://raw.githubusercontent.com/RnzTx/Popular-Movies/master/readme.md";
    public void testOkHttpHandler(){
        String responce = null;
        HttpHandler handler = new HttpHandler(TEST_URL);
        try {
            responce = handler.execute();
            Log.e(LOG_TAG,responce);
        }catch (Exception e){
            Log.e(LOG_TAG,e.toString());
        }
        assertNotNull("Failed to Fetch Data",responce);
    }
}
