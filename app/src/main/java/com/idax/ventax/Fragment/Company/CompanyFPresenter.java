package com.idax.ventax.Fragment.Company;

import android.graphics.Bitmap;

import com.idax.ventax.Conn.Conn;
import com.idax.ventax.Conn.Delete;
import com.idax.ventax.Conn.Update;

import java.text.MessageFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.idax.ventax.Extra.Util.ConfigSocialMedia.getStringImage;

public class CompanyFPresenter {

    CompanyFView view;

    public CompanyFPresenter(CompanyFView view) {
        this.view = view;
    }


    public void UploadCompanyPhoto(Bitmap image, int company_id, String company) {
        view.showProgressBar("Guardando foto");
        String imageStr = getStringImage(image);
        Update update = Conn.getClient().create(Update.class);
        Call<String> call = update.updatePhoto(imageStr, company.toUpperCase(), company_id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                view.hideProgressBar();
                if (response.isSuccessful()) {
                    view.OnUpdatePhotoSuccess();
                } else {
                    view.OnError();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                view.hideProgressBar();
                view.OnError();
            }
        });
    }

    public void ChangeStateCompany(int companyId, int state) {
        Update update = Conn.getClient().create(Update.class);
        Call<String> call = update.ChangeStateCompany(state, companyId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body().equals("1")) {
                        view.OnDesactivateSuccess(state, state == 1 ? "Activado" : "Desactivado");
                        return;
                    }
                }
                view.onDesactiveError(MessageFormat.format("No se pudo {0} su empresa. Intent de nuevo", state == 1 ? "activar" : "desactivar"));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                view.onDesactiveError(MessageFormat.format("No se pudo {0} su empresa. Intent de nuevo", state == 1 ? "activar" : "desactivar"));
            }
        });

    }

    public void DeleteStateCompany(int companyId) {
        Delete delete = Conn.getClient().create(Delete.class);
        Call<String> call = delete.DeleteCompany(companyId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body().equals("1")) {
                        view.onDeleteCompanySuccess("Empresa eliminada");
                        return;
                    }
                }
                view.onDeleteCompanyError("Ocurrió un error");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                view.onDeleteCompanyError("Ocurrió un error");            }
        });
    }


}
