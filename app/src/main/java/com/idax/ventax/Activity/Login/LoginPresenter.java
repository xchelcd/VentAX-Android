package com.idax.ventax.Activity.Login;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.idax.ventax.Conn.Conn;
import com.idax.ventax.Conn.Get;
import com.idax.ventax.Entity.Company;
import com.idax.ventax.Entity.LoginModel;
import com.idax.ventax.Entity.OrderModel;
import com.idax.ventax.Entity.User;
import com.idax.ventax.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.idax.ventax.Extra.Constansts.ANDROID;
import static com.idax.ventax.Extra.Util.throwError;

public class LoginPresenter {

    private LoginView view;
    private Context context;
    public LoginPresenter(LoginView view, Context context){
        this.view = view;
        this.context = context;
    }

    public void GetUserByCredentials(String userName, String password){//deprecate
        view.showProgressBar(new StringBuffer().append(context.getString(R.string.finding_user)).append(userName).append("\"").toString());

        Get get = Conn.getClient().create(Get.class);
        Call<User> call = get.getUserByCredentials(userName, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                view.hideProgressBar();
                if (response.isSuccessful() && response.body() != null) {
                    //view.onSuccessCredentials(response.body());
                    GetAllBusiness(response.body());
                } else {
                    view.onErrorCredentials(throwError(context, Integer.parseInt(response.message())));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                view.hideProgressBar();
                view.onErrorCredentials(call.toString());
            }
        });
    }

    public void GetAllBusiness(final User user){//deprecate
        view.showProgressBar("Espere un momento...");
        Get get = Conn.getClient().create(Get.class);
        Call<List<Company>> call = get.getAllBusiness();
        call.enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                view.hideProgressBar();
                if (response.isSuccessful() && response.body() != null) {
                    view.onFinalSuccessRequest(user, response.body());
                } else {
                    view.onFinalErrorRequest(throwError(context, Integer.parseInt(response.message())));
                }
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable t) {
                view.hideProgressBar();
                view.onFinalErrorRequest(call.toString());
            }
        });
    }

    public void GetUserByCredentialsAndCheckAffiliate(String phone, String password) {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            //todo checar respuesta con firebase analytics
                            return;
                        }
                        String token = task.getResult();
                        GetUserByCredentialsAndCheckAffiliate2(phone, password, token);
                    }
                });
    }
    public void GetUserByCredentialsAndCheckAffiliate2(String phone, String password, String token) {
        //final String[] token = {""};
        view.showProgressBar(new StringBuffer().append(context.getString(R.string.finding_user)).append(phone).append("\"").toString());
        //todo corregir el archivo de mi lap porque aún mantiene el género
        Get get = Conn.getClient().create(Get.class);
        Call<LoginModel> call = get.GetUserByCredentialsAndCheckAffiliate(phone, password, token, ANDROID);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                view.hideProgressBar();
                if (response.isSuccessful() && response.body() != null) {
                    view.onSuccessCredentials(response.body());
                } else {
                    //view.onErrorCredentials(throwError(context, Integer.parseInt(response.message())));
                    view.onErrorCredentials("Usuario o contraseña incorrecto");
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                view.hideProgressBar();
                view.onErrorCredentials("Usuario o contraseña incorrecto");
            }
        });
    }

    public void GetCheckUserByUserCredentials(User user){
        //TODO SIMILAR -> GetUserByCredentialsAndCheckAffiliate pero recibe un atributo PHONE y un USERNAME
        view.showProgressBar("Cargando datos");
        Get get = Conn.getClient().create(Get.class);
        Call<LoginModel> call = get.GetCheckUserByUserCredentials(user.getPhone(),user.getId());//user.getUserName(), user.getId());
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                //view.hideProgressBar();
                if (response.isSuccessful() && response.body() != null) {
                    view.onSuccessUserSaved(response.body());
                } else {
                    view.onErrorCredentials(throwError(context, Integer.parseInt(response.message())));
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                //view.hideProgressBar();
            }
        });
    }

    public void test(OrderModel orderModel) {
        Get get = Conn.getClient().create(Get.class);
        Call<String> call = get.Test(orderModel);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }
}
