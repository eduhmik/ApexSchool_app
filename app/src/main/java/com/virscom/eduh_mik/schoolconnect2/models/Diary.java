package com.virscom.eduh_mik.schoolconnect2.models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Eduh_mik on 4/20/2018.
 */

public class Diary {
    private String d_id;
    private String name;
    private String description;
    private String date;
    private String student_id;
    private String first_name;
    private String last_name;


    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getClass_id() {
        return class_id;
    }

    private String class_id;

    public String getD_id() {
        return d_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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
}
