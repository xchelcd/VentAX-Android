package com.idax.ventax.Conn;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.idax.ventax.Extra.Constansts.restURL;
import static com.idax.ventax.Extra.EndPoints.DELETE_COMPANY;
import static com.idax.ventax.Extra.EndPoints.DELETE_PRODUCT;

public interface Delete {

    @FormUrlEncoded
    @POST(restURL + DELETE_PRODUCT)
    Call<String> DeleteProduct(@Field("company_id") int companyId,
                               @Field("product_id") int productId);

    @FormUrlEncoded
    @POST(restURL + DELETE_COMPANY)
    Call<String> DeleteCompany(
            @Field("company_id") int companyId);
}
