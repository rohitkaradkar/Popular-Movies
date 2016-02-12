package com.example.rnztx.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.rnztx.popularmovies.modules.HttpHandler;

public class MainActivity extends AppCompatActivity {
    final String LOG_TAG = MainActivity.class.getSimpleName();

    // Constants for URI builder
    final static String URL_SCHEME = "http";
    final static String BASE_URL_API = "api.themoviedb.org";
    final static String BASE_PATH_DISCOVER1 = "3";
    final static String BASE_PATH_DISCOVER2 = "discover";
    final static String BASE_PATH_DISCOVER3 = "movie";
    final static String KEY_SORT = "sort_by";
    final static String KEY_API = "api_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainActivity_container,new MainFragment())
                    .commit();
        }
//        new FetchMovieData().execute();
//
//        // only for debugging
//        JsonHandler fetchMovies = new JsonHandler();
//
//        ArrayList<MovieInfo> movieInfos = fetchMovies.parseData();
//        for (MovieInfo movie: movieInfos){
//            Log.e(LOG_TAG,movie.toString());
//        }

    }

    private String testFetchData(){
        final String VAL_SORT ="popularity.desc";
        final String VAL_API_KEY = BuildConfig.TMDB_API_KEY;

        Uri.Builder movieUriBuilder = new Uri.Builder();
        movieUriBuilder.scheme(URL_SCHEME)
                .authority(BASE_URL_API)
                .appendPath(BASE_PATH_DISCOVER1)
                .appendPath(BASE_PATH_DISCOVER2)
                .appendPath(BASE_PATH_DISCOVER3)
                .appendQueryParameter(KEY_SORT,VAL_SORT)
                .appendQueryParameter(KEY_API,VAL_API_KEY);

        Log.e(LOG_TAG,movieUriBuilder.toString());
        HttpHandler movieDbHandler = new HttpHandler(movieUriBuilder.build().toString());
//        Log.e(LOG_TAG,movieDbHandler.fetchData());
        return movieDbHandler.fetchData();
    }

    public class FetchMovieData extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            String rawData = testFetchData();
            Log.e(LOG_TAG,rawData);
            return null;
        }
    }
}
