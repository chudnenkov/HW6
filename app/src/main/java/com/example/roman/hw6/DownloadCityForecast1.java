package com.example.roman.hw6;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by roman on 08.01.2016.
 */
public class DownloadCityForecast1 extends AsyncTask<String, Integer, String> {

    public ProgressDialog progressDialog;
    public static Activity context   ;

    @Override
    protected void onPreExecute(){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("downloading...");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String ... url){
        String content = null;
        try{
         content = downloadForecast(url[0]);
        }catch (IOException e){}

        return content;
    }

    @Override
    protected void onPostExecute(String result){
        progressDialog.cancel();
    }


    public String downloadForecast (String urlCity) throws IOException {

        InputStream is = null;

        try {
            URL url = new URL(urlCity);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            //  httpURLConnection.setRequestMethod("GET");  ??
            httpURLConnection.connect();
            int response = httpURLConnection.getResponseCode();
            is = httpURLConnection.getInputStream();
            String content = IOUtils.toString(is, "UTF-8");

            return content;

        }finally {
            if (is !=null){
                is.close();
            }
        }

    }
}
