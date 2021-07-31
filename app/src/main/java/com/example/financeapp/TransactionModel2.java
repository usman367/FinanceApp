package com.example.financeapp;

//Created this in java becuase the kotlin class is not working in this java project
public class TransactionModel2 {
    int id;
    String amount;
    String description;
    String date;
    String method;


    TransactionModel2(int id, String amount, String description, String date, String method){
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.method = method;
    }

    public String getDate() {
        return date;
    }

    public String getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

}
