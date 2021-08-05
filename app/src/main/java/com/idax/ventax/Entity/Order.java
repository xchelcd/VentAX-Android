package com.idax.ventax.Entity;

import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("user_id")
    private int userId;

    @SerializedName("company_id")
    private int companyId;

    @SerializedName("product_id")
    private int productId;

    @SerializedName("qty")
    private int qty;

    @SerializedName("date")
    private String date;

    @SerializedName("comment")
    private String comment;

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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
