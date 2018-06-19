package com.virscom.eduh_mik.schoolconnect2.models;

public class Exam {
    private String student_id, ex_id, type, year, term, section, maths, english, kisw, scie, ss, computer;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getScie() {
        return scie;
    }

    public void setScie(String scie) {
        this.scie = scie;
    }

    public Exam(String student_id, String ex_id, String type, String year, String term, String section, String maths,
                String english, String kisw, String scie, String ss, String computer){
        this.student_id = student_id;
        this.scie = scie;

        this.ex_id = ex_id;
        this.type = type;
        this.year = year;
        this.section = section;
        this.maths = maths;
        this.english = english;
        this.kisw = kisw;
        this.ss = ss;
        this.computer = computer;
        this.term = term;

    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getEx_id() {
        return ex_id;
    }

    public void setEx_id(String ex_id) {
        this.ex_id = ex_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getMaths() {
        return maths;
    }

    public void setMaths(String maths) {
        this.maths = maths;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getKisw() {
        return kisw;
    }

    public void setKisw(String kisw) {
        this.kisw = kisw;
    }

    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }

    public String getComputer() {
        return computer;
    }

    public void setComputer(String computer) {
        this.computer = computer;
    }
}
