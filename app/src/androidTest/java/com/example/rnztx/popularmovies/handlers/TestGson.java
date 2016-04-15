package com.example.rnztx.popularmovies.handlers;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.rnztx.popularmovies.modules.tmdb.reviews.TmdbReviews;
import com.example.rnztx.popularmovies.modules.tmdb.videos.TmdbVideos;
import com.example.rnztx.popularmovies.modules.utils.Utilities;
import com.google.gson.Gson;

/**
 * Created by rnztx on 14/4/16.
 */
public class TestGson extends AndroidTestCase {
    public static final String LOG_TAG = TestGson.class.getSimpleName();
    public static final String TESTM_MOVIE_ID = "293660";
    public void testTmdbVideosClass(){

        try {
            String rawResult = new HttpHandler(
                    Utilities.buildUrlForMovieVideos(TESTM_MOVIE_ID)
            ).execute();

            Gson gson = new Gson();
            TmdbVideos tmdbVideos = gson.fromJson(rawResult,TmdbVideos.class);

            assertNotNull("Error in videos",tmdbVideos.getId());
            assertNotNull("Error in Video Info",tmdbVideos.getResults());

        }catch (Exception e){
            Log.e(LOG_TAG,e.toString());
        }
    }

    public void testTmdbReviewClass(){
        String rawJsonData;

        try {
            String reviewUrl = Utilities.buildUrlForMovieReviews(TESTM_MOVIE_ID);
            rawJsonData = new HttpHandler(reviewUrl).execute();
            Gson gson = new Gson();
            TmdbReviews reviews = gson.fromJson(rawJsonData,TmdbReviews.class);

            Log.e(LOG_TAG,reviews.getResults().toString());

            assertNotNull("ERROR in Review OBj",reviews.getId());
            assertNotNull("ERROR in Review result OBj",reviews.getResults());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
