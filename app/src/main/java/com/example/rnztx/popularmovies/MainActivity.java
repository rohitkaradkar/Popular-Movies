package com.example.rnztx.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView sampleImage = (ImageView)findViewById(R.id.image_sample);
        Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into(sampleImage);
    }
}
