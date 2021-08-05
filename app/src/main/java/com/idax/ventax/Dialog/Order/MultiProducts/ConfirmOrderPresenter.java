package com.idax.ventax.Dialog.Order.MultiProducts;


import com.idax.ventax.Conn.Conn;
import com.idax.ventax.Conn.Insert;
import com.idax.ventax.Entity.OrderModel;
import com.squareup.okhttp.ResponseBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmOrderPresenter {

    private ConfirmOrderView view;

    public ConfirmOrderPresenter(ConfirmOrderView view) {
        this.view = view;
    }

    public void InsertOrder(OrderModel orderModel){
        Insert insert = Conn.getClient().create(Insert.class);
        Call<String> call = insert.InsertNewOrder(orderModel);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                view.OnSuccess();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                view.OnError();
            }
        });

    }
}
