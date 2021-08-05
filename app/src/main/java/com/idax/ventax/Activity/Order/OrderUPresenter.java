package com.idax.ventax.Activity.Order;

import com.idax.ventax.Conn.Conn;
import com.idax.ventax.Conn.Delete;
import com.idax.ventax.Conn.Update;
import com.idax.ventax.Dialog.Order.Cancel.CancelOrderInterface;
import com.idax.ventax.Entity.OrderModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.idax.ventax.Extra.Constansts.CANCEL_TYPE_COMPANY;

public class OrderUPresenter {
    private OrderUView view;

    public OrderUPresenter(OrderUView view) {
        this.view = view;
    }

    public void CancelOrder(OrderModel orderModel, int type, CancelOrderInterface cancelOrderInterface, int position) {
        view.showProgressBar("Espere un momento.");
        Update update = Conn.getClient().create(Update.class);
        Call<String> call;
        if (type == CANCEL_TYPE_COMPANY) call = update.CancelOrderFromCompany(orderModel.getOrderId(), orderModel.getUserId());
        else call = update.CancelOrderFromClient(orderModel.getOrderId(), orderModel.getUserId());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful())
                    if (response.body().equals("1")){
                        orderModel.setState(4);
                        view.OnSuccessCancel(orderModel, position);
                        cancelOrderInterface.OnResponse(1);
                    }

                view.hideProgressBar();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                view.OnErrorCancel();
                view.hideProgressBar();
            }
        });
    }
}
