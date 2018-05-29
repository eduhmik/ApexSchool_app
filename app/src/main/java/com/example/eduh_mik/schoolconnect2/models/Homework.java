package com.example.eduh_mik.schoolconnect2.models;

public class Homework {
    private String sub_id, descr, date, student_id;

    public Homework(String sub_id, String descr, String date, String student_id){
        this.sub_id = sub_id;
        this.descr = descr;
        this.date = date;
        this.student_id = student_id;

    }

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }
}
