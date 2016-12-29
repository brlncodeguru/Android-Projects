package com.example.mohamed.hw06;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editTextCity;
    EditText editTextCountry;

    public static String CITY_KEY="city";
    public static String COUNTRY_KEY="country";
    String temperatureUnit="";
    DatabaseDataManager dm;
    TextView txtError;
    RelativeLayout relativeLayout;
    Boolean onCreateCalled=false;

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String preferenceTempUnit=preferences.getString(getResources().getString(R.string.Temperature),"F");
        temperatureUnit=preferenceTempUnit;
        if (onCreateCalled)
        {
        onCreateCalled=false;
        }
        else
    {
    txtError= (TextView) findViewById(R.id.textViewError);
    dm=new DatabaseDataManager(this);
    List<Note> notes=dm.getAllNotes();
    if(notes.size()>0)
    {
        for(int i =0 ; i<notes.size(); i++){


            Note n1=notes.get(i);
            if(n1.getFavorite().equalsIgnoreCase("YES"))
            {
                notes.remove(i);
                notes.add(0,n1);
            }


        }
        txtError.setVisibility(View.INVISIBLE);
        relativeLayout= (RelativeLayout) findViewById(R.id.relLayout);
        Log.d("Demo", String.valueOf(relativeLayout.getChildCount()));
        if(relativeLayout.getChildCount()==5)
        {
            relativeLayout.removeViewAt(4);
        }


        final RecyclerView daywiseRecycler = new RecyclerView(this);
        LinearLayoutManager linMgr = new LinearLayoutManager(this);
        linMgr.setOrientation(LinearLayoutManager.VERTICAL);

        RecyclerFavoritesAdapter adapter=new RecyclerFavoritesAdapter(new ArrayList<Note>(notes),this,daywiseRecycler);


        daywiseRecycler.setHasFixedSize(true);
        daywiseRecycler.setAdapter(adapter);
        daywiseRecycler.setLayoutManager(linMgr);
        daywiseRecycler.setBackgroundColor(Color.WHITE);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW,R.id.buttonMainActivitySearch);
        params.topMargin=20;


        daywiseRecycler.setLayoutParams(params);
        daywiseRecycler.setLongClickable(true);

        relativeLayout.addView(daywiseRecycler);



    }
    else
    {
        txtError.setVisibility(View.VISIBLE);
    }


}


        Log.d("Demo",preferenceTempUnit);



        Log.d("Demo",preferenceTempUnit);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_Settings:
                Intent intPrefs=new Intent(MainActivity.this,MyPrefsActivity.class);
                startActivity(intPrefs);
                return true;
        }


        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainactivitymenu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onCreateCalled=true;
        editTextCity= (EditText) findViewById(R.id.editTextMainActivityCity);
        editTextCountry= (EditText) findViewById(R.id.editTextMainActivityCountry);
        editTextCity.setError("Enter the city");
        editTextCountry.setError("Enter the country");
        txtError= (TextView) findViewById(R.id.textViewError);


        findViewById(R.id.buttonMainActivitySearch).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isconnectedtointernet()) {
                    Log.d("demo","Connected");

                    if (editTextCity.getText().toString().length() > 0 && editTextCountry.getText().toString().length() > 0) {
                        Intent intent = new Intent(MainActivity.this, CityWeatherActivity.class);
                        String city = editTextCity.getText().toString();
                        String[] cityStrinArray = editTextCity.getText().toString().split(" ");
                        StringBuilder sb = new StringBuilder();
                        Log.d("demo", city.toString());
                        for (int x = 0; x < cityStrinArray.length; x++) {
                            String subString = cityStrinArray[x];
                            String output = subString.substring(0, 1).toUpperCase() + subString.substring(1);
                            if (x < cityStrinArray.length - 1)
                                sb.append(output).append(" ");
                            else if (x == cityStrinArray.length - 1)
                                sb.append(output);
                            Log.d("demo", cityStrinArray[x]);

                        }
                        String country = editTextCountry.getText().toString().toUpperCase();
                        Log.d("demo", country.toString());
                        Log.d("demo", sb.toString());

                        intent.putExtra(CITY_KEY, sb.toString());
                        intent.putExtra(COUNTRY_KEY, country);
                        startActivity(intent);

                    } else {
                        Toast.makeText(MainActivity.this, "Please enter a city and a country.", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }

        });


        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String preferenceTempUnit=preferences.getString(getResources().getString(R.string.Temperature),"F");
        temperatureUnit=preferenceTempUnit;

        dm=new DatabaseDataManager(this);
        List<Note> notes=dm.getAllNotes();
        if(notes.size()>0)
        {
            for(int i =0 ; i<notes.size(); i++){


                Note n1=notes.get(i);
                if(n1.getFavorite().equalsIgnoreCase("YES"))
                {
                    notes.remove(i);
                    notes.add(0,n1);
                }


            }
            txtError.setVisibility(View.INVISIBLE);
            relativeLayout= (RelativeLayout) findViewById(R.id.relLayout);
            Log.d("Demo", String.valueOf(relativeLayout.getChildCount()));
            if(relativeLayout.getChildCount()==5)
            {
                relativeLayout.removeViewAt(4);
            }


            final RecyclerView daywiseRecycler = new RecyclerView(this);
            LinearLayoutManager linMgr = new LinearLayoutManager(this);
            linMgr.setOrientation(LinearLayoutManager.VERTICAL);

            RecyclerFavoritesAdapter adapter=new RecyclerFavoritesAdapter(new ArrayList<Note>(notes),this,daywiseRecycler);
            adapter.notifyDataSetChanged();

            daywiseRecycler.setHasFixedSize(true);
            daywiseRecycler.setAdapter(adapter);
            daywiseRecycler.setLayoutManager(linMgr);
            daywiseRecycler.setBackgroundColor(Color.WHITE);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW,R.id.buttonMainActivitySearch);
            params.topMargin=20;


            daywiseRecycler.setLayoutParams(params);
            daywiseRecycler.setLongClickable(true);

            relativeLayout.addView(daywiseRecycler);



        }
        else
        {
            txtError.setVisibility(View.VISIBLE);
        }


        Log.d("Demo",preferenceTempUnit);


    }

    private boolean isconnectedtointernet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null & networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
