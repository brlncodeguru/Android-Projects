package com.example.welcome.baccalculator;

/**
 * Created by welcome on 01-Sep-16.
 */
public class Drink {

    int drinkSize;
    int alcoholPercent;

    public Drink(int drinkSize, int alcoholPercent) {
        this.drinkSize = drinkSize;
        this.alcoholPercent = alcoholPercent;
    }

    public int getDrinkSize() {
        return drinkSize;
    }

    public void setDrinkSize(int drinkSize) {
        this.drinkSize = drinkSize;
    }

    public int getAlcoholPercent() {
        return alcoholPercent;
    }

    public void setAlcoholPercent(int alcoholPercent) {
        this.alcoholPercent = alcoholPercent;
    }
}
