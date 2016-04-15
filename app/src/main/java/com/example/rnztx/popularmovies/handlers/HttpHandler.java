package com.example.rnztx.popularmovies.handlers;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rnztx on 11/2/16.
 */
public class HttpHandler {
    final private static String LOG_TAG = HttpHandler.class.getSimpleName();
    final private static String REQUEST_TYPE = "GET";
    private URL connectionUrl;

    public String fetchData(){
        //Originally posted by Udacity
        //https://gist.github.com/udacityandroid/d6a7bb21904046a91695

        // these things need to be closed inside Finally
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // will contain raw JSON responce as String
        String forecastJsonStr = null;
        try{

            // open connection to Server
            urlConnection = (HttpURLConnection)connectionUrl.openConnection();
            urlConnection.setRequestMethod(REQUEST_TYPE);
            urlConnection.connect();

            // read inout stream to String
            InputStream inputStream = urlConnection.getInputStream();

            if(inputStream == null)
                return null;

            // get inputStream to BufferedReader
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer = new StringBuffer();
            String line;

            while((line = reader.readLine())!= null){
                // make debugging LOT easier by adding new line
                buffer.append(line + '\n');
            }

            // check if stream is empty
            if(buffer.length()==0)
                return null;

            forecastJsonStr = buffer.toString();

        }
        catch(Exception e){
            Log.e(LOG_TAG,e.toString());
        }
        finally {
            if(urlConnection!=null)
                urlConnection.disconnect();
            if(reader != null){
                try{
                    reader.close();
                }catch(Exception e){
                    Log.e(LOG_TAG,e.toString());
                }
            }
        }
        return forecastJsonStr;
    }

    public HttpHandler(String url ) {
        try {
            this.connectionUrl = new URL(url);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG,e.toString());
        }
    }

    public String execute() throws IOException{
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(this.connectionUrl)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}