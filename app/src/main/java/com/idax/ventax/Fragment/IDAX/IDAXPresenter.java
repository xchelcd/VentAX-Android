package com.idax.ventax.Fragment.IDAX;


import com.idax.ventax.Conn.Conn;
import com.idax.ventax.Conn.Insert;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IDAXPresenter {

    private IDAXView view;

    public IDAXPresenter(IDAXView view) {
        this.view = view;
    }

    public void InsertSuggestion(int userId, String suggestion){
        view.showProgressBar("Gracias por su sugerencia/queja");
        Insert insert = Conn.getClient().create(Insert.class);
        Call<String> call = insert.InsertSuggestion(userId, suggestion);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("1")) view.OnSuccessSuggestion("Enviado.");
                else view.OnErrorSuggestion("No se pudo enviar su sugerencia/queja");
                view.hideProgressBar();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                view.hideProgressBar();
                view.OnErrorSuggestion("No se pudo enviar su sugerencia/queja");
            }
        });
    }
}
