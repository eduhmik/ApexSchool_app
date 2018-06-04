package com.example.eduh_mik.schoolconnect2.models;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        try {
            Date date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.date);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            return simpleDateFormat.format(date);
        }
        catch(Exception e){
            e.printStackTrace();
            return "";
        }

    }

    public String getDay(){
        try{
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.date);
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");

            return sdf.format(date);
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }
}
