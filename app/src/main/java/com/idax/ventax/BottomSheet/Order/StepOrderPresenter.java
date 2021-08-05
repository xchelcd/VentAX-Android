package com.idax.ventax.BottomSheet.Order;


import com.idax.ventax.Conn.Conn;
import com.idax.ventax.Conn.Update;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StepOrderPresenter {

    StepOrderView view;

    public StepOrderPresenter(StepOrderView view) {
        this.view = view;
    }

    public void NextStepOrder(int orderId, int step) {
        view.showProgressBar("");
        Update update = Conn.getClient().create(Update.class);
        Call<String> call = update.NextStepOrder(orderId, step);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body().equals("1"))
                    view.OnSuccessNextStep("Notificación enviada al comprador");
                else view.OnErrorNextStep("No se pudo actualizar");
                view.hideProgressBar();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                view.OnErrorNextStep("Ocurrió un error");
                view.hideProgressBar();
            }
        });
    }
}
