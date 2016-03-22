package com.example.rnztx.popularmovies.movieDetails;


import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rnztx.popularmovies.R;
import com.example.rnztx.popularmovies.data.MovieContract;
import com.example.rnztx.popularmovies.modules.MovieInfo;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    @Bind(R.id.btnFavourite)
    ImageButton btnFavourite;
    private MovieInfo mMovieInfo = null;
    private static boolean isSaved = false;

    final static String IMAGE_BASE = "http://image.tmdb.org/t/p/w185/";
    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //retrieve object from intent
        String INTENT_EXTRA_KEY = getString(R.string.intentExtra_MovieInfo_Key);
        Bundle intentBundle = getActivity().getIntent().getExtras();
        mMovieInfo = (MovieInfo)intentBundle.getParcelable(INTENT_EXTRA_KEY);
        checkFavourite();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this,rootView);
        updateIcon();
        // set title
        TextView txtMovieTitle= (TextView)rootView.findViewById(R.id.txt_movie_title);
        txtMovieTitle.setText(mMovieInfo.getTitle());

        // set Poster Image
        ImageView imgMoviePoster = (ImageView)rootView.findViewById(R.id.img_detailActivity_poster);
        String imgUrl = IMAGE_BASE+ mMovieInfo.getPoster_path();
        Picasso.with(getContext()).load(imgUrl).into(imgMoviePoster);

        // set movie Summary
        TextView txtMovieSummary= (TextView)rootView.findViewById(R.id.txt_movie_summary);
        txtMovieSummary.setText(mMovieInfo.getPlot());

        // set Release Date
        TextView txtMovieReleaseDate= (TextView)rootView.findViewById(R.id.txt_movie_releaseDate);
        txtMovieReleaseDate.setText(mMovieInfo.getRelease_date());

        // set avg rating
        TextView txtMovieRatings= (TextView)rootView.findViewById(R.id.txt_movie_rating);
        txtMovieRatings.setText(mMovieInfo.getVote_avg());

        return rootView;
    }
    private void updateIcon(){
        if (isSaved){
            btnFavourite.setImageResource(R.drawable.ic_favorite_full);
        }
        else
            btnFavourite.setImageResource(R.drawable.ic_favorite_holo);
    }
    @OnClick(R.id.btnFavourite)
    public void markAsFavourite(){
        if (mMovieInfo != null){

            if (isSaved){ // delete from database

            }
            else { // save to databse
                ContentValues values = new ContentValues();
                values.put(MovieContract.MovieEntry._ID,mMovieInfo.getMovie_id());
                values.put(MovieContract.MovieEntry.COLUMN_TITLE,mMovieInfo.getTitle());
                values.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE,mMovieInfo.getRelease_date());
                values.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH,mMovieInfo.getPoster_path());
                values.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE,mMovieInfo.getVote_avg());
                values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW,mMovieInfo.getPlot());

                Uri movieUri = getContext().getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI,values);
                long rowId = ContentUris.parseId(movieUri);

                if (rowId>0){
                    Log.e(LOG_TAG,"Saved: "+mMovieInfo.getMovie_id());
                    isSaved = true;
                }
            }
            updateIcon();
        }
    }

    private void checkFavourite(){
        if (mMovieInfo!=null){
            Cursor cursor = null;
            try {
                long movieId = Long.valueOf(mMovieInfo.getMovie_id());
                cursor = getContext().getContentResolver().query(
                        MovieContract.MovieEntry.buildMovieWithIdUri(movieId),
                        null,
                        MovieContract.MovieEntry._ID+" = "+movieId,null,null);

                if (cursor.moveToFirst()){
                    if (movieId == Long.valueOf(cursor.getString(0))){
                        isSaved = true;
                        Log.e(LOG_TAG,"Saved to favourite");
                    }
                }
                else {
                    isSaved = false;
                    Log.e(LOG_TAG,"not saved");
                }
            }catch (Exception e){
                Log.e(LOG_TAG,e.toString());
            }finally {
                if (cursor!=null)
                    cursor.close();
            }
        }
    }


}
