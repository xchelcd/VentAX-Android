package com.idax.ventax.Dialog.Order;

import com.google.gson.Gson;
import com.idax.ventax.Conn.Conn;
import com.idax.ventax.Conn.Insert;
import com.idax.ventax.Entity.Order;
import com.idax.ventax.Entity.OrderModel;
import com.idax.ventax.Entity.User;
import com.squareup.okhttp.ResponseBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderPresenter {

    private OrderView view;

    public OrderPresenter(OrderView view) {
        this.view = view;
    }

    public void InsertOrder(OrderModel orderModel) {
        Insert insert = Conn.getClient().create(Insert.class);
        Call<String> call = insert.InsertNewOrder(orderModel);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body().equals("1"))
                    view.onSuccessOrderNotification("Pedido realizado.");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                view.onErrorOrderNotification("Ocurrió un error");
            }
        });

    }

    public void InsertOrderSingleRequest(int userId, int companyId, int productId, int qty, String comment) {
        view.showProgressBar("Creando pedido...");
        Insert insert = Conn.getClient().create(Insert.class);
        Call<Void> call = insert.InsertOrderSingleRequest(userId, companyId, productId, qty, comment);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                view.hideProgressBar();
                view.onSuccessOrderNotification("Pedido realizado.");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                view.hideProgressBar();
            }
        });
    }
}
