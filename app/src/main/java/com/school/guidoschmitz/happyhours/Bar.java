package com.school.guidoschmitz.happyhours;

import android.text.Spanned;

/**
 * Created by Liam Hubers on 27-5-2015.
 */
public class Bar {
    private Spanned name;
    private Spanned address;
    private Integer thumbnail;

    public Bar(Spanned name, Spanned address, Integer thumbnail) {
        this.setName(name);
        this.setAddress(address);
        this.setThumbnail(thumbnail);
    }

    public void setName(Spanned name) {
        this.name = name;
    }

    public void setAddress(Spanned address) {
        this.address = address;
    }

    public void setThumbnail(Integer thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Spanned getName() {
        return this.name;
    }

    public Spanned getAddress() {
        return this.address;
    }

    public Integer getThumbnail() {
        return this.thumbnail;
    }
}
