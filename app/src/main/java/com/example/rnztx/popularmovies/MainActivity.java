package com.example.rnztx.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView sampleImage = (ImageView)findViewById(R.id.image_sample);
        Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into(sampleImage);


//        // only for debugging
//        JsonHandler fetchMovies = new JsonHandler();
//        ArrayList<ArrayList> movieInfo = fetchMovies.parseData();
//
//        for (ArrayList item : movieInfo){
//            Log.e(LOG_TAG,"----------");
//            for (int i=0; i < item.size(); i++){
//                Log.e(LOG_TAG,item.get(i).toString());
//            }
//        }

    }
}
