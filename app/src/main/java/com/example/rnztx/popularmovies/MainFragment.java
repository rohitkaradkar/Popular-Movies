package com.example.rnztx.popularmovies;


import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.rnztx.popularmovies.data.MovieContract;
import com.example.rnztx.popularmovies.handlers.HttpHandler;
import com.example.rnztx.popularmovies.handlers.JsonHandler;
import com.example.rnztx.popularmovies.modules.AdapterMovieInfo;
import com.example.rnztx.popularmovies.modules.utils.Constants.ColIndices;
import com.example.rnztx.popularmovies.modules.MovieInfo;
import com.example.rnztx.popularmovies.modules.utils.Utilities;

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
    final static String BASE_API_VERSION = "3";
    final static String BASE_PATH = "movie";
    final static String KEY_API = "api_key";
    // Instance Extras
    final static String STATE_ADAPTER_CONTENT = "adapter";

    public MainFragment() {
        // Required empty public constructor
    }

    // Click Listener Event
    public interface Callback{
        void onItemSelected(MovieInfo selectedMovie);
    }

    private void populateGridView(){
        // get preference
        String choice = Utilities.getMovieSortChoice(getActivity());

        if (choice.equals(getString(R.string.value_movie_sort_favorite))){
            displayFavourites();
        }else {
            new MovieTask().execute(choice);
        }

    }

    public void displayFavourites(){

        Cursor cursor = null;
        ArrayList<MovieInfo> favouriteList = new ArrayList<>();
        try {
            cursor = getContext().getContentResolver().query(
                    MovieContract.MovieEntry.buildAllMoviesUri(),
                    null,null,null,null);

            cursor.moveToFirst();
            do{
                MovieInfo favouriteMovie = new MovieInfo(
                        cursor.getString(ColIndices.MOV_TITLE),
                        cursor.getString(ColIndices.MOV_POSTER_PATH),
                        cursor.getString(ColIndices.MOV_OVERVIEW),
                        cursor.getString(ColIndices.MOV_VOTE),
                        cursor.getString(ColIndices.MOV_RELEASE_DATE),
                        cursor.getString(ColIndices.MOV_ID)
                );
                favouriteList.add(favouriteMovie);
                if (favouriteList.size() > 0){
                    mAdapterMovieInfo.clear();
                    mAdapterMovieInfo.addAll(favouriteList);
                }
            }while (cursor.moveToNext());
        }catch (Exception e){
            Toast.makeText(getActivity(),"No Favourite List",Toast.LENGTH_SHORT).show();
        }
        finally {
            if (cursor!=null)
                cursor.close();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menu = item.getItemId();
        String choice = null;
        // set choice as per Menu Selection
        if(menu == R.id.menu_view_pop){
            choice = getString(R.string.value_movie_sort_popular);
        }
        else if(menu == R.id.menu_view_rated){
            choice = getString(R.string.value_movie_sort_topRated);
        }
        else if (menu == R.id.menu_view_fav){
            choice = getString(R.string.value_movie_sort_favorite);
        }
        // display as checked
        item.setChecked(true);
        // store choice
        Utilities.storeMovieSortChoice(getActivity(),choice);
        // populate Grid as per choice
        populateGridView();
        // notify user
        Toast.makeText(getActivity(),"Displaying "+item.getTitle(),Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
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
             populateGridView();
        }
        else {
             mAdapterMovieInfo.clear();
             ArrayList<MovieInfo> arrayList = savedInstanceState.getParcelableArrayList(STATE_ADAPTER_CONTENT);
             mAdapterMovieInfo.addAll(arrayList);
        }
    }

    // Add items to menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        /** get stored preference & set it as checked on start
         *
         * 0 popular
         * 1 top rated
         * 2 favorite
         */
        inflater.inflate(R.menu.menu,menu);

        String choice = Utilities.getMovieSortChoice(getActivity());
        int index = 0;
        if (choice.equals(getString(R.string.value_movie_sort_popular))) {
            index = 0;
        }
        else if (choice.equals(getString(R.string.value_movie_sort_topRated))) {
            index = 1;
        }
        else if (choice.equals(getString(R.string.value_movie_sort_favorite))) {
            index = 2;
        }

        menu.getItem(index).setChecked(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Save user Data
        ArrayList<MovieInfo> arrayList = new ArrayList<>();
        for(int i=0;i < mAdapterMovieInfo.getCount(); i++){
            arrayList.add(mAdapterMovieInfo.getItem(i));
        }
        outState.putParcelableArrayList(STATE_ADAPTER_CONTENT,arrayList);

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
                /**
                 * Now we Notify MainActivity to handle Click Event
                 */
                ((Callback)getActivity()).onItemSelected(selectedMovie);
            }
        });

        return rootView;
    }

    // handles background execution
    private class MovieTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            final String SORT_VALUE =params[0];
            final String VAL_API_KEY = BuildConfig.TMDB_API_KEY;

            Uri.Builder movieUriBuilder = new Uri.Builder();
            movieUriBuilder.scheme(URL_SCHEME)
                    .authority(BASE_URL_API)
                    .appendPath(BASE_API_VERSION)
                    .appendPath(BASE_PATH)
                    .appendPath(SORT_VALUE)
                    .appendQueryParameter(KEY_API,VAL_API_KEY);

            HttpHandler movieDbHandler = new HttpHandler(movieUriBuilder.build().toString());

            String jsonData = movieDbHandler.fetchData();
            Utilities.storeMovieSortChoice(getActivity(),SORT_VALUE);
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
