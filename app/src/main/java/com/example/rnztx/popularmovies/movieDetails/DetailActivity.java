package com.example.rnztx.popularmovies.movieDetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.rnztx.popularmovies.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detailActivity_container,new DetailFragment())
                    .commit();
        }
    }
}
