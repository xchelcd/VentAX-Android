package com.idax.ventax.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

@Entity(tableName = "orders")
public class OrderSQLite {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "company_id")
    private int companyId;

    @ColumnInfo(name = "company_name")
    private String companyName;

    @ColumnInfo(name = "product_id")
    private int productId;

    @ColumnInfo(name = "product_name")
    private String productName;

    @ColumnInfo(name = "qty")
    private int qty;

    @ColumnInfo(name = "price")
    private double price;

    //@ColumnInfo(name = "date")
    //private String date;

    @ColumnInfo(name = "comment")
    private String comment;

    public OrderSQLite(int id, int companyId, String companyName, int productId, String productName, int qty, double price, String comment) {
        this.id = id;
        this.companyId = companyId;
        this.companyName = companyName;
        this.productId = productId;
        this.productName = productName;
        this.qty = qty;
        this.price = price;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
