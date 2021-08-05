package com.idax.ventax.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Schedule implements Serializable {

    @SerializedName("day_id")
    private int day;

    @SerializedName("start")
    private String start;

    @SerializedName("end")
    private String end;

    public Schedule(int day, String start, String end) {
        this.day = day;
        this.start = start;
        this.end = end;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
