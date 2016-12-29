package com.example.lakshminarayanabr.hw05;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class CityWeatherActivity extends AppCompatActivity {

    GetWeatherData downloadWeatherData;

    TextView txtCurrentlocation,txtLocation;
    ListView listVW;
    GetWeatherData getWeatherData;
    ProgressDialog pgDialog;
    ArrayList<Weather> weatherList;
    ArrayList<String> favoriteList;


    WeatherHourlyAdapter adapter;

    public static String LIST_WEATHER="LIST";
    public static String POS="POS";
    public static String CITY="CITY";
    public static String STATE="STATE";
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menufav, menu);
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        pgDialog=new ProgressDialog(this);
        pgDialog.setMessage("Loading Hourly Data");

        favoriteList=new ArrayList<>();

        txtCurrentlocation=(TextView)findViewById(R.id.textViewCurrentLoacatton);
        txtLocation=(TextView)findViewById(R.id.textViewLocation);
        listVW=(ListView)findViewById(R.id.listViewWeatherHourly);


        if(getIntent().getExtras()!=null)
        {
            downloadWeatherData=new GetWeatherData();


            Log.d("Weather ******",getIntent().getExtras().getString(MainActivity.CITY_NAME));
            Log.d("Weather ******",getIntent().getExtras().getString(MainActivity.STATE));

            String city_name = null;

            if(getIntent().getExtras().getString(MainActivity.CITY_NAME)!=null)
            {
                city_name=getIntent().getExtras().getString(MainActivity.CITY_NAME);

                String city=city_name.replaceAll(" ","_");
                Log.d("Weather ******",city);






            downloadWeatherData.setCity_name(city);
            }
            if(getIntent().getExtras().getString(MainActivity.STATE)!=null)
            {
            downloadWeatherData.setState(getIntent().getExtras().getString(MainActivity.STATE));
            }

            downloadWeatherData.baseURL="http://api.wunderground.com/api/";
            downloadWeatherData.api_key="6053c442370e0348";
            downloadWeatherData.activity=this;
            Log.d("Weather ******",downloadWeatherData.getURL());

            downloadWeatherData.execute(downloadWeatherData.baseURL);

            txtLocation.setText(city_name);

            final String state=getIntent().getExtras().getString(MainActivity.STATE);
            final String city;
            city=city_name;
            listVW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    Intent intDetails=new Intent(CityWeatherActivity.this,DetailsActivity.class);
                    intDetails.putParcelableArrayListExtra(LIST_WEATHER,weatherList);
                    intDetails.putExtra(POS,position);
                    intDetails.putExtra(CITY,city);
                    intDetails.putExtra(STATE,state);

                    startActivity(intDetails);


                }
            });




        }





    }


    public void gotoPrevActivity()
    {
        Toast.makeText(this,"No cities match your search query",Toast.LENGTH_LONG).show();
        finish();
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

    public void addToFavorite()
    {
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

        favoriteList=strings;
        Log.d("DDDDD",strings.toString());


        if(strings.size()==0)
        {
            Weather weatherObj=new Weather();
            weatherObj.setCityName(weatherList.get(0).getCityName());
            weatherObj.setDate(weatherList.get(0).getDate());
            weatherObj.setStateNmae(weatherList.get(0).getStateNmae());
            weatherObj.setTemperature(weatherList.get(0).getTemperature());
            downloadWeatherData.setJsonObject(weatherObj);

            favoriteList.add(downloadWeatherData.getJsonObject());
            Toast.makeText(CityWeatherActivity.this,"Added the Favourites",Toast.LENGTH_LONG).show();
        }
        else
        {
            for (int x=0;x<strings.size();x++)
            {

                Weather weather=gson.fromJson(strings.get(x),Weather.class);
                //converting from json to java object

                String cityname=getIntent().getExtras().getString(MainActivity.CITY_NAME).replaceAll("_"," ");



                if(weather.getCityName().equalsIgnoreCase(cityname.trim()))
                {
                    Weather weatherObj=new Weather();
                    weatherObj.setCityName(weatherList.get(0).getCityName());
                    weatherObj.setDate(weatherList.get(0).getDate());
                    weatherObj.setStateNmae(weatherList.get(0).getStateNmae());
                    weatherObj.setTemperature(weatherList.get(0).getTemperature());
                    downloadWeatherData.setJsonObject(weatherObj);
                    favoriteList.remove(x);


                        favoriteList.add(downloadWeatherData.getJsonObject());


                    Toast.makeText(CityWeatherActivity.this,"Updated the Favourites",Toast.LENGTH_LONG).show();
                    Log.d("yyu",favoriteList.toString());
                    Log.d("lll","This is the size of **favoriteList***"+ String.valueOf(favoriteList.size()));

                   // Log.d("xxxx", String.valueOf(favoriteList.size()));


                    String converted=gson.toJson(favoriteList);
                    Log.d("lll",converted.toString());
                    storePrefrences(converted);
                    return;

                }


            }
            Weather weatherObj=new Weather();
            weatherObj.setCityName(weatherList.get(0).getCityName());
            weatherObj.setDate(weatherList.get(0).getDate());
            weatherObj.setStateNmae(weatherList.get(0).getStateNmae());
            weatherObj.setTemperature(weatherList.get(0).getTemperature());
            downloadWeatherData.setJsonObject(weatherObj);

            favoriteList.add(downloadWeatherData.getJsonObject());
            Toast.makeText(CityWeatherActivity.this,"Added the Favourites",Toast.LENGTH_LONG).show();

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
