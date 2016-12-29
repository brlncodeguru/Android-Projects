package com.example.lakshminarayanabr.hw05;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

public class DetailsActivity extends AppCompatActivity {

    TextView txtLocation,txttemp,txtClimateType,txtmaxTemp,txtminTemp,txtfeelslike,txtHumidity,txtDewpoint,txtPressure,txtClouds,txtWind;
    ImageView image;
    ArrayList<Weather> weatherArrayList;
    int pos;
    ArrayList<String> favoriteList;
    GetWeatherData downloadWeatherData;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menufav, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        downloadWeatherData=new GetWeatherData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        favoriteList=new ArrayList<>();
        image=(ImageView)findViewById(R.id.imageViewIcon);

        txtLocation=(TextView)findViewById(R.id.textViewLocation);
        txttemp=(TextView)findViewById(R.id.txtTemp);
        txtClimateType=(TextView)findViewById(R.id.textViewClimate);
        txtmaxTemp=(TextView)findViewById(R.id.textViewMax);
        txtminTemp=(TextView)findViewById(R.id.textViewMin);

        txtfeelslike=(TextView)findViewById(R.id.textViewFeelsLike);
        txtHumidity=(TextView)findViewById(R.id.textViewHumidity);
        txtDewpoint=(TextView)findViewById(R.id.textViewDewpoint);
        txtPressure=(TextView)findViewById(R.id.textViewPressure);
        txtClouds=(TextView)findViewById(R.id.textViewClouds);
        txtWind=(TextView)findViewById(R.id.textViewWind);

        if(getIntent().getExtras().getParcelableArrayList(CityWeatherActivity.LIST_WEATHER)!=null)
        {
            weatherArrayList=getIntent().getExtras().getParcelableArrayList(CityWeatherActivity.LIST_WEATHER);

        }
        if(getIntent().getExtras().getInt(CityWeatherActivity.POS)>=0)
        {
            pos=getIntent().getExtras().getInt(CityWeatherActivity.POS);

        }
        Weather weatherObj=weatherArrayList.get(pos);
        if(getIntent().getExtras().getString(CityWeatherActivity.CITY)!=null)
        {
            if(getIntent().getExtras().getString(CityWeatherActivity.STATE)!=null)
            {
                txtLocation.setText("Location : "+getIntent().getExtras().getString(CityWeatherActivity.CITY)+","+getIntent().getExtras().getString(CityWeatherActivity.STATE)+ "("+weatherObj.time+")");
            }

        }


        txttemp.setText(weatherObj.temperature+" \u2109");
        txtClimateType.setText(weatherObj.climateType);

        Picasso.with(this).load(weatherObj.icon_URL).into(image);



        txtfeelslike.setText("Feels Like :"+weatherObj.feelsLike+ " Fahrenheit");
        txtHumidity.setText("Humidity :"+weatherObj.humidity + "%");
        txtDewpoint.setText("DewPoint :" +weatherObj.dewpoint + " Fahrenheit");
        txtPressure.setText("Pressure :"+weatherObj.pressure + " hPa");
        txtClouds.setText("Clouds :"+weatherObj.clouds);
        txtWind.setText("Wind :"+weatherObj.windSpeed + "mph," + weatherObj.windDirection);


        int maxTemp=0;
        int minTemp=0;
        Calendar c=Calendar.getInstance();
        int day= c.get(Calendar.DAY_OF_MONTH);
        for(Weather obj : weatherArrayList)
        {

            if(obj.mDay==day)
            {
                if(maxTemp==0||Integer.parseInt(obj.temperature)>maxTemp)
                {
                    maxTemp=Integer.parseInt(obj.temperature);

                }
                if(minTemp==0||Integer.parseInt(obj.temperature)<minTemp)
                {
                    minTemp=Integer.parseInt(obj.temperature);
                }
            }


        }
        txtmaxTemp.setText("Max Temperature :"+String.valueOf(maxTemp));
        txtminTemp.setText("Min Temperature :"+String.valueOf(minTemp));







    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getItemId()==R.id.menu_addfav)
        {
            Log.d("Weather","Added to Favourites");
            addToFavorite();
        }
        return true;
    }

    private void addToFavorite() {
        Gson gson=new Gson();
        String convertedAgain=getSharedPreferences();
        ArrayList<String> strings;
        if(convertedAgain=="No Favorites Available")
        {
            strings=new ArrayList<>();
        }
        else
        {
            strings= (ArrayList<String>) gson.fromJson(convertedAgain,new TypeToken<ArrayList<String>>(){

            }.getType());

        }
        Log.d("llll",convertedAgain);
        favoriteList=strings;

        if(strings.size()==0)
        {
            Weather weatherObj=new Weather();
            weatherObj.setCityName(weatherArrayList.get(0).getCityName());
            weatherObj.setDate(weatherArrayList.get(0).getDate());
            weatherObj.setStateNmae(weatherArrayList.get(0).getStateNmae());
            weatherObj.setTemperature(weatherArrayList.get(0).getTemperature());
            downloadWeatherData.setJsonObject(weatherObj);

            favoriteList.add(downloadWeatherData.getJsonObject());
            Toast.makeText(DetailsActivity.this,"Added the Favourites",Toast.LENGTH_LONG).show();
        }
        else
        {
            for (int x=0;x<strings.size();x++)
            {

                Weather weather=gson.fromJson(strings.get(x),Weather.class);
                if(weather.getCityName().equalsIgnoreCase(getIntent().getExtras().getString(CityWeatherActivity.CITY)))
                {
                    Weather weatherObj=new Weather();
                    weatherObj.setCityName(weatherArrayList.get(0).getCityName());
                    weatherObj.setDate(weatherArrayList.get(0).getDate());
                    weatherObj.setStateNmae(weatherArrayList.get(0).getStateNmae());
                    weatherObj.setTemperature(weatherArrayList.get(0).getTemperature());
                    downloadWeatherData.setJsonObject(weatherObj);
                    favoriteList.remove(x);
                    favoriteList.add(downloadWeatherData.getJsonObject());

                    Toast.makeText(DetailsActivity.this,"Updated the Favourites",Toast.LENGTH_LONG).show();
                    Log.d("yyu",favoriteList.toString());


                    String converted=gson.toJson(favoriteList);
                    Log.d("lll",converted.toString());
                    storePrefrences(converted);


                    return;
                }





            }
            Weather weatherObj=new Weather();
            weatherObj.setCityName(weatherArrayList.get(0).getCityName());
            weatherObj.setDate(weatherArrayList.get(0).getDate());
            weatherObj.setStateNmae(weatherArrayList.get(0).getStateNmae());
            weatherObj.setTemperature(weatherArrayList.get(0).getTemperature());
            downloadWeatherData.setJsonObject(weatherObj);

            favoriteList.add(downloadWeatherData.getJsonObject());
            Toast.makeText(DetailsActivity.this,"Added the Favourites",Toast.LENGTH_LONG).show();



        }





        Log.d("yyu",favoriteList.toString());


        String converted=gson.toJson(favoriteList);
        Log.d("lll",converted.toString());
        storePrefrences(converted);



    }

    public void storePrefrences(String converted )
    {
        SharedPreferences sharedPreferences=getSharedPreferences("FIRST_CITY",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Favorites",converted);
        editor.commit();

    }

    public String getSharedPreferences()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("FIRST_CITY",MODE_PRIVATE);
        String edited=sharedPreferences.getString("Favorites","No Favorites Available");
        return edited;
    }
}
