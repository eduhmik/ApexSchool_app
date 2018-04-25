package com.example.eduh_mik.schoolconnect.models;

/**
 * Created by Eduh_mik on 4/21/2018.
 */

public class Notices {

    String notice, notDescription, notDate;
    public Notices(String s, String s1, String s2) {

        this.notice = s;
        this.notDescription = s1;
        this.notDate = s2;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getNotDescription() {
        return notDescription;
    }

    public void setNotDescription(String notDescription) {
        this.notDescription = notDescription;
    }

    public String getNotDate() {
        return notDate;
    }

    public void setNotDate(String notDate) {
        this.notDate = notDate;
    }
}
