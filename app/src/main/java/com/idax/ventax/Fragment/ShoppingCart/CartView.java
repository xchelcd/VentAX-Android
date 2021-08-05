package com.idax.ventax.Fragment.ShoppingCart;

import com.idax.ventax.Entity.OrderModel;
import com.idax.ventax.Entity.OrderSQLite;
import com.idax.ventax.Extra.IConst;

import java.util.List;

public interface CartView extends IConst {

    void onSuccessResponse(List<OrderModel> orderModelList);
    void onErrorResponse(String message);

    void OnOrderRequestSuccess(List<OrderSQLite> orderSQLiteList);
}
