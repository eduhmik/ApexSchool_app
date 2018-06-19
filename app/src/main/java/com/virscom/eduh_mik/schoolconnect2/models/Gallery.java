package com.virscom.eduh_mik.schoolconnect2.models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Eduh_mik on 4/23/2018.
 */

public class Gallery {
    String descr, date, image;

    public Gallery(String descr, String image, String date){
        this.descr = descr;
        this.image = image;
        this.date = date;
    }

    public String getDescr() {
        return descr;
    }

    public String getImage() {
        return image;
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
