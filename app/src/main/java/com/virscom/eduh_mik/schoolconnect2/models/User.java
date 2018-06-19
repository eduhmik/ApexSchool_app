package com.virscom.eduh_mik.schoolconnect2.models;

/**
 * Created by Mayore on 09/04/2018.
 */

public class User {
    private String user_id;

    private String email;

    private String first_name;

    private String last_name;

    private String phone;

    private String role;

    private String status;

    private String image;


    public String getId() {
        return user_id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getImage() {
        return image;
    }
    public String getPhone(){
        return phone;
    }
    public String getRole(){
        return role;
    }
    public String getStatus(){
        return status;
    }
}
