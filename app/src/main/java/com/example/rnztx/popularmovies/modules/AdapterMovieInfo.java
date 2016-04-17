package com.example.rnztx.popularmovies.modules;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.rnztx.popularmovies.R;
import com.example.rnztx.popularmovies.modules.utils.Utilities;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rnztx on 12/2/16.
 */
public class AdapterMovieInfo extends ArrayAdapter<MovieInfo> {
    private static String LOG_TAG = AdapterMovieInfo.class.getSimpleName();
    @Bind(R.id.gridView_moviePoster) ImageView moviePosterImage;

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

        ButterKnife.bind(this,convertView);
        // check if image is stored on sd! offline mode...
        File imagePath = Utilities.getAbsoluteImageStoragePath(IMAGE_NAME);
        if (imagePath.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath.getPath());
            moviePosterImage.setImageBitmap(bitmap);
        }else
            Picasso.with(getContext()).load(IMAGE_BASE+IMAGE_NAME).into(moviePosterImage);

        return convertView;
    }
}
