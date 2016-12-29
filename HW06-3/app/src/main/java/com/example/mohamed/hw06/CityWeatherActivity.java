package com.example.mohamed.hw06;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CityWeatherActivity extends AppCompatActivity {

    GetWeather downloadWeatherData;
    Weather weather;
    ArrayList<WeatherDay> weatherDays;
    String temperatureUnit;
    DatabaseDataManager dm;
    ProgressDialog pgDialog;
    Boolean onCreateCalled=false;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cityweathermenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.menu_SettingsForCityWeater)
        {
            Log.d("demo","CITY WEATHER ACTIVITY: "+"Settings");
            Intent intPrefs=new Intent(CityWeatherActivity.this,MyPrefsActivity.class);
            startActivity(intPrefs);
            return true;
        }
        else if (item.getItemId()==R.id.saveCity)
        {
            setSQLData();

            return true;
        }



        return false;


    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String preferenceTempUnit=preferences.getString(getResources().getString(R.string.Temperature),"F");
temperatureUnit=preferenceTempUnit;
        Log.d("Demo",preferenceTempUnit);
        if(onCreateCalled)
        {
            onCreateCalled=false;
        }
        else
        {
            LinearLayout linearLayout= (LinearLayout)findViewById(R.id.linLayout);
            linearLayout.removeAllViews();

            TextView textView=new TextView(this);
            LinearLayout.LayoutParams ltextViewParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(ltextViewParams);


            textView.setText("Daily Forecast for "+weatherDays.get(0).city+","+weatherDays.get(0).countryname);
            linearLayout.addView(textView);

            final RecyclerView daywiseRecycler = new RecyclerView(this);
            LinearLayoutManager linMgr = new LinearLayoutManager(this);
            linMgr.setOrientation(LinearLayoutManager.HORIZONTAL);

            RecyclerDateViewAdapter adapter=new RecyclerDateViewAdapter(this.weatherDays,daywiseRecycler,this);

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

        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String preferenceTempUnit=preferences.getString(getResources().getString(R.string.Temperature),"F");
        temperatureUnit=preferenceTempUnit;
        onCreateCalled=true;
        weather=new Weather();
        pgDialog=new ProgressDialog(this);
        pgDialog.setMessage("Loading  Data");


        if (getIntent()!=null)
        {
            Intent intent=new Intent(CityWeatherActivity.this,GetWeather.class);
            downloadWeatherData=new GetWeather();
            Log.d("here",getIntent().getExtras().getString(MainActivity.CITY_KEY));
            Log.d("here",getIntent().getExtras().getString(MainActivity.COUNTRY_KEY));

            String cityName=getIntent().getExtras().getString(MainActivity.CITY_KEY);
            String city=cityName.replace(" ","_");
            Log.d("Here",city.toString());
            downloadWeatherData.context=this;

            downloadWeatherData.setCityNmae(city);
            downloadWeatherData.setCountryInitial(getIntent().getExtras().getString(MainActivity.COUNTRY_KEY));
            Log.d("ini",getIntent().getExtras().getString(MainActivity.COUNTRY_KEY));
            downloadWeatherData.setKey("88aae0641bc2ada9d26cad18af69f5a5");
            downloadWeatherData.setUrl("http://api.openweathermap.org/data/2.5/forecast?q=");
            Log.d("yes",downloadWeatherData.getUrl());
            downloadWeatherData.execute(downloadWeatherData.getUrl());





        }


    }
    public void setSQLData()
    {
        dm=new DatabaseDataManager(this);
        String cityName,countryName,temperature,favorite;
        cityName=getIntent().getExtras().getString(MainActivity.CITY_KEY);
        countryName=getIntent().getExtras().getString(MainActivity.COUNTRY_KEY);
        temperature=weatherDays.get(0).weathers.get(0).getTemperature();
        favorite=weatherDays.get(0).weathers.get(0).getCondition();

        // dm.saveNote(new Note(list2.get(0).getTemperature(),list2.get(0).getTime()));
        StringBuilder sb=new StringBuilder();
        Calendar c=Calendar.getInstance();
        sb.append(c.get(Calendar.MONTH)+"/"+c.get(Calendar.DAY_OF_MONTH)+"/"+c.get(Calendar.YEAR));
        Note note=dm.getNote(cityName);
        if(note!=null)
        {
            note.setUpdatedDate(sb.toString());
            note.setTemperature(temperature);
            dm.updateNote(note);
            Toast.makeText(this,"Updated City",Toast.LENGTH_LONG).show();
        }
        else
        {
            dm.saveNote(new Note(cityName,countryName,temperature,favorite,sb.toString()));
            Toast.makeText(this,"Added City",Toast.LENGTH_LONG).show();
        }

        List<Note> notes=dm.getAllNotes();



        Log.d("demo500","INSIDE CITY WEATHER ACTIVITY SQL: "+notes.toString());
        Log.d("demo500","Variables"+cityName+countryName+temperature+favorite);

    }

}
