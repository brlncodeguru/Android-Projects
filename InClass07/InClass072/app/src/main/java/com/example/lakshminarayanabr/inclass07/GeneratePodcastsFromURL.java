package com.example.lakshminarayanabr.inclass07;

import android.os.AsyncTask;
import android.util.Log;

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
import java.util.Collections;

/**
 * Created by lakshminarayanabr on 10/3/16.
 */
public class GeneratePodcastsFromURL extends AsyncTask<String,Void,ArrayList<Podcast>> {

    MainActivity activity;

    public GeneratePodcastsFromURL(MainActivity activity, String baseURL) {
        this.activity = activity;
        this.baseURL = baseURL;
    }

    String baseURL;

    @Override
    protected ArrayList<Podcast> doInBackground(String... params) {
        ArrayList<Podcast> questionsList=new ArrayList<Podcast>();
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
        }  finally {


        }


        return questionsList;




    }

    @Override
    protected void onPostExecute(ArrayList<Podcast> podcasts) {
        super.onPostExecute(podcasts);

        activity.pgDialog.dismiss();
        Collections.sort(podcasts,Podcast.dateOrder);

        activity.podcastArrayList=podcasts;

        activity.adapter=new PodcastsAdapter(activity,R.layout.itemrowlayout,podcasts);


        activity.listView.setAdapter(activity.adapter);
        activity.adapter.setNotifyOnChange(true);

        activity.orgPodcasst=podcasts;




        Log.d("Demo",podcasts.toString());
        Log.d("Demo", String.valueOf(podcasts.toArray().length));



    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    public ArrayList<Podcast> parseJson(String ln) throws  JSONException
    {
        ArrayList<Podcast> podcastArrayList=new ArrayList<>();


        JSONObject root=new JSONObject(ln);

        JSONObject feedObject=root.getJSONObject("feed");

        JSONArray entryArray=feedObject.getJSONArray("entry");

        for(int i=0;i<entryArray.length();i++)
        {
            Podcast pod=new Podcast();

            JSONObject podCastObj=entryArray.getJSONObject(i);


            JSONObject titleObj=podCastObj.getJSONObject("title");
            pod.title=titleObj.getString("label");

            JSONObject summaryObject=podCastObj.getJSONObject("summary");
            pod.summary=summaryObject.getString("label");

            JSONObject releaseDateObject=podCastObj.getJSONObject("im:releaseDate");
            pod.releaseDate=releaseDateObject.getString("label");


            JSONArray imageArrray=podCastObj.getJSONArray("im:image");
            int minHeight=0;
            int maxHeight=0;
            JSONObject minImageObject=null;
            JSONObject maxImageObject=null;

            for(int j=0;j<imageArrray.length();j++)
            {
                JSONObject imageObj=imageArrray.getJSONObject(j);

                JSONObject attributeObj=imageObj.getJSONObject("attributes");

                if(attributeObj.getInt("height")<minHeight||minHeight==0)
                {
                    minHeight=attributeObj.getInt("height");
                    minImageObject=imageObj;
                }

                if(attributeObj.getInt("height")>maxHeight||maxHeight==0)
                {
                    maxHeight=attributeObj.getInt("height");
                    maxImageObject=imageObj;



                }







            }

            pod.thumbnailURL=minImageObject.getString("label");
            pod.LargeImageURL=maxImageObject.getString("label");













            podcastArrayList.add(pod);
        }




        return podcastArrayList;
    }
}
