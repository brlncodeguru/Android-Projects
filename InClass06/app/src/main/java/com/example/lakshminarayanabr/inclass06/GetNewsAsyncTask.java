package com.example.lakshminarayanabr.inclass06;

/**
 * Created by lakshminarayanabr on 9/27/16.
 */
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by rigot on 9/21/2016.
 */
public class GetNewsAsyncTask extends AsyncTask<String, Void, ArrayList<NewsItem>> {
    IQueData activity;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();



    }

    //allows access to MainActivity
    public GetNewsAsyncTask(IQueData activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<NewsItem> doInBackground(String... questionsUrl) {

        try {
            URL url = new URL(questionsUrl[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {

                InputStream in = con.getInputStream();
                return NewsItemUtil.NewsItemParser.parseItems(in);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<NewsItem> questions) {
        super.onPostExecute(questions);
        Log.d("demo", questions.toString());



        activity.createNewsItem(questions);


    }

    public static interface IQueData {


        public void createNewsItem(ArrayList<NewsItem> questions);

    }
}
