package com.idax.ventax.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Account implements Serializable {

    @SerializedName("user_name")
    private String userName;
    @SerializedName("password")
    private String password;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("age")
    private int age;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("ext")
    private String ext;
    @SerializedName("affiliate")
    private int affiliate;
    @SerializedName("token")
    private String token;

    public Account() {
    }

    public Account(String ext, String phone, String password){
        this.ext = ext;
        this.phone = phone;
        this.password = password;
    }

    public Account(String userName, String password, String startDate, int age, String email, String phone, String ext, int affiliate) {
        this.userName = userName;
        this.password = password;
        this.startDate = startDate;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.ext = ext;
        this.affiliate = affiliate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public int getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(int affiliate) {
        this.affiliate = affiliate;
    }

    public String getToken() {
        return token;
    }
}
