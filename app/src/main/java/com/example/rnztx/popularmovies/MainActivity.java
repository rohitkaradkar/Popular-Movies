package com.example.rnztx.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.rnztx.popularmovies.modules.utils.Constants;
import com.example.rnztx.popularmovies.modules.MovieInfo;
import com.example.rnztx.popularmovies.movieDetails.DetailActivity;
import com.example.rnztx.popularmovies.movieDetails.DetailFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.Callback {
    final String LOG_TAG = MainActivity.class.getSimpleName();
    private static boolean mTwoPane = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // check if it is Tablet Ui
        if(findViewById(R.id.MovieDetail_container)!=null)
            mTwoPane = true;
        else
            mTwoPane = false;

    }

    @Override
    public void onItemSelected(MovieInfo selectedMovie) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(Constants.ARG_MOVIE_DETAIL,selectedMovie);

        if(mTwoPane){
            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.MovieDetail_container,detailFragment)
                    .commit();
        }
        else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtras(arguments);
            startActivity(intent);
        }
    }
}
