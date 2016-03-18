package com.example.rnztx.popularmovies.data;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.example.rnztx.popularmovies.modules.Constants;

/**
 * Created by rnztx on 18/3/16.
 */
public class TestUriMatcher extends AndroidTestCase {
    private static final long dummyMovieId = 25465456;
    private static final Uri TEST_MOVIE_ALL = MovieContract.MovieEntry.buildAllMoviesUri();
    private static final Uri TEST_MOVIE_WITH_ID = MovieContract.MovieEntry.buildMovieWithIdUri(dummyMovieId);
    private static final Uri TEST_MOVIE_WITH_ID_FOR_REVIEW = MovieContract.MovieEntry.buildMovieWithIdForReviewUri(dummyMovieId);

    public void testUriMatcher(){
        UriMatcher uriMatcher = MovieProvider.buildUriMatcher();
        assertEquals("ERROR: invalid all movies Uri",
                Constants.UriMatchCodes.MOVIES,
                uriMatcher.match(TEST_MOVIE_ALL));

        assertEquals("ERROR: invalid movie with id Uri",
                Constants.UriMatchCodes.MOVIE_WITH_ID,
                uriMatcher.match(TEST_MOVIE_WITH_ID));

        assertEquals("ERROR: invalid review Uri",
                Constants.UriMatchCodes.MOVIE_REVIEW,
                uriMatcher.match(TEST_MOVIE_WITH_ID_FOR_REVIEW));

    }
}
