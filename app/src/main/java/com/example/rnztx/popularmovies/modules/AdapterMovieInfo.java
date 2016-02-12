package com.example.rnztx.popularmovies.modules;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rnztx.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rnztx on 12/2/16.
 */
public class AdapterMovieInfo extends ArrayAdapter<MovieInfo> {
    private static String LOG_TAG = AdapterMovieInfo.class.getSimpleName();
    public AdapterMovieInfo(Activity activity, List<MovieInfo> movieInfo){
        super(activity,0,movieInfo);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieInfo movieInfo = getItem(position);
        String IMAGE_BASE = "http://image.tmdb.org/t/p/w185/";
        String IMAGE_NAME = movieInfo.getPoster_path();
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item_grid, parent, false);
        }

        TextView txtMovieInfo = (TextView) convertView.findViewById(R.id.movie_text);
        txtMovieInfo.setText(movieInfo.getTitle());

        ImageView sampleImage = (ImageView)convertView.findViewById(R.id.movie_image);
        Picasso.with(getContext()).load(IMAGE_BASE+IMAGE_NAME).into(sampleImage);

//        Log.e(LOG_TAG,movieInfo.getPoster_path());

        return convertView;
    }
}
