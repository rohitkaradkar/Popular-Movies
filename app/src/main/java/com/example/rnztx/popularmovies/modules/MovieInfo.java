package com.example.rnztx.popularmovies.modules;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rnztx on 12/2/16.
 */
public class MovieInfo implements Parcelable {
    private String title;
    private String poster_path;
    private String plot;
    private String vote_avg;
    private String release_date;

    public MovieInfo(String title, String poster_path, String plot, String vote_avg, String release_date) {
        this.title = title;
        this.poster_path = poster_path;
        this.plot = plot;
        this.vote_avg = vote_avg;
        this.release_date = release_date;
    }
     private MovieInfo(Parcel parcel){
        title = parcel.readString();
        poster_path = parcel.readString();
        plot = parcel.readString();
        vote_avg = parcel.readString();
        release_date = parcel.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeString(title);
        parcel.writeString(poster_path);
        parcel.writeString(plot);
        parcel.writeString(vote_avg);
        parcel.writeString(release_date);
    }

    public final Parcelable.Creator<MovieInfo> CREATOR = new Parcelable.Creator<MovieInfo>(){
        @Override
        public MovieInfo createFromParcel(Parcel source) {
             return new MovieInfo(source);
        }

        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };
}
