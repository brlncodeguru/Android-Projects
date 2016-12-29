package com.example.lakshminarayanabr.inclass05;

import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by lakshminarayanabr on 9/19/16.
 */
public class ShowImage extends AsyncTask<String,Void,ArrayList<String>> {

    public MainActivity context ;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        context.dialog.setMessage("Loading Dictionary ...");
        context.dialog.setCancelable(false);
        context.dialog.show();
    }

    public ShowImage(MainActivity context) {
        this.context = context;
    }

    @Override
    protected ArrayList<String> doInBackground(String... params) {

        ArrayList<String> imageUrl = new ArrayList<>();
       String baseUrl = "http://dev.theappsdr.com/apis/photos/index.php?keyword=" + params[0] ;
        BufferedReader reader ;
        try {
            URL url = new URL(baseUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
             reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";

            while((line = reader.readLine())!=null)
            {

                sb.append(line);

            }
            String[] resultingURL = sb.toString().split(";");
            for (String s : resultingURL

            )
            {

                imageUrl.add(s);
                Log.d("DEMO", s);

            }
            imageUrl.remove(0);
            return imageUrl;

        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;



    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);
        context.imageurl = strings;
        context.setImage();

        context.dialog.dismiss();
    }
}
