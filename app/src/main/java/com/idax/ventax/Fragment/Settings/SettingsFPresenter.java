package com.idax.ventax.Fragment.Settings;


import com.google.gson.Gson;
import com.idax.ventax.Conn.Conn;
import com.idax.ventax.Conn.Update;
import com.idax.ventax.Entity.CompanyConfigurationModel;
import com.idax.ventax.Entity.Design;
import com.idax.ventax.Entity.Schedule;
import com.idax.ventax.Entity.SocialMedia;
import com.idax.ventax.Entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFPresenter {

    SettingsFView view;

    public SettingsFPresenter(SettingsFView view){
        this.view = view;
    }


    public void updateCompanyByCompanyId(CompanyConfigurationModel ccm){
        Gson gson = new Gson();
        String json = gson.toJson(ccm, CompanyConfigurationModel.class);
        view.showProgressBar("Guardando datos, espere un momento");
        Update update = Conn.getClient().create(Update.class);
        Call<String> call = update.UpdateCompanyConfigurationModel(ccm);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                view.hideProgressBar();
                view.OnSuccessDataSaved(ccm);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                view.hideProgressBar();
                view.OnErrorDataSaved();
            }
        });
    }
}
