package com.example.mohamed.hw06;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Mohamed on 10/16/2016.
 */
public class GetWeather extends AsyncTask<String, Void,ArrayList<Weather>> {
    String cityNmae, countryInitial,url,key;

    CityWeatherActivity context;


    @Override
    protected ArrayList<Weather> doInBackground(String... params) {

        URL url= null;
        try {
            url = new URL(params[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int status=connection.getResponseCode();
            if (status==HttpURLConnection.HTTP_OK)

            {
                InputStream in =connection.getInputStream();
                return WeatherParser.WeatherPullParser.parseWeather(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            Log.d("ex","ParseException"+e.getMessage());
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Weather> weathers) {
        if (weathers!=null) {
            context.weatherDays=getWeatherDayFromWeatherObjs(weathers);

            LinearLayout linearLayout= (LinearLayout) context.findViewById(R.id.linLayout);

            TextView textView=new TextView(context);
            LinearLayout.LayoutParams ltextViewParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(ltextViewParams);


            textView.setText("Daily Forecast for "+cityNmae+","+countryInitial);
            linearLayout.addView(textView);

            final RecyclerView daywiseRecycler = new RecyclerView(context);
            LinearLayoutManager linMgr = new LinearLayoutManager(context);
            linMgr.setOrientation(LinearLayoutManager.HORIZONTAL);

            RecyclerDateViewAdapter adapter=new RecyclerDateViewAdapter(context.weatherDays,daywiseRecycler,context);

            daywiseRecycler.setHasFixedSize(true);
            daywiseRecycler.setAdapter(adapter);
            daywiseRecycler.setLayoutManager(linMgr);
            daywiseRecycler.setBackgroundColor(Color.LTGRAY);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            daywiseRecycler.setLayoutParams(params);
            daywiseRecycler.setLongClickable(true);

            linearLayout.addView(daywiseRecycler);

            final int pos = 0;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    daywiseRecycler.findViewHolderForAdapterPosition(pos).itemView.performClick();
                }
            },1);



            Log.d("res", weathers.toString());

            Log.d("resc",String.valueOf(weathers.size()));
            Log.d("res",getWeatherDayFromWeatherObjs(weathers).toString());
            context.pgDialog.dismiss();

        }
        super.onPostExecute(weathers);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       context.pgDialog.show();



    }

    public String getCityNmae() {
        return cityNmae;
    }

    public void setCityNmae(String cityNmae) {
        this.cityNmae = cityNmae;
    }

    public String getCountryInitial() {
        return countryInitial;
    }


    public void setCountryInitial(String countryInitial) {
        this.countryInitial = countryInitial;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setUrl(String url) {
        this.url = url+cityNmae+","+countryInitial+"&mode=xml&appid="+key;
    }
    public String getUrl()
    {
        return url;
    }

    public ArrayList<WeatherDay> getWeatherDayFromWeatherObjs(ArrayList<Weather> weathers)
    {
        ArrayList<WeatherDay> weatherDays=new ArrayList<>();

        for(String s: getDatesFromWeatherObjs(weathers))
        {
            WeatherDay weatherDay=new WeatherDay();
            weatherDay.city=this.cityNmae;
            weatherDay.countryname=this.countryInitial;
            weatherDay.date=s;
            for (Weather weather :weathers)
            {
                if(weather.date.equals(s))
                {
                    weatherDay.weathers.add(weather);
                }
            }
            weatherDays.add(weatherDay);
        }





        return weatherDays;
    }

    public ArrayList<String> getDatesFromWeatherObjs(ArrayList<Weather> weathers)
    {
        ArrayList<String> dates=new ArrayList<>();

        for (Weather weather : weathers)
        {

            if (!dates.contains(weather.date))
            {
                dates.add(weather.date);

            }



        }


        return dates;
    }
}
