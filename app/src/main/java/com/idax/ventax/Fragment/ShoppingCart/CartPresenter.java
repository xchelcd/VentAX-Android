package com.idax.ventax.Fragment.ShoppingCart;


import com.idax.ventax.Conn.Conn;
import com.idax.ventax.Conn.Get;
import com.idax.ventax.Entity.OrderModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartPresenter {

    private CartView view;

    public CartPresenter(CartView view) {
        this.view = view;
    }

    public void GetAllUserOrdersByUserId(int userId) {
        view.showProgressBar("Cargando datos...");
        Get get = Conn.getClient().create(Get.class);
        Call<List<OrderModel>> call = get.GetAllOrdersByUserId(userId);
        call.enqueue(new Callback<List<OrderModel>>() {
            @Override
            public void onResponse(Call<List<OrderModel>> call, Response<List<OrderModel>> response) {
                if (response.body() != null && !response.body().isEmpty()) {
                    view.onSuccessResponse(response.body());
                } else if (response.body().isEmpty()) {
                    view.onErrorResponse("Aún no cuenta con pedidos pendientes");
                } else {
                    view.onErrorResponse("Ocurrió un error, verifique su internet");
                }
                view.hideProgressBar();
            }

            @Override
            public void onFailure(Call<List<OrderModel>> call, Throwable t) {
                view.hideProgressBar();
            }
        });
    }
}
