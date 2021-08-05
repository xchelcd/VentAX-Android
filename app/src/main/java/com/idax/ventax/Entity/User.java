package com.idax.ventax.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User extends Account implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("last_name")
    private String lastName;

    public User(String name, String lastName, String ext, String number, String password) {
        super(ext, number, password);
        this.name = name;
        this.lastName = lastName;
    }

    public User(int id, String name, String lastName, String userName, String password, String startDate, int age, String email, String phone, String ext, int affiliate) {
        super(userName, password, startDate, age, email, phone, ext, affiliate);
        this.id = id;
        this.name = name;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
