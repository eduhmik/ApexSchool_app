package com.example.eduh_mik.schoolconnect2.models;

public class Section {
    String name, desc;
    int c_id;

    public int getC_id() {
        return c_id;
    }

    public Section(String name, String desc){
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
