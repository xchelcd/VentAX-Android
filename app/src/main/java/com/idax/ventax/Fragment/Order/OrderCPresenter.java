package com.idax.ventax.Fragment.Order;

import com.idax.ventax.Conn.Conn;
import com.idax.ventax.Conn.Get;
import com.idax.ventax.Entity.OrderModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderCPresenter {

    private OrderCView view;

    public OrderCPresenter(OrderCView view) {
        this.view = view;
    }

    public void GetAllOrdersByCompanyId(int companyId) {
        view.showProgressBar("Cargando datos...");
        Get get = Conn.getClient().create(Get.class);
        Call<List<OrderModel>> call = get.GetAllOrdersByCompanyId(companyId);
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
