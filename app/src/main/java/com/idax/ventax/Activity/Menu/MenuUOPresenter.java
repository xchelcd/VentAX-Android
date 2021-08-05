package com.idax.ventax.Activity.Menu;

import com.idax.ventax.Conn.Conn;
import com.idax.ventax.Conn.Delete;
import com.idax.ventax.Conn.Update;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuUOPresenter {

    private MenuUpdateObjects view;

    public MenuUOPresenter(MenuUpdateObjects view) {
        this.view = view;
    }

    public void LogOutAccount(int userId, String token){
        view.showProgressBar("Cerrando sesión...");
        Update update = Conn.getClient().create(Update.class);
        Call<Void> call = update.LogOutAccount(userId, token);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                view.onSuccessLogOut();
                view.hideProgressBar();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                view.hideProgressBar();
                view.onErrorLogOut("Ocurrió un error");
            }
        });
    }
}
