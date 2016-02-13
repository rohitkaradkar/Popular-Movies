package com.example.rnztx.popularmovies;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.rnztx.popularmovies.modules.AdapterMovieInfo;
import com.example.rnztx.popularmovies.handlers.HttpHandler;
import com.example.rnztx.popularmovies.handlers.JsonHandler;
import com.example.rnztx.popularmovies.modules.MovieInfo;
import com.example.rnztx.popularmovies.movieDetails.DetailActivity;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private AdapterMovieInfo mAdapterMovieInfo;
    private final String LOG_TAG = MainFragment.class.getSimpleName();
    // Constants for URI builder
    final static String URL_SCHEME = "http";
    final static String BASE_URL_API = "api.themoviedb.org";
    final static String BASE_PATH_DISCOVER1 = "3";
    final static String BASE_PATH_DISCOVER2 = "discover";
    final static String BASE_PATH_DISCOVER3 = "movie";
    final static String KEY_SORT = "sort_by";
    final static String KEY_API = "api_key";

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        // No parameter constructor returns dummy data
        ArrayList<MovieInfo> dummyData = new JsonHandler().parseData();
        mAdapterMovieInfo = new AdapterMovieInfo(getActivity(),dummyData);

        // GridView To display on Main Activity
        GridView gridView = (GridView) rootView.findViewById(R.id.movie_gridView);
        gridView.setAdapter(mAdapterMovieInfo);

        //Fetch Data & update adapter
        new MovieTask().execute();

        // attaching click listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);

                startActivity(intent);
            }
        });

        return rootView;
    }

    private String fetchJsonData(){
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

//        Log.e(LOG_TAG,movieUriBuilder.toString());
        HttpHandler movieDbHandler = new HttpHandler(movieUriBuilder.build().toString());
//        Log.e(LOG_TAG,movieDbHandler.fetchData());
        return movieDbHandler.fetchData();
    }

    // handles background execution
    private class MovieTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {
            String jsonData = fetchJsonData();
//            Log.e(LOG_TAG,""jsonData);
            return jsonData;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null){
                // clearn previous data
                mAdapterMovieInfo.clear();

                JsonHandler jsonHandler = new JsonHandler(s);
                ArrayList<MovieInfo> movieInfoArrayList = jsonHandler.parseData();

                mAdapterMovieInfo.addAll(movieInfoArrayList);

                Log.e(LOG_TAG,"All Done"+s.length());
            }
        }
    }
}
