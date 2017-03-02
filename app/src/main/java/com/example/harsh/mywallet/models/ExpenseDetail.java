package com.example.harsh.mywallet.models;

/**
 * Created by Harsh on 16-01-2017.
 */

public class ExpenseDetail {

    private int day;
    private int month;
    private int year;
    private String category;
    private String details;
    private double amount;

    public ExpenseDetail(){

    }
    public ExpenseDetail(String category, String details, double amount){
        this.category = category;
        this.details = details;
        this.amount = amount;
    }

    public ExpenseDetail(int day, int month, int year,String category, String details, double amount){
        this.day= day;
        this.month= month;
        this.year = year;
        this.category = category;
        this.details = details;
        this.amount = amount;
    }

    public void setDay(int day){
        this.day = day;
    }

    public int getDay(){
        return day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}
