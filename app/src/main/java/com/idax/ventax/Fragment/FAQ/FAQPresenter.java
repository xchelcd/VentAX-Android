package com.idax.ventax.Fragment.FAQ;


import com.google.gson.Gson;
import com.idax.ventax.Conn.Conn;
import com.idax.ventax.Conn.Update;
import com.idax.ventax.Entity.CompanyConfigurationModel;
import com.idax.ventax.Entity.FAQ;
import com.idax.ventax.Entity.FAQModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FAQPresenter {

    private FAQView view;

    public FAQPresenter(FAQView view) {
        this.view = view;
    }

    public void UpdateFAQs(FAQModel faqModel){
        Gson gson = new Gson();
        String json = gson.toJson(faqModel, FAQModel.class);
        view.showProgressBar("Actualizando");
        Update update = Conn.getClient().create(Update.class);
        Call<String> call = update.UpdateFAQs(faqModel);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                view.OnSuccessFAQ();
                view.hideProgressBar();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                view.OnErrorFAQ("Ocurrió un error. No se pudo actualziar las preguntas.");
                view.hideProgressBar();
            }
        });
    }
}
