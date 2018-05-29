package com.example.eduh_mik.schoolconnect2.models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Eduh_mik on 4/21/2018.
 */

public class Activities {
    String name, descr, date;

    public Activities(String name, String descr, String date) {

        this.name = name;
        this.descr = descr;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDescr() {
        return descr;
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
}
