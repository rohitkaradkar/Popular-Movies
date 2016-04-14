package com.example.rnztx.popularmovies.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

import com.example.rnztx.popularmovies.R;

/**
 * Created by rnztx on 14/4/16.
 */
public class Utilities {
    public static void storeMovieSortChoice(FragmentActivity activity, String choice){
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
            String key = activity.getString(R.string.key_movie_sort_choice);
            editor.putString(key,choice);
        editor.apply();
    }

    public static String getMovieSortChoice(FragmentActivity activity){
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        String key = activity.getString(R.string.key_movie_sort_choice);
        String defaultValue = activity.getString(R.string.value_movie_sort_topRated);
        String choice = sharedPreferences.getString(key,defaultValue);
        return  choice;
    }
}
