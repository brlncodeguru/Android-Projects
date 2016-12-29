package com.example.mohamed.hw06;

/**
 * Created by lakshminarayanabr on 10/20/16.
 */
public class Favorites {

    String city,country,temperature,favorites,updatedDate;

    @Override
    public String toString() {
        return "Favorites{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", temperature='" + temperature + '\'' +
                ", favorites='" + favorites + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                '}';
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getFavorites() {
        return favorites;
    }

    public void setFavorites(String favorites) {
        this.favorites = favorites;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Favorites(String city, String country, String temperature, String favorites, String updatedDate) {

        this.city = city;
        this.country = country;
        this.temperature = temperature;
        this.favorites = favorites;
        this.updatedDate = updatedDate;
    }

    public Favorites() {

    }
}
