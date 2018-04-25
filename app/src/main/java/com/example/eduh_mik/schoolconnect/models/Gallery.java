package com.example.eduh_mik.schoolconnect.models;

import android.graphics.drawable.Drawable;

/**
 * Created by Eduh_mik on 4/23/2018.
 */

public class Gallery {
    String text;
    Drawable image;

    public Gallery(String text, Drawable image){
        this.text = text;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
