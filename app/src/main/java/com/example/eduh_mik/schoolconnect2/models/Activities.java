package com.example.eduh_mik.schoolconnect2.models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Eduh_mik on 4/21/2018.
 */

public class Activities {
    String act1, act2, act3, act4, date;

    public Activities(String act1, String act2, String act3, String act4) {

    }

    public String getAct1() {
        return act1;
    }

    public String getAct2() {
        return act2;
    }

    public String getAct3() {
        return act3;
    }

    public String getAct4() {
        return act4;
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
