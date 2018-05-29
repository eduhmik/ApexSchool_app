package com.example.eduh_mik.schoolconnect2.models;

/**
 * Created by Eduh_mik on 4/21/2018.
 */

public class Contact {
    String first_name, role, phone, email;

    public Contact(String name, String role, String phone, String email){
        this.first_name = name;
        this.role = role;
        this.phone = phone;
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getRole() {
        return role;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
