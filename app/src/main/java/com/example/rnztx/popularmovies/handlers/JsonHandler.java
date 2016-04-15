package com.example.rnztx.popularmovies.handlers;


import android.util.Log;

import com.example.rnztx.popularmovies.modules.MovieInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonHandler {
    private String LOG_TAG = JsonHandler.class.getSimpleName();
    private String rawJsonData = null;

    //required fields from Jason Data
    private final String TMDB_RESULTS = "results";
    private final String ORIGINAL_TITLE = "original_title";
    private final String POSTER_PATH = "poster_path";
    private final String PLOT = "overview";
    private final String VOTE_AVG = "vote_average";
    private final String RELEASE_DATE = "release_date";
    private final String MOVIE_ID = "id";

    public JsonHandler(){
        // this is dummy Data of single movie
        this.rawJsonData = "{\n" +
                "\"poster_path\": \"/6bCplVkhowCjTHXWv49UjRPn0eK.jpg\",\n" +
                "\"adult\": false,\n" +
                "\"overview\": \"Fearing the actions of a god-like Super Hero left unchecked, Gotham City’s own formidable, forceful vigilante takes on Metropolis’s most revered, modern-day savior, while the world wrestles with what sort of hero it really needs. And with Batman and Superman at war with one another, a new threat quickly arises, putting mankind in greater danger than it’s ever known before.\",\n" +
                "\"release_date\": \"2016-03-23\",\n" +
                "\"genre_ids\": [\n" +
                "28,\n" +
                "12,\n" +
                "14,\n" +
                "878\n" +
                "],\n" +
                "\"id\": 209112,\n" +
                "\"original_title\": \"Batman v Superman: Dawn of Justice\",\n" +
                "\"original_language\": \"en\",\n" +
                "\"title\": \"Batman v Superman: Dawn of Justice\",\n" +
                "\"backdrop_path\": \"/cejHDyHEJSjtpsPgGzm1GNsZLMF.jpg\",\n" +
                "\"popularity\": 90.344458,\n" +
                "\"vote_count\": 709,\n" +
                "\"video\": false,\n" +
                "\"vote_average\": 5.94\n" +
                "}";
    }

    public JsonHandler(String data){
        this.rawJsonData = data;
    }

    public ArrayList parseData(){
        ArrayList<MovieInfo> movieInfoArray = new ArrayList<>();
        try {
            JSONObject TMDB_root = new JSONObject(this.rawJsonData);
            JSONArray resultsArray = TMDB_root.getJSONArray(TMDB_RESULTS);
            for(int i=0; i < resultsArray.length();i++){

                JSONObject singleMovieInfo = resultsArray.getJSONObject(i);

                MovieInfo information = new MovieInfo(
                     singleMovieInfo.getString(ORIGINAL_TITLE)
                    ,singleMovieInfo.getString(POSTER_PATH)
                    ,singleMovieInfo.getString(PLOT)
                    ,singleMovieInfo.getString(VOTE_AVG)
                    ,singleMovieInfo.getString(RELEASE_DATE)
                    ,Long.toString(singleMovieInfo.getLong(MOVIE_ID))
                );

                movieInfoArray.add(information);
//                Log.e(LOG_TAG,information.toString());
            }
        }catch (Exception e){
            Log.e(LOG_TAG,e.toString());
        }
        return movieInfoArray;
    }

}
