package com.example.lakshminarayanabr.expensemanager;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;

/**
 * Created by lakshminarayanabr on 9/11/16.
 */
public class Expense implements Parcelable {

    String expenseName;

    String category;

    String expenseDate;

    String imgURI;

    Double amount;

    public Expense(String expenseName, String category, String expenseDate, String imgURI, Double amount) {
        this.expenseName = expenseName;
        this.category = category;
        this.expenseDate = expenseDate;
        this.imgURI = imgURI;
        this.amount = amount;
    }

    public Expense() {

    }

    protected Expense(Parcel in) {
        expenseName = in.readString();
        category = in.readString();
        expenseDate = in.readString();
        amount=in.readDouble();
        imgURI=in.readString();

    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(expenseName);
        dest.writeString(category);
        dest.writeString(expenseDate);
        dest.writeDouble(amount);
        dest.writeString(imgURI);
    }
}
