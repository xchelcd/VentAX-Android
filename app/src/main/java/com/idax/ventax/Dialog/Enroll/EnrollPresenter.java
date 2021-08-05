package com.idax.ventax.Dialog.Enroll;


import com.idax.ventax.Conn.Conn;
import com.idax.ventax.Conn.Insert;
import com.idax.ventax.Entity.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnrollPresenter {

    private EnrollView view;

    public EnrollPresenter(EnrollView view) {
        this.view = view;
    }

    public void enrollUser(User user, String token) {
        view.showProgressBar("Registrando usuario. Espere un momento");
        Insert insert = Conn.getClient().create(Insert.class);

        Call<String> call = insert.EnrollUser(user.getName(), user.getLastName(), user.getPhone(), user.getExt(), user.getPassword());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                view.hideProgressBar();
                view.onAuthSuccess("Usuario creado correctamente");

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                view.hideProgressBar();
            }
        });
    }
}
