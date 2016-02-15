package com.example.rnztx.popularmovies.movieDetails;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rnztx.popularmovies.R;
import com.example.rnztx.popularmovies.modules.MovieInfo;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    final static String IMAGE_BASE = "http://image.tmdb.org/t/p/w185/";
    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        //retrieve object from intent
        String INTENT_EXTRA_KEY = getString(R.string.intentExtra_MovieInfo_Key);
        Bundle intentBundle = getActivity().getIntent().getExtras();
        MovieInfo movieInfo = (MovieInfo)intentBundle.getParcelable(INTENT_EXTRA_KEY);

        // set title
        TextView txtMovieTitle= (TextView)rootView.findViewById(R.id.txt_movie_title);
        txtMovieTitle.setText(movieInfo.getTitle());

        // set Poster Image
        ImageView imgMoviePoster = (ImageView)rootView.findViewById(R.id.img_detailActivity_poster);
        String imgUrl = IMAGE_BASE+movieInfo.getPoster_path();
        Picasso.with(getContext()).load(imgUrl).into(imgMoviePoster);

        // set movie Summary
        TextView txtMovieSummary= (TextView)rootView.findViewById(R.id.txt_movie_summary);
        txtMovieSummary.setText(movieInfo.getPlot());

        // set Release Date
        TextView txtMovieReleaseDate= (TextView)rootView.findViewById(R.id.txt_movie_releaseDate);
        txtMovieReleaseDate.setText(movieInfo.getRelease_date());

        // set avg rating
        TextView txtMovieRatings= (TextView)rootView.findViewById(R.id.txt_movie_rating);
        txtMovieRatings.setText(movieInfo.getVote_avg());

        return rootView;
    }

}
