package com.example.harsh.mywallet.models;

/**
 * Created by Harsh on 25-01-2017.
 */

public class Lending {
    private int day;
    private int month;
    private int year;
    private String to;
    private double amount;
    private double paid;
    private int id;

    public Lending ( int id, int day,   int month, int year, String to, double amount, double paid)
   {
       this.amount=amount;
       this.day= day;
       this.month =  month;
       this.paid = paid;
       this.to =  to;
       this.year = year;
       this.id = id;

   }

    public Lending (  int day,   int month, int year, String to, double amount, double paid)
    {
        this.amount=amount;
        this.day= day;
        this.month =  month;
        this.paid = paid;
        this.to =  to;
        this.year = year;

    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public double getAmount() {
        return amount;
    }

    public double getPaid() {
        return paid;
    }

    public String getTo() {
        return to;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
