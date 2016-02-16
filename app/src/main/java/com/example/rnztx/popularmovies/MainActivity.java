package com.example.rnztx.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {
    final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainActivity_container,new MainFragment())
                    .commit();
        }
    }

    // create Settings menu


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
