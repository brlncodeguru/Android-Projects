package com.example.mohamed.hw06;

import java.util.ArrayList;

/**
 * Created by lakshminarayanabr on 10/20/16.
 */
public class WeatherDay {

    String city,countryname,date;

    ArrayList<Weather> weathers;

    public WeatherDay(String city, String countryname, String date, ArrayList<Weather> weathers) {
        this.city = city;
        this.countryname = countryname;
        this.date = date;
        this.weathers = weathers;
    }

    public WeatherDay() {
weathers=new ArrayList<>();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Weather> getWeathers() {
        return weathers;
    }

    public void setWeathers(ArrayList<Weather> weathers) {
        this.weathers = weathers;
    }

    @Override
    public String toString() {
        return "WeatherDay{" +
                "city='" + city + '\'' +
                ", countryname='" + countryname + '\'' +
                ", date='" + date + '\'' +
                ", weathers=" + weathers +
                '}';
    }
}
