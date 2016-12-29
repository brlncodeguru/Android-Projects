package com.example.lakshminarayanabr.hw05;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by lakshminarayanabr on 10/7/16.
 */
public class Weather implements Parcelable {

    String time,temperature,dewpoint,clouds,icon_URL,windSpeed,windDirection,climateType,humidity,feelsLike,maximumTemp,minimumTemp,pressure;

    String cityName, stateNmae;

    String date;
    int mDay;
    protected Weather(Parcel in) {
        time = in.readString();
        temperature = in.readString();
        dewpoint = in.readString();
        clouds = in.readString();
        icon_URL = in.readString();
        windSpeed = in.readString();
        windDirection = in.readString();
        climateType = in.readString();
        humidity = in.readString();
        feelsLike = in.readString();
        maximumTemp = in.readString();
        minimumTemp = in.readString();
        pressure = in.readString();
        mDay=in.readInt();
        cityName=in.readString();
        stateNmae=in.readString();
        date=in.readString();
    }

    public static final Creator<Weather> CREATOR = new Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

    @Override
    public String toString() {
        return "Weather{" +
                "time='" + time + '\'' +
                ", temperature='" + temperature + '\'' +
                ", dewpoint='" + dewpoint + '\'' +
                ", clouds='" + clouds + '\'' +
                ", icon_URL='" + icon_URL + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", windDirection='" + windDirection + '\'' +
                ", climateType='" + climateType + '\'' +
                ", humidity='" + humidity + '\'' +
                ", feelsLike='" + feelsLike + '\'' +
                ", maximumTemp='" + maximumTemp + '\'' +
                ", minimumTemp='" + minimumTemp + '\'' +
                ", pressure='" + pressure + '\'' +
                '}';
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setDewpoint(String dewpoint) {
        this.dewpoint = dewpoint;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public void setIcon_URL(String icon_URL) {
        this.icon_URL = icon_URL;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public void setClimateType(String climateType) {
        this.climateType = climateType;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    public void setMaximumTemp(String maximumTemp) {
        this.maximumTemp = maximumTemp;
    }

    public void setMinimumTemp(String minimumTemp) {
        this.minimumTemp = minimumTemp;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getTime() {

        return time;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getDewpoint() {
        return dewpoint;
    }

    public String getClouds() {
        return clouds;
    }

    public String getIcon_URL() {
        return icon_URL;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public String getClimateType() {
        return climateType;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public String getMaximumTemp() {
        return maximumTemp;
    }

    public String getMinimumTemp() {
        return minimumTemp;
    }

    public String getPressure() {
        return pressure;
    }

    public Weather() {
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setmDay(int mDay) {
        this.mDay = mDay;
    }

    public void setStateNmae(String stateNmae) {
        this.stateNmae = stateNmae;
    }

    public String getDate() {

        return date;
    }

    public String getCityName() {
        return cityName;
    }

    public String getStateNmae() {
        return stateNmae;
    }

    public int getmDay() {
        return mDay;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(time);
        dest.writeString(temperature);
        dest.writeString(dewpoint);
        dest.writeString(clouds);
        dest.writeString(icon_URL);
        dest.writeString(windSpeed);
        dest.writeString(windDirection);
        dest.writeString(climateType);
        dest.writeString(humidity);
        dest.writeString(feelsLike);
        dest.writeString(maximumTemp);
        dest.writeString(minimumTemp);
        dest.writeString(pressure);
        dest.writeInt(mDay);
        dest.writeString(cityName);
        dest.writeString(stateNmae);
        dest.writeString(date);;

    }
}
