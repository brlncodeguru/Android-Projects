package com.example.lakshminarayanabr.homework7;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by lakshminarayanabr on 11/19/16.
 */

public class User implements Parcelable {

    String fname,lname,email,password,userid,profilePicURL,gender;

        String jsonObject;

    protected User(Parcel in) {
        fname = in.readString();
        lname = in.readString();
        email = in.readString();
        password = in.readString();
        userid = in.readString();
        profilePicURL = in.readString();
        jsonObject = in.readString();
        gender=in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!fname.equals(user.fname)) return false;
        if (!lname.equals(user.lname)) return false;
        return email.equals(user.email);

    }

    @Override
    public int hashCode() {
        int result = fname.hashCode();
        result = 31 * result + lname.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userid='" + userid + '\'' +
                ", profilePicURL='" + profilePicURL + '\'' +
                ", gender='" + gender + '\'' +
                ", jsonObject='" + jsonObject + '\'' +
                '}';
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProfilePicURL() {
        return profilePicURL;
    }

    public void setProfilePicURL(String profilePicURL) {
        this.profilePicURL = profilePicURL;
    }

    public void setJsonObject(User hh)
    {
        Gson gson=new Gson();
        jsonObject=gson.toJson(hh);



    }
    public String getJsonObject()
    {
        return jsonObject;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fname);
        dest.writeString(lname);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(userid);
        dest.writeString(profilePicURL);
        dest.writeString(jsonObject);
        dest.writeString(gender);
    }
}
