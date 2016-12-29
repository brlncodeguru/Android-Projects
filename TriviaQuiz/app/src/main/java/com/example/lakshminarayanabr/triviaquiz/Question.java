package com.example.lakshminarayanabr.triviaquiz;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by lakshminarayanabr on 9/23/16.
 */
public class Question implements Parcelable{

    ArrayList<String> choices;
    int answerChoice;
    String questionText;
    String imageURL;
    int id;

    public Question(ArrayList<String> choices, int answerChoice, String questionText, String image,int id) {
        this.choices = choices;
        this.answerChoice = answerChoice;
        this.questionText = questionText;
        this.imageURL = image;
        this.id=id;
    }

    public Question() {
        choices=new ArrayList<String>();
    }


    protected Question(Parcel in) {
        choices = in.createStringArrayList();
        answerChoice = in.readInt();
        questionText = in.readString();
        imageURL = in.readString();
        id = in.readInt();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(choices);
        dest.writeInt(answerChoice);
        dest.writeString(questionText);
        dest.writeString(imageURL);
        dest.writeInt(id);
    }
}
