package com.example.rnztx.popularmovies.movieDetails;


import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rnztx.popularmovies.R;
import com.example.rnztx.popularmovies.data.MovieContract;
import com.example.rnztx.popularmovies.handlers.HttpHandler;
import com.example.rnztx.popularmovies.modules.MovieInfo;
import com.example.rnztx.popularmovies.modules.tmdb.reviews.ReviewResult;
import com.example.rnztx.popularmovies.modules.tmdb.reviews.TmdbReviews;
import com.example.rnztx.popularmovies.modules.tmdb.videos.TmdbVideos;
import com.example.rnztx.popularmovies.modules.tmdb.videos.VideoResult;
import com.example.rnztx.popularmovies.modules.utils.Constants;
import com.example.rnztx.popularmovies.modules.utils.Utilities;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    @Bind(R.id.btnFavourite)  ImageButton btnFavourite;
    @Bind(R.id.txt_review_author) TextView txtReviewAuthor;
    @Bind(R.id.txt_review_content) TextView txtReviewContent;
    @Bind(R.id.img_detailActivity_poster) ImageView imgMoviePoster;
    @Bind(R.id.container_movie_trailer) LinearLayout containerMovieTrailer;

    private MovieInfo mMovieInfo ;
    private static boolean isSaved = false;
    private MovieDataTask mFetchMovieDataTask;
    private View.OnClickListener mTrailerClickListener;
    private HashMap<ImageView,String> mTrailerKeys = new HashMap<>();

    final static String IMAGE_BASE = "http://image.tmdb.org/t/p/w185/";
    public DetailFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //retrieve object in Two Pane
        Bundle arguments = getArguments();
        if (arguments==null) {
            // retrieve object in single pane
            Intent intent = getActivity().getIntent();
            arguments = intent.getExtras();
        }

        mMovieInfo = arguments.getParcelable(Constants.ARG_MOVIE_DETAIL);
        // download movie reviews and Videos info
        mFetchMovieDataTask = new MovieDataTask(mMovieInfo.getMovie_id());
        mFetchMovieDataTask.execute();
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

        mTrailerClickListener = new View.OnClickListener() {
            /**
             * Youtube url play is referred from
             * http://stackoverflow.com/a/12439378/2804351
             */
            @Override
            public void onClick(View v) {
                String youtubeKey = mTrailerKeys.get(v);
                Intent intentYoutubeTrailer;
                try {
                    intentYoutubeTrailer = new Intent(Intent.ACTION_VIEW,Uri.parse("vnd.youtube:"+youtubeKey));
                    startActivity(intentYoutubeTrailer);
                }catch (ActivityNotFoundException e){
                    intentYoutubeTrailer = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v="+youtubeKey));
                    startActivity(intentYoutubeTrailer);
                }
            }
        };
        return rootView;
    }

    public void showReviews(TmdbReviews tmdbReviews){
        for (ReviewResult result: tmdbReviews.getResults()){
            txtReviewAuthor.setText(result.getAuthor());
            txtReviewContent.setText(result.getContent());
        }
    }
    public void showMovieVideos(TmdbVideos tmdbVideos){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,16,0,16);

        for (VideoResult result: tmdbVideos.getResults()){
            String youtubeKey = result.getKey();
            String videoThumbnailUrl = Utilities.getYoutubeVideoThumbnailUrl(youtubeKey);

            ImageView imageView = new ImageView(getContext());
            imageView.setOnClickListener(mTrailerClickListener);
            imageView.setLayoutParams(layoutParams);
            Picasso.with(getContext()).load(videoThumbnailUrl).into(imageView);
            mTrailerKeys.put(imageView,youtubeKey);
            containerMovieTrailer.addView(imageView);
        }
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
                long movieId = Long.valueOf(mMovieInfo.getMovie_id());
                int rowsDeleted = getContext().getContentResolver().delete(
                        MovieContract.MovieEntry.buildMovieWithIdUri(movieId),
                        MovieContract.MovieEntry._ID + " = ?",
                        new String[]{mMovieInfo.getMovie_id()}
                );
                if (rowsDeleted > 0){
                    Log.e(LOG_TAG,"Movie Deleted");
                    updateIcon();
                    isSaved = false;
                }else
                    Log.e(LOG_TAG,"Failed to delete" +rowsDeleted);
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
                storePosterToExternalStorage(mMovieInfo.getPoster_path());
                if (rowId>0){
                    isSaved = true;
                }
            }
            updateIcon();
        }
    }

    private void storePosterToExternalStorage(String imageName){
        // this code snippet is referred from StackOverflow
        if (imgMoviePoster!=null){
            // get image from ImageView
            BitmapDrawable bitmapDrawable = (BitmapDrawable)imgMoviePoster.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            // get absolute image Path
            File imageFile = Utilities.getAbsoluteImageStoragePath(imageName);
            FileOutputStream fileOutputStream;
            try {
                fileOutputStream = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
//                Log.e(LOG_TAG,"Saved to"+imageFile.getPath());
            }catch (Exception e){
                Log.e(LOG_TAG,e.toString());
            }
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
                    }
                }
                else {
                    isSaved = false;
                }
            }catch (Exception e){
                Log.e(LOG_TAG,e.toString());
            }finally {
                if (cursor!=null)
                    cursor.close();
            }
        }
    }


    /**
     * Created by rnztx on 16/4/16.
     */
    private class MovieDataTask extends AsyncTask<Void,Void,Boolean> {
        private final String LOG_TAG = MovieDataTask.class.getSimpleName();
        private String movie_id;
        private String urlMovieVideos;
        private String urlMovieReviews;
        private TmdbReviews movieReviews;
        private TmdbVideos movieVideos;

        public MovieDataTask(String id){
            this.movie_id = id;
            this.urlMovieReviews = Utilities.buildUrlForMovieReviews(movie_id);
            this.urlMovieVideos = Utilities.buildUrlForMovieVideos(movie_id);
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            HttpHandler httpHandler = new HttpHandler();
            try {
                String jsonMoviesReviewData = httpHandler.execute(this.urlMovieReviews);
                String jsonMovieVideosData = httpHandler.execute(this.urlMovieVideos);
                movieReviews = new Gson().fromJson(jsonMoviesReviewData,TmdbReviews.class);
                movieVideos = new Gson().fromJson(jsonMovieVideosData,TmdbVideos.class);
                return true;
            }catch (Exception e){
                Log.e(LOG_TAG,e.toString());
            }
            return false;
        }

        public TmdbReviews getMovieReviews() {
            if (movieReviews.getId()!=null)
                return movieReviews;
            else
                throw new NullPointerException("Failed to get movie Reviews");
        }

        public TmdbVideos getMovieVideos() {
            if (movieVideos.getId()!=null)
                return movieVideos;
            else
                throw new NullPointerException("Failed to get movie Videos");
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean){
                showReviews(movieReviews);
                showMovieVideos(movieVideos);
            }
        }
    }
}
