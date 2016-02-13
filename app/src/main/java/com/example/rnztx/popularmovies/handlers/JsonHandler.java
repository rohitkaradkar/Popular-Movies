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

    public JsonHandler(){
        this.rawJsonData = "{\"page\":1,\"results\":[{\"poster_path\":\"\\/inVq3FRqcYIRl2la8iZikYYxFNR.jpg\",\"adult\":false,\"overview\":\"Based upon Marvel Comics’ most unconventional anti-hero, DEADPOOL tells the origin story of former Special Forces operative turned mercenary Wade Wilson, who after being subjected to a rogue experiment that leaves him with accelerated healing powers, adopts the alter ego Deadpool. Armed with his new abilities and a dark, twisted sense of humor, Deadpool hunts down the man who nearly destroyed his life.\",\"release_date\":\"2016-02-09\",\"genre_ids\":[35,12,28,878],\"id\":293660,\"original_title\":\"Deadpool\",\"original_language\":\"en\",\"title\":\"Deadpool\",\"backdrop_path\":\"\\/nbIrDhOtUpdD9HKDBRy02a8VhpV.jpg\",\"popularity\":57.267788,\"vote_count\":274,\"video\":false,\"vote_average\":6.16}],\"total_results\":255141,\"total_pages\":12758}";
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
                );

                movieInfoArray.add(information);
                Log.e(LOG_TAG,information.toString());
            }
        }catch (Exception e){
            Log.e(LOG_TAG,e.toString());
        }
        return movieInfoArray;
    }
}
