package com.example.eduh_mik.schoolconnect.models;

/**
 * Created by Eduh_mik on 4/21/2018.
 */

public class Contact {
    String name, position, details, email;

    public Contact(String name, String position, String details, String email){
        this.name = name;
        this.position = position;
        this.details = details;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
