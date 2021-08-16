package com.idax.ventax.Activity.Launch;

import com.idax.ventax.Conn.Conn;
import com.idax.ventax.Conn.Get;
import com.idax.ventax.Entity.LoginModel;
import com.idax.ventax.Entity.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaunchPresenter {

    private LaunchView view;

    public LaunchPresenter(LaunchView view) {
        this.view = view;
    }

    public void GetCheckUserByUserCredentials(User user){
        Get get = Conn.getClient().create(Get.class);
        Call<LoginModel> call = get.GetCheckUserByUserCredentials(user.getPhone(),user.getId());//user.getUserName(), user.getId());
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful() & response.body() != null) {
                    view.onUserSaved(response.body());
                } else {
                    view.onError("Ocurrió un error. Checa tu conexión a internet");
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                view.onError("Ocurrió un error. Checa tu conexión a internet");
            }
        });
    }

    public void test(User user) {
        Get get = Conn.getClient().create(Get.class);
        Call<String> call = get.Test(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() & response.body() != null) {
                    //view.onUserSaved(response.body());
                } else {
                    view.onError("Ocurrió un error. Checa tu conexión a internet");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                view.onError("Ocurrió un error. Checa tu conexión a internet");
            }
        });
    }
}
