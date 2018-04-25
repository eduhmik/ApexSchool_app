package com.example.eduh_mik.schoolconnect.models;

/**
 * Created by Eduh_mik on 4/21/2018.
 */

public class Activities {
    String name, grade, activities1, activities2, activities3;

    public Activities(String s, String s1, String s2, String s3, String s4) {

        this.name = s;
        this.grade = s1;
        this.activities1 = s2;
        this.activities2 = s3;
        this.activities3 = s4;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getActivities1() {
        return activities1;
    }

    public void setActivities1(String activities1) {
        this.activities1 = activities1;
    }

    public String getActivities2() {
        return activities2;
    }

    public void setActivities2(String activities2) {
        this.activities2 = activities2;
    }

    public String getActivities3() {
        return activities3;
    }

    public void setActivities3(String activities3) {
        this.activities3 = activities3;
    }

}
