package com.idax.ventax.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderProduct implements Serializable {

    @SerializedName("product_id")
    private int productId;

    @SerializedName("name")
    private String product;

    @SerializedName("qty")
    private int qty;

    @SerializedName("total")
    private double total;//precio del producto individual por qty

    public OrderProduct(int productId, int qty, double total) {
        this.productId = productId;
        this.qty = qty;
        this.total = total;
    }

    public OrderProduct(int productId, int qty) {
        this.productId = productId;
        this.qty = qty;
    }

    public OrderProduct(int productId, String product, int qty) {
        this.productId = productId;
        this.product = product;
        this.qty = qty;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
