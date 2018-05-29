package com.example.eduh_mik.schoolconnect2.models;

public class Fees {
    String section, Amount, paid, balance, term, year;

    public Fees(String section, String Amount, String paid, String balance, String term, String year){
        this.section = section;
        this.Amount = Amount;
        this.paid = paid;
        this.balance = balance;
        this.term = term;
        this.year = year;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        this.Amount = amount;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
