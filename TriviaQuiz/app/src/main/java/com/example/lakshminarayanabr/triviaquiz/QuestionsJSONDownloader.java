package com.example.lakshminarayanabr.triviaquiz;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by lakshminarayanabr on 9/23/16.
 */
public class QuestionsJSONDownloader extends AsyncTask<String,Integer,ArrayList<Question>> {

    MainActivity activity;
    String baseURL;

    public QuestionsJSONDownloader(MainActivity activity, String baseURL) {
        this.activity = activity;
        this.baseURL = baseURL;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.pgBar.setVisibility(View.VISIBLE);
        activity.txtLoading.setVisibility(View.VISIBLE);
        activity.btnStartTrivia.setEnabled(false);
        activity.imgVwTrivia.setVisibility(View.INVISIBLE);
        activity.txtLoading.setText("Loading...");


    }

    @Override
    protected void onPostExecute(ArrayList<Question> questions) {
        super.onPostExecute(questions);
        activity.pgBar.setVisibility(View.INVISIBLE);
//        activity.txtLoading.setVisibility(View.INVISIBLE);
        activity.btnStartTrivia.setEnabled(true);
        activity.imgVwTrivia.setVisibility(View.VISIBLE);
        activity.txtLoading.setText("Trivia Ready");

        activity.questionList=questions;




        Log.d("Demo","ArrayList"+questions.toString());

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected ArrayList<Question> doInBackground(String... params) {

        ArrayList<Question> questionsList=new ArrayList<Question>();
        HttpURLConnection connection;
        URL url = null;
        try {
            url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if(connection.getResponseCode()==HttpURLConnection.HTTP_OK)
            {
                BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line=reader.readLine();
                StringBuilder sb=new StringBuilder();
                while(line!=null)
                {
                    sb.append(line);
                    line=reader.readLine();

                }

                return parseJson(sb.toString());


            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally {


        }


        return questionsList;
    }

    public ArrayList<Question> parseJson(String ln) throws JSONException {
        ArrayList<Question> questionArrayList=new ArrayList<Question>();
        JSONObject root=new JSONObject(ln);

        JSONArray questionsJSONArray=root.getJSONArray("questions");

        for(int i=0;i<questionsJSONArray.length();i++)
        {
            Question ques=new Question();
            JSONObject questionObject=questionsJSONArray.getJSONObject(i);

            ques.questionText=questionObject.getString("text");
            ques.id= Integer.parseInt(questionObject.getString("id"))+1;
            if (questionObject.has("image"))
            ques.imageURL=questionObject.getString("image");
            JSONObject choicesJSONObject=questionObject.getJSONObject("choices");
            JSONArray choiceJSONArray=choicesJSONObject.getJSONArray("choice");
            for(int j=0;j<choiceJSONArray.length();j++)
            {

                Log.d("Demo",choiceJSONArray.getString(j));

                ques.choices.add(choiceJSONArray.getString(j));




            }
            ques.answerChoice= Integer.parseInt(choicesJSONObject.getString("answer"));

            questionArrayList.add(ques);






        }




        return questionArrayList;

    }
}
