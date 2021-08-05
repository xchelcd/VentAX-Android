package com.idax.ventax.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SocialMedia implements Serializable {

    @SerializedName("id")
    private int socialmedia_id; //facebook 0 - instagram 1 - twitter 2 - web 3 - whats 4

    @SerializedName("code")
    private String code;

    public SocialMedia() {
    }

    public SocialMedia(int socialmedia_id, String code) {
        this.socialmedia_id = socialmedia_id;
        this.code = code;
    }

    public int getSocialmedia_id() {
        return socialmedia_id;
    }

    public void setSocialmedia_id(int socialmedia_id) {
        this.socialmedia_id = socialmedia_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
