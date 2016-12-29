package com.example.mohamed.hw06;

import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * Created by Mohamed on 10/16/2016.
 */
public class Weather implements Serializable {
    String time, weatherIcon, temperature, condition, pressure, humidity,windSspeed,windDirection,date;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) throws ParseException {

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");

        SimpleDateFormat dateformatter=new SimpleDateFormat("MM/dd/yyyy");
        Log.d("date",fmt.parse(time).toString());

        Date date=null;
        date=fmt.parse(time);


        this.date=dateformatter.format(date);

        SimpleDateFormat dateformatter1=new SimpleDateFormat("hh:mm a");

        this.time=dateformatter1.format(date);


    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindSspeed() {
        return windSspeed;
    }

    public void setWindSspeed(String windSspeed) {
        this.windSspeed = windSspeed;
    }

    public String toString()
    {
        return "Time: "+ time+" Weather Icon: "+ weatherIcon+" Tempereture: "+ temperature+"  Condition: "+ condition+" Pressure: "+ pressure+" Humidity: "+ humidity+" Wind Speed: "+windSspeed+" Wind Direction: "+windDirection+"Weather Icon:"+weatherIcon+"   Date:"+date;
    }
}
