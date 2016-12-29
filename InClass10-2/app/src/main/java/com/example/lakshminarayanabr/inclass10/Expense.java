package com.example.lakshminarayanabr.inclass10;

/**
 * Created by lakshminarayanabr on 11/7/16.
 */

public class Expense {
    String name,category,amount,date,userid,expenseID;

    public String getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(String expenseID) {
        this.expenseID = expenseID;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Expense(String name, String category, String amount, String date, String userid) {
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.userid=userid;

    }

    public Expense() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expense expense = (Expense) o;

        if (!name.equals(expense.name)) return false;
        if (!category.equals(expense.category)) return false;
        if (!amount.equals(expense.amount)) return false;
        if (!date.equals(expense.date)) return false;
        if (!userid.equals(expense.userid)) return false;
        return expenseID.equals(expense.expenseID);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + amount.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + userid.hashCode();
        result = 31 * result + expenseID.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", amount='" + amount + '\'' +
                ", date='" + date + '\'' +
                ", userid='" + userid + '\'' +
                ", expenseID='" + expenseID + '\'' +
                '}';
    }

    public void setDate(String date) {
        this.date = date;
    }


}
