package com.example.lakshminarayanabr.triviaquiz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.view.View;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by lakshminarayanabr on 9/23/16.
 */
public class ImageDonwnLoader extends AsyncTask<String,Integer,Bitmap> {

    TriviaActivity activity;

    public ImageDonwnLoader(TriviaActivity activity) {
        this.activity = activity;
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
    protected void onPreExecute() {
        super.onPreExecute();
        activity.pgBar.setVisibility(View.VISIBLE);
        activity.imgVw.setVisibility(View.INVISIBLE);
        activity.txtLoading.setVisibility(View.VISIBLE);
        activity.txtVwQuestion.setVisibility(View.INVISIBLE);
        activity.scrllVwChoices.setVisibility(View.INVISIBLE);


    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        activity.pgBar.setVisibility(View.INVISIBLE);
        activity.imgVw.setImageBitmap(bitmap);
        activity.imgVw.setVisibility(View.VISIBLE);
        activity.txtLoading.setVisibility(View.INVISIBLE);
        activity.txtVwQuestion.setVisibility(View.VISIBLE);
        activity.scrllVwChoices.setVisibility(View.VISIBLE);
        activity.isPaused=false;
        activity.timer=new CountDownTimer(activity.timeRemaining,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(activity.isPaused)
                {
                    cancel();

                }
                else
                {
                    activity.txtVwTimer.setText(millisUntilFinished/1000 +"seconds");

                    activity.timeRemaining=millisUntilFinished;


                }

            }

            @Override
            public void onFinish() {
                activity.startStatsActivity(activity.correctAnswers,activity.questionList);

            }
        }.start();




    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
}
