package com.example.lakshminarayanabr.hw05;
//package com.example.mohamed.hw5;//

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Res","Resume MainActivity");
        favList=new ArrayList<>();
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

        for (int x=0;x<strings.size();x++) {

            Weather weather = gson.fromJson(strings.get(x), Weather.class);
            favList.add(weather);

            //converting from json to java object

        }

        adapter=new FavouritesListAdapter(this,R.layout.itemfavlayout,favList);
        lvFav.setAdapter(adapter);
        if(favList.size()>0)
        {
            lvFav.setVisibility(View.VISIBLE);
            lvErrorMessage.setVisibility(View.INVISIBLE);
        }
        else
        {
            lvFav.setVisibility(View.INVISIBLE);
            lvErrorMessage.setVisibility(View.VISIBLE);
        }



    }

    EditText editCity,editState;
    Button btnSubmit;
    ListView lvFav;
    TextView lvErrorMessage;
    Gson gson;
    Weather weather;
    ArrayList<Weather> favList;

    public static  String CITY_NAME="CITY_NAME";
    public static  String STATE="STATE";
    GetWeatherData getWeatherData;
    FavouritesListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        favList=new ArrayList<>();
        getWeatherData=new GetWeatherData();

        editCity=(EditText)findViewById(R.id.editTextCity);
        editState=(EditText)findViewById(R.id.editTextState);
        lvFav=(ListView)findViewById(R.id.listViewFavourites);
        btnSubmit=(Button)findViewById(R.id.buttonSubmit);
        editCity.setError("Enter the City");
        editCity.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        setCapitalizeTextWatcher(editCity);
        editState.setError("Enter the State");
        editCity.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        setCapitalizeTextWatcher(editState);
        lvErrorMessage=(TextView)findViewById(R.id.textViewNoFavourites);


        lvFav.setVisibility(View.INVISIBLE);
        lvErrorMessage.setVisibility(View.VISIBLE);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editCity.getText()!=null&& editState.getText()!=null&&editCity.getText().toString().length()>0&&editState.getText().toString().length()>0)
                {
                    Intent intCityWeather=new Intent(MainActivity.this,CityWeatherActivity.class);
                    Log.d("Weather ******",editCity.getText().toString());
                    Log.d("Weather ******",editState.getText().toString());

                    String[] cityStrings=editCity.getText().toString().split(" ");
                    StringBuilder sb=new StringBuilder();

                    for(int i=0;i<cityStrings.length;i++)
                    {
                        String subString=cityStrings[i];
                        String output=subString.substring(0,1).toUpperCase()+subString.substring(1);//what does it do
                        if(i<cityStrings.length-1)
                       sb.append(output).append(" ");
                        else if(i==cityStrings.length-1)
                            sb.append(output);

                    }

                    String state=editState.getText().toString().toUpperCase();


                    intCityWeather.putExtra(CITY_NAME,sb.toString().replace("_"," ").trim());
                    intCityWeather.putExtra(STATE,state);
                    Log.d("Weather ******",intCityWeather.getExtras().getString(MainActivity.CITY_NAME));
                    Log.d("Weather ******",intCityWeather.getExtras().getString(MainActivity.STATE));
                    startActivity(intCityWeather);


                }
                else
                {
                    Toast.makeText(MainActivity.this,"Enter the City and State",Toast.LENGTH_LONG).show();
                }
            }
        });

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

        for (int x=0;x<strings.size();x++) {

            Weather weather = gson.fromJson(strings.get(x), Weather.class);
            favList.add(weather);

            //converting from json to java object

        }

        adapter=new FavouritesListAdapter(this,R.layout.itemfavlayout,favList);
        lvFav.setAdapter(adapter);
        if(favList.size()>0)
        {
            lvFav.setVisibility(View.VISIBLE);
            lvErrorMessage.setVisibility(View.INVISIBLE);
        }
        else
        {
            lvFav.setVisibility(View.INVISIBLE);
            lvErrorMessage.setVisibility(View.VISIBLE);
        }


        lvFav.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                favList.remove(position);

                if(favList.size()>0)
                {
                    lvFav.setVisibility(View.VISIBLE);
                    lvErrorMessage.setVisibility(View.INVISIBLE);
                }
                else
                {
                    lvFav.setVisibility(View.INVISIBLE);
                    lvErrorMessage.setVisibility(View.VISIBLE);
                }
                adapter.setNotifyOnChange(true);
                adapter.notifyDataSetChanged();


                ArrayList<String> stringFavList=new ArrayList<String>();
                Gson gson=new Gson();
                for(int i=0;i<favList.size();i++)
                {

                    Weather weather=favList.get(i);
                    getWeatherData.setJsonObject(weather);


                    stringFavList.add(getWeatherData.getJsonObject());
                }
                String convertedJSONstring=gson.toJson(stringFavList);
                storePrefrences(convertedJSONstring);
                Toast.makeText(MainActivity.this,"Record has been deleted from favorites",Toast.LENGTH_LONG).show();
                return false;
            }
        });





    }

    public static void setCapitalizeTextWatcher(final EditText editText) {
        final TextWatcher textWatcher = new TextWatcher() {

            int mStart = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }



            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mStart = start + count;
            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                String capitalizedText;
                if (input.length() < 1)
                    capitalizedText = input;
                else
                    capitalizedText = input.substring(0, 1).toUpperCase() + input.substring(1);
                if (!capitalizedText.equals(editText.getText().toString())) {
                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            editText.setSelection(mStart);
                            editText.removeTextChangedListener(this);
                        }
                    });
                    editText.setText(capitalizedText);
                }
            }
        };

        editText.addTextChangedListener(textWatcher);



    }
    public void storePrefrences(String converted )
    {
        SharedPreferences sharedPreferences=getSharedPreferences("FIRST_CITY",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Favorites",converted);
        editor.commit();

    }

    public  String getSharedPreferences()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("FIRST_CITY",MODE_PRIVATE);
        String edited=sharedPreferences.getString("Favorites","No Favorites Available");
        return edited;
    }



}
