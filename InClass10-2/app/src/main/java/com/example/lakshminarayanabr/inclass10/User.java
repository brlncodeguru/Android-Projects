package com.example.lakshminarayanabr.inclass10;

/**
 * Created by lakshminarayanabr on 11/7/16.
 */

public class User {

    String emailAddress;
    String password;
    String fullName;

    public User(String emailAddress, String password, String fullName) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.fullName = fullName;
    }

    public User() {

    }

    @Override
    public String toString() {
        return "User{" +
                "emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
