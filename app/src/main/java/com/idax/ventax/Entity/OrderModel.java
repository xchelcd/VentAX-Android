package com.idax.ventax.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable {//todo add attribute -> first_name, full_number

    @SerializedName("id")
    private int orderId;

    @SerializedName("phone")
    private String phone;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("first_name")
    private String personName;

    @SerializedName("company_id")
    private int companyId;

    @SerializedName("name")
    private String companyName;

    @SerializedName("state")
    private int state;

    @SerializedName("total")
    private double total;//precio total de todos los productos

    @SerializedName("date")
    private String date;

    @SerializedName("comment")
    private String comment;

    @SerializedName("products")
    private List<OrderProduct> orderProductList;


    public OrderModel(int userId, int companyId, double total, List<OrderProduct> orderProductList, String comment) {
        this.userId = userId;
        this.companyId = companyId;
        this.total = total;
        this.orderProductList = orderProductList;
        this.comment = comment;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<OrderProduct> getOrderProductList() {
        return orderProductList;
    }

    public void setOrderProductList(List<OrderProduct> orderProductList) {
        this.orderProductList = orderProductList;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPhone() {
        return phone;
    }

    public String getPersonaName() {
        return personName;
    }
}
