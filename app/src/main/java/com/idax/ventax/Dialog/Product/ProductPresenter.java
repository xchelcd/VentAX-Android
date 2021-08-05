package com.idax.ventax.Dialog.Product;

import android.graphics.Bitmap;

import com.idax.ventax.Conn.Conn;
import com.idax.ventax.Conn.Delete;
import com.idax.ventax.Conn.Insert;
import com.idax.ventax.Conn.Update;
import com.idax.ventax.Entity.Product;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.idax.ventax.Extra.Util.ConfigSocialMedia.getStringImage;

public class ProductPresenter {

    private ProductView view;

    public ProductPresenter(ProductView view) {
        this.view = view;
    }

    public void AddNewProduct(int companyId, String companyName, Product product, Bitmap image) {
        view.showProgressBar("Guardando item.\n Espere un momento");
        String imageStr = getStringImage(image);
        Insert insert = Conn.getClient().create(Insert.class);
        Call<Product> call = insert.InsertProduct(companyId, product.getName(), product.getPrice(), product.getDescription(), product.getDetails(), 0, companyName, imageStr);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    view.OnAddSuccess(response.body());
                } else {
                    view.OnAddError("");
                }
                view.hideProgressBar();
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                view.OnAddError("");
                view.hideProgressBar();
            }
        });
    }

    public void DeleteProduct(int companyId, int productId) {
        view.showProgressBar("Eliminando item");
        Delete delete = Conn.getClient().create(Delete.class);
        Call<String> call = delete.DeleteProduct(companyId, productId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    view.OnDeleteSuccess("");
                } else {
                    view.OnDeleteError("");
                }
                view.hideProgressBar();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                view.OnDeleteError("");
                view.hideProgressBar();
            }
        });
    }

    public void EditProduct(int companyId, String companyName, Product product, Bitmap image) {
        view.showProgressBar("Guardando cambios");
        String imageStr = getStringImage(image);
        Update update = Conn.getClient().create(Update.class);
        Call<Product> call = update.UpdateProduct(product.getId(), companyId, companyName, product.getName(), product.getPrice(), product.getDescription(), product.getDetails(), 0, imageStr);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    view.OnEditSuccess(response.body());
                } else {
                    view.OnEditError("");
                }
                view.hideProgressBar();
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                view.OnEditError("");
                view.hideProgressBar();
            }
        });
    }
}
