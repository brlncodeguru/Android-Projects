package com.example.lakshminarayanabr.inclass06;

/**
 * Created by lakshminarayanabr on 9/27/16.
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by rigot on 9/26/2016.
 */

public class GetImageAsyncTask extends AsyncTask<String, Void, Bitmap>{



    GetImageAsyncTask(iImage im, ImageView i){
        this.im=im;
        image = i;
    }

    iImage im;
    ImageView image;

    public static interface iImage{
        void setImage(Bitmap b, ImageView v);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        im.setImage(bitmap,image);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Log.d("Demo",strings[0]);

        try {
            URL url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");



            con.connect();
            int statusCode = con.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {

                InputStream in = con.getInputStream();
                Bitmap bp = BitmapFactory.decodeStream(in);
                return bp;
            }} catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        return null;


    }
}
