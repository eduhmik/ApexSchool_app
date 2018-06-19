package com.virscom.eduh_mik.schoolconnect2.Retrofit;

import java.util.ArrayList;

public class ListResponse<T> {
    public String status,message;
    public ArrayList<T> list;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<T> getList() {

        return list;
    }
}
