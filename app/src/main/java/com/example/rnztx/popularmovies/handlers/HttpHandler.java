package com.example.rnztx.popularmovies.handlers;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rnztx on 11/2/16.
 */
public class HttpHandler {
    final private static String LOG_TAG = HttpHandler.class.getSimpleName();
    final private static String REQUEST_TYPE = "GET";
    private String connectionUrl;

    public HttpHandler(){
    }

    public HttpHandler(String url ) {
        this.connectionUrl = url;
    }
    public String execute(String url) throws IOException{
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    public String fetchData() throws IOException{
        return this.execute(this.connectionUrl);
    }
    public String execute() throws IOException{
       return this.execute(this.connectionUrl);
    }
}