package com.idax.ventax.Fragment.Menu;

import android.content.Context;

import com.idax.ventax.Conn.Conn;
import com.idax.ventax.Conn.Get;
import com.idax.ventax.Entity.EntrepreneurModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.idax.ventax.Extra.Util.throwError;

public class MenuPresenter {

    private MenuView view;
    private Context context;

    public MenuPresenter(MenuView view, Context context) {
        this.view = view;
        this.context = context;
    }

    //getCompanyEnrepeneursModel

    public void GetCompanyEntrepreneurModel(int id){
        view.showProgressBar("Espere un momento...");
        Get get = Conn.getClient().create(Get.class);
        Call<EntrepreneurModel> call = get.getCompanyEntrepreneurModel(id);
        call.enqueue(new Callback<EntrepreneurModel>() {
            @Override
            public void onResponse(Call<EntrepreneurModel> call, Response<EntrepreneurModel> response) {
                view.hideProgressBar();
                if (response.isSuccessful() & response.body() != null) {
                    view.onSuccessCompanyEntrepreneurModel(response.body());
                } else {
                    view.onErrorCompanyEntrepreneurMode(throwError(context, Integer.parseInt(response.message())));
                }
            }

            @Override
            public void onFailure(Call<EntrepreneurModel> call, Throwable t) {
                view.hideProgressBar();
                view.onErrorCompanyEntrepreneurMode(call.toString());
            }
        });
    }

}
