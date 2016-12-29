package com.example.lakshminarayanabr.inclass05;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by lakshminarayanabr on 9/19/16.
 */
public class CreateBitmap extends AsyncTask<String ,Void ,Bitmap>{
    public MainActivity context ;

    public CreateBitmap(MainActivity context) {
        this.context = context;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        URL url = null;
        try {
            url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            Bitmap image = BitmapFactory.decodeStream(connection.getInputStream());
            return image;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }


        return null;

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        ImageView iv = (ImageView) context.findViewById(R.id.imageViewResult);
        iv.setImageBitmap(bitmap);
        context.dialog.dismiss();
    }
    protected void onPreExecute() {
        super.onPreExecute();
        context.dialog.setMessage("Loading Photo ...");
        context.dialog.setCancelable(false);
        context.dialog.show();
    }
}
