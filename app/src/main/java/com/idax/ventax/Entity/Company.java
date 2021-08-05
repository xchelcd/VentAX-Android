package com.idax.ventax.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Company implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("category_id")
    private int category;

    @SerializedName("address")
    private String address;

    @SerializedName("active")
    private int active;

    @SerializedName("priority")
    private int priority;


    public Company() {
        this.id = -1;
        this.userId = -1;
        this.name = "Sin internet";
        this.description = "Sin internet";
        this.category = -1;
        this.address = "Sin internet";
        this.active = 1;
        this.priority = -1;
    }

    public Company(int id, int userId, String name, String description, int category, String address, int active, int priority) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.address = address;
        this.active = active;
        this.priority = priority;
    }
    public Company(int userId, String name, String description, int category){
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
