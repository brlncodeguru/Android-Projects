package com.example.mohamed.hw06;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by lakshminarayanabr on 10/20/16.
 */
public class Note {
    private long _id;
    private String cityName, countryName,temperature,favorite,updatedDate;

    public static Comparator<Note> favOrder =
            new Comparator<Note>() {
                @Override
                public int compare(Note n1, Note n2) {




                  return n2.getFavorite().compareTo(n1.getFavorite());
                }
            };


    @Override
    public String toString() {
        return "Note{" +
                "_id=" + _id +
                ", cityName='" + cityName + '\'' +
                ", countryName='" + countryName + '\'' +
                ", temperature='" + temperature + '\'' +
                ", favorite='" + favorite + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                '}';
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Note(String cityName, String countryName, String temperature, String favorite, String updatedDate)
    {
        this.cityName=cityName;
        this.countryName=countryName;
        this.temperature=temperature;
        this.favorite=favorite;
        this.updatedDate=updatedDate;


    }

    public Note ()
    {

    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

}
