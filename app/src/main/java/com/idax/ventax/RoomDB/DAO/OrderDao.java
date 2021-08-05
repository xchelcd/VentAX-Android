package com.idax.ventax.RoomDB.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.idax.ventax.Entity.OrderSQLite;
import com.idax.ventax.Entity.Product;

import java.util.List;

@Dao
public interface OrderDao {

    @Query("SELECT id, company_id, company_name, product_id, product_name,  SUM(qty) as qty, price, comment FROM orders WHERE company_id = :companyId GROUP BY product_id")
    List<OrderSQLite>getAllOrdersByCompany(int companyId);

    @Query("SELECT * FROM orders")
    List<OrderSQLite>getAllOrders();

    @Query("SELECT id, company_id, company_name, product_id, product_name,  SUM(price * qty) AS price, qty, comment FROM orders  GROUP BY company_id ")
    List<OrderSQLite>getAllBusinessNames();

    @Insert
    void insertOrder(OrderSQLite order);

    @Query("SELECT * FROM orders WHERE product_id = :productId")
    OrderSQLite getProduct(int productId);

    @Delete
    void deleteOrder(OrderSQLite order);

    @Query("DELETE FROM orders WHERE product_id = :productId")
    void deleteAllByProductId(int productId);

    @Delete
    void deleteOrders(List<OrderSQLite> orderList);

    @Query("UPDATE orders SET qty = :qty WHERE product_id = :prodcutId")
    void upadteQty(int prodcutId, int qty);

    /*
    @ColumnInfo(name = "id")
@ColumnInfo(name = "company_id")
@ColumnInfo(name = "user_id")
@ColumnInfo(name = "Product")
@ColumnInfo(name = "qty")
@ColumnInfo(name = "date")
@ColumnInfo(name = "comment")
     */
}
