package com.school.guidoschmitz.happyhours.models;

import java.io.Serializable;

public class Event implements Serializable {
    protected int locationId;
    protected int dayOfWeek;
    protected String description;
    protected String startTime;
    protected String endTime;

    public int getLocationId() { return this.locationId; }

    public void setLocationId(int locationId) { this.locationId = locationId; }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
