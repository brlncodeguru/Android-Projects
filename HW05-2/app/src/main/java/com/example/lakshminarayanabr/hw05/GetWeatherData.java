package com.example.lakshminarayanabr.hw05;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

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

/**
 * Created by lakshminarayanabr on 10/7/16.
 */
public class GetWeatherData extends AsyncTask<String,Void,ArrayList<Weather>> {

    Gson gson;
   public static String jsonObject;
    public static String jsonObjectList;
    String WEATHER_CODE="code";



    String baseURL,api_key,state,city_name;

CityWeatherActivity activity;


    public String getURL(){
        return this.baseURL+this.api_key+"/hourly/q/"+this.state+"/"+this.getCity_name()+".json" ;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getBaseURL() {

        return baseURL;
    }

    public String getApi_key() {
        return api_key;
    }

    public String getState() {
        return state;
    }

    public String getCity_name() {
        return city_name;
    }

    public GetWeatherData(String baseURL, String api_key, String state, String city_name, CityWeatherActivity activity) {
        this.baseURL = baseURL;
        this.api_key = api_key;
        this.state = state;
        this.city_name = city_name;
        this.activity = activity;
    }

    public GetWeatherData(String baseURL, String city_name, String state, String api_key) {

        this.baseURL = baseURL;
        this.city_name = city_name;
        this.state = state;
        this.api_key = api_key;
    }

    @Override
    protected ArrayList<Weather> doInBackground(String... params) {
        ArrayList<Weather> questionsList=new ArrayList<Weather>();
        HttpURLConnection connection;
        URL url = null;
        try {
            url = new URL(getURL());
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
    protected void onPostExecute(ArrayList<Weather> weathers) {
        super.onPostExecute(weathers);
        activity.pgDialog.dismiss();
        if(weathers.size()>0)
        {
            activity.txtCurrentlocation.setVisibility(View.VISIBLE);
            activity.txtLocation.setVisibility(View.VISIBLE);
            activity.listVW.setVisibility(View.VISIBLE);
            Log.d("Weather ********??????",weathers.toString());

            activity.adapter=new WeatherHourlyAdapter(activity,R.layout.itemrowlayout,weathers);


            activity.listVW.setAdapter(activity.adapter);
            activity.adapter.setNotifyOnChange(true);
            activity.weatherList=weathers;







        }
        else
        {
            activity.gotoPrevActivity();
        }



    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.pgDialog.show();
        activity.txtCurrentlocation.setVisibility(View.INVISIBLE);
        activity.txtLocation.setVisibility(View.INVISIBLE);
        activity.listVW.setVisibility(View.INVISIBLE);



    }

    public GetWeatherData() {

    }

    public ArrayList<Weather> parseJson(String ln) throws  JSONException
    {
        ArrayList<Weather> weatherHourlyList=new ArrayList<>();


        JSONObject root=new JSONObject(ln);



        JSONArray hourlyForecastArray=root.getJSONArray("hourly_forecast");


        if(hourlyForecastArray!=null)
        {
            Weather weatherObj = null;          //read the bottom page
            for(int i=0;i<hourlyForecastArray.length();i++)
            {
                weatherObj=new Weather();

                JSONObject hourlyForecastObj=hourlyForecastArray.getJSONObject(i);

                //time
                JSONObject timeObj=hourlyForecastObj.getJSONObject("FCTTIME");
                StringBuilder sb=new StringBuilder();
                weatherObj.setTime(timeObj.getString("civil"));
                weatherObj.mDay=Integer.parseInt(timeObj.getString("mday"));
                sb.append(timeObj.getString("mon")).append("/").append(weatherObj.mDay).append("/").append(timeObj.getString("year"));
                weatherObj.date=sb.toString();

                //temperature
                JSONObject tempObj=hourlyForecastObj.getJSONObject("temp");
                weatherObj.setTemperature(tempObj.getString("english"));
                //Dewpoint
                JSONObject dewPointObj=hourlyForecastObj.getJSONObject("dewpoint");
                weatherObj.setDewpoint(dewPointObj.getString("english"));
                //Clouds
                weatherObj.setClouds(hourlyForecastObj.getString("condition"));
                //windSpeed
                JSONObject windSpeedObj=hourlyForecastObj.getJSONObject("wspd");
                weatherObj.setWindSpeed(windSpeedObj.getString("english"));
                //windDirection
                JSONObject windDirectionObj=hourlyForecastObj.getJSONObject("wdir");
                weatherObj.setWindDirection(windDirectionObj.getString("degrees")+" \u00B0"+windDirectionObj.getString("dir"));

                //iconURL
                weatherObj.setIcon_URL(hourlyForecastObj.getString("icon_url"));

                //ClimateType
                weatherObj.setClimateType(hourlyForecastObj.getString("wx"));

                //humidity
                weatherObj.setHumidity(hourlyForecastObj.getString("humidity"));

                //feelsLike
                JSONObject feelsLikeObj=hourlyForecastObj.getJSONObject("feelslike");
                weatherObj.setFeelsLike(feelsLikeObj.getString("english"));

                //pressure
                JSONObject pressureObject=hourlyForecastObj.getJSONObject("mslp");
                weatherObj.setPressure(pressureObject.getString("metric"));


                weatherObj.cityName=this.city_name.replaceAll("_"," ");
                weatherObj.stateNmae=this.state;


                weatherHourlyList.add(weatherObj);


            }






        }
//        else if (errorObj!=null)
//        {
//            weatherHourlyList=null;
//
//
//        }

       // gson=new Gson();
      //  jsonObject=gson.toJson(weatherObj);
       // Log.d("demoObject",jsonObject.toString());
       // setJsonObject(jsonObject);

        Log.d("Weather",weatherHourlyList.toString());

//        gson=new Gson();
//        jsonObjectList=gson.toJson(weatherHourlyList.get(0));

        setJsonObject(weatherHourlyList.get(0));
        Log.d("demoList",jsonObjectList.toString());


        return weatherHourlyList;
    }

    public void setJsonObject(Weather hh)
    {
        gson=new Gson();
        jsonObjectList=gson.toJson(hh);



    }
    public String getJsonObject()
    {
        return jsonObjectList;
    }

}


