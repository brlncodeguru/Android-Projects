package com.example.lakshminarayanabr.inclass03;

import java.io.Serializable;

/**
 * Created by lakshminarayanabr on 9/6/16.
 */
public class Student implements Serializable {
    String name;
    String emailAddress;
    String department;
    String accountState;
    String mood;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAccountState() {
        return accountState;
    }

    public void setAccountState(String accountState) {
        this.accountState = accountState;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public Student(String name, String emailAddress, String department, String accountState, String mood) {

        this.name = name;
        this.emailAddress = emailAddress;
        this.department = department;
        this.accountState = accountState;
        this.mood = mood;
    }

    public Student() {
    }
}
