package com.example.rnztx.popularmovies.modules.tmdb.reviews;

/**
 * Created by rnztx on 15/4/16.
 */
public class ReviewResult {
    private String id;
    private String author;
    private String content;
    private String url;

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "ReviewResult{\n" +
                "id='" + id + '\'' +
                ",\nauthor='" + author + '\'' +
                ", \ncontent='" + content + '\'' +
                ", \nurl='" + url + '\'' +
                '}';
    }
}
