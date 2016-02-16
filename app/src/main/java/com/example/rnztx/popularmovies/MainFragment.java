package com.example.rnztx.popularmovies;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
    // Instance Extras
    final static String STATE_ADAPTER_CONTENT = "adapter";
    //Movie Sort Keys
    final static String SORTBY_POP = "popularity.desc";
    final static String SORTBY_RATING = "vote_count.desc";// vote_average / vote_count
    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //declare that fragment have menu options
        setHasOptionsMenu(true);

        // No parameter constructor returns dummy data
        ArrayList<MovieInfo> dummyData = new JsonHandler().parseData();
        mAdapterMovieInfo = new AdapterMovieInfo(getActivity(),dummyData);

        if(savedInstanceState == null){
             //Fetch Data & update adapter
             new MovieTask().execute(SORTBY_RATING);
        }
        else {
             mAdapterMovieInfo.clear();
             ArrayList<MovieInfo> arrayList = savedInstanceState.getParcelableArrayList(STATE_ADAPTER_CONTENT);
             mAdapterMovieInfo.addAll(arrayList);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu,menu);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Save user Data
        ArrayList<MovieInfo> arrayList = new ArrayList<>();
        for(int i=0;i < mAdapterMovieInfo.getCount(); i++){
            arrayList.add(mAdapterMovieInfo.getItem(i));
        }
        outState.putParcelableArrayList(STATE_ADAPTER_CONTENT,arrayList);
//        Log.e(LOG_TAG,"Saving Instance State");

        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // GridView To display on Main Activity
        GridView gridView = (GridView) rootView.findViewById(R.id.movie_gridView);
        gridView.setAdapter(mAdapterMovieInfo);



        // attaching click listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // get MovieInfo object for selected movie
                MovieInfo selectedMovie = mAdapterMovieInfo.getItem(position);

                // add Object to intent
                String INTENT_EXTRA_KEY = getString(R.string.intentExtra_MovieInfo_Key);
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(INTENT_EXTRA_KEY,selectedMovie);

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

        HttpHandler movieDbHandler = new HttpHandler(movieUriBuilder.build().toString());

        return movieDbHandler.fetchData();
    }

    // handles background execution
    private class MovieTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            final String VAL_SORT =params[0];
            final String VAL_API_KEY = BuildConfig.TMDB_API_KEY;

            Uri.Builder movieUriBuilder = new Uri.Builder();
            movieUriBuilder.scheme(URL_SCHEME)
                    .authority(BASE_URL_API)
                    .appendPath(BASE_PATH_DISCOVER1)
                    .appendPath(BASE_PATH_DISCOVER2)
                    .appendPath(BASE_PATH_DISCOVER3)
                    .appendQueryParameter(KEY_SORT,VAL_SORT)
                    .appendQueryParameter(KEY_API,VAL_API_KEY);

            HttpHandler movieDbHandler = new HttpHandler(movieUriBuilder.build().toString());

            String jsonData = movieDbHandler.fetchData();

            return jsonData;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null){
                // clear previous data
                mAdapterMovieInfo.clear();

                JsonHandler jsonHandler = new JsonHandler(s);
                ArrayList<MovieInfo> movieInfoArrayList = jsonHandler.parseData();

                mAdapterMovieInfo.addAll(movieInfoArrayList);

                Log.e(LOG_TAG,"DONE: Fetching Movie Data");
            }
        }
    }
}
