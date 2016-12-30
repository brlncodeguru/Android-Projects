package com.example.lakshminarayanabr.midtermexamapp;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

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
import java.util.Comparator;

/**
 * Created by lakshminarayanabr on 10/24/16.
 */
public class GetAppDataFromAPI extends AsyncTask<String,Void,ArrayList<AppObject>> {

    MainActivity activity;

    public GetAppDataFromAPI(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<AppObject> doInBackground(String... params) {
        ArrayList<AppObject> appObjectArrayList=new ArrayList<AppObject>();
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


        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.pgDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<AppObject> appObjects) {
        Log.d("Demo",appObjects.toString());
        activity.pgDialog.dismiss();

        activity.filteredList=new ArrayList<AppObject>(activity.dm.getAllNotes());
        activity.appObjectArrayList=appObjects;

        if (activity.filteredList!=null)
        {
            if(activity.filteredList.size()>0)
            {
                for(int i=0;i<activity.appObjectArrayList.size();i++)
                {

                    for (int j=0;j<activity.filteredList.size();j++)
                    {
                        AppObject object=activity.appObjectArrayList.get(i);
                        AppObject object1=activity.filteredList.get(j);

                        if(object1.equals(object))
                        {
                            activity.appObjectArrayList.remove(object1);
                        }
                    }




                }

            }

        }
        activity.sw.setChecked(false);
        activity.sw.setText("Descending");
        Collections.sort(activity.appObjectArrayList, new Comparator<AppObject>(){
            public int compare(AppObject emp1, AppObject emp2) {
                // ## Ascending order
                return emp2.price.compareTo(emp1.price); // To compare string values
                // return Integer.valueOf(emp1.getId()).compareTo(emp2.getId()); // To compare integer values

                // ## Descending order
                // return emp2.getFirstName().compareToIgnoreCase(emp1.getFirstName()); // To compare string values
                // return Integer.valueOf(emp2.getId()).compareTo(emp1.getId()); // To compare integer values
            }
        });

        activity.adapter=new TopListAdapter(activity,R.layout.toplistviewrowitem,activity.appObjectArrayList);

        activity.listTop.setAdapter(activity.adapter);




        super.onPostExecute(appObjects);
    }
    public ArrayList<AppObject> parseJson(String ln) throws  JSONException
    {
        ArrayList<AppObject> appObjectArrayList=new ArrayList<>();


        JSONObject root=new JSONObject(ln);

        JSONObject feedObject=root.getJSONObject("feed");

        JSONArray entryArray=feedObject.getJSONArray("entry");

        for(int i=0;i<entryArray.length();i++)
        {
            AppObject appObj=new AppObject();

            JSONObject appObject=entryArray.getJSONObject(i);

            JSONObject nameObject=appObject.getJSONObject("im:name");
            appObj.appName=nameObject.getString("label");

            JSONObject priceObject=appObject.getJSONObject("im:price");
            JSONObject attributeObject=priceObject.getJSONObject("attributes");
            appObj.price=attributeObject.getString("amount");
            appObj.priceUNIT=attributeObject.getString("currency");



            JSONObject maxObject=null;
            JSONObject minObject=null;

            JSONArray imageArray=appObject.getJSONArray("im:image");
            int minHeight=0;
            int maxHeight=0;


            for(int j=0;j<imageArray.length();j++)
            {
                JSONObject imagObject=imageArray.getJSONObject(j);
                JSONObject attributeObj=imagObject.getJSONObject("attributes");

                if(attributeObj.getInt("height")<minHeight||minHeight==0)
                {
                    minHeight=attributeObj.getInt("height");
                    minObject=imagObject;

                }

                if(attributeObj.getInt("height")>maxHeight||maxHeight==0)
                {
                    maxHeight=attributeObj.getInt("height");
                    maxObject=imagObject;

                }


            }

           appObj.thumbnailImageURL= minObject.getString("label");
            appObj.largeImageUrl=maxObject.getString("label");

            appObjectArrayList.add(appObj);


        }




        return appObjectArrayList;
    }

}
