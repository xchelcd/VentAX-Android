package com.idax.ventax.Activity.RegisterCompany;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.idax.ventax.Conn.Conn;
import com.idax.ventax.Conn.Insert;
import com.squareup.okhttp.ResponseBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.idax.ventax.Extra.Util.ConfigSocialMedia.getStringImage;

public class RegisterPresenter {

    private RegisterView view;

    public RegisterPresenter(RegisterView view) {
        this.view = view;
    }

    public void EnrollCompany(int userId, String name, String description, int category, String token, String address, String email, Bitmap image) {
        view.showProgressBar("Procesando solicitud");
        token = (token == null) ? "" : token;
        String imageStr;
        try {
            imageStr = getStringImage(image);
        } catch (Exception e) {
            imageStr = "";
        }
        Insert insert = Conn.getClient().create(Insert.class);
        Call<String> call = insert.EnrollCompany(userId, name, description, category, token, address, email, imageStr);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> ResponseBody, Response<String> response) {
                if (response.body().equals("1")) view.OnSuccessEnrollCompany("Registro exitoso");
                else view.OnErrorEnrollCompany("Ocurrió un error");
                view.hideProgressBar();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                view.OnErrorEnrollCompany("Ocurrió un error");
                view.hideProgressBar();
            }
        });
    }
}
