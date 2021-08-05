package com.idax.ventax.Conn;

import com.idax.ventax.Entity.CompanyConfigurationModel;
import com.idax.ventax.Entity.FAQModel;
import com.idax.ventax.Entity.Product;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.idax.ventax.Extra.Constansts.restURL;
import static com.idax.ventax.Extra.EndPoints.CANCEL_ORDER_FROM_CLIENT;
import static com.idax.ventax.Extra.EndPoints.CANCEL_ORDER_FROM_COMPANY;
import static com.idax.ventax.Extra.EndPoints.STATE_COMPANY;
import static com.idax.ventax.Extra.EndPoints.LOG_OUT;
import static com.idax.ventax.Extra.EndPoints.NEXT_STEP_ORDER;
import static com.idax.ventax.Extra.EndPoints.CONFIGURATION_MODEL;
import static com.idax.ventax.Extra.EndPoints.UPDATE_PRODUCT;
import static com.idax.ventax.Extra.EndPoints.STATE_PRODUCT;
import static com.idax.ventax.Extra.EndPoints.COMPANY_PHOTO;

public interface Update {
    @FormUrlEncoded
    @POST(restURL + LOG_OUT)
    Call<Void> LogOutAccount(
            @Field("user_id") int userId,
            @Field("token") String token);

    @FormUrlEncoded
    @POST(restURL + COMPANY_PHOTO)
    Call<String> updatePhoto(@Field("image") String image,
                             @Field("company") String company,
                             @Field("company_id") int company_id);


    //UpdateCompanyConfigurationModel
    @POST(restURL + CONFIGURATION_MODEL)
    Call<String> UpdateCompanyConfigurationModel(@Body CompanyConfigurationModel ccm);

    @POST(restURL + "UpdateFAQs")
    Call<String> UpdateFAQs(@Body FAQModel faqModel);

    @FormUrlEncoded
    @POST(restURL + NEXT_STEP_ORDER)
    Call<String> NextStepOrder(@Field("order_id") int orderId,
                               @Field("step") int step);

    //isset($_POST['company_id']) && isset($_POST['company_name']) &&
    // isset($_POST['product_name']) && isset($_POST['price']) &&
    // isset($_POST['description']) && isset($_POST['details']) &&
    // isset($_POST['position']) && isset($_POST['id']) && isset($_POST['image'])
    @FormUrlEncoded
    @POST(restURL + UPDATE_PRODUCT)
    Call<Product> UpdateProduct(@Field("id") int id, @Field("company_id") int company_id, @Field("company_name") String company_name,
                                @Field("product_name") String product_name, @Field("price") double price,
                                @Field("description") String description, @Field("details") String details,
                                @Field("position") int position, @Field("image") String image);

    @FormUrlEncoded
    @POST(restURL + STATE_PRODUCT)
    Call<String> UpdateProductState(@Field("id")int id, @Field("state") int state);


    @FormUrlEncoded
    @POST(restURL + STATE_COMPANY)
    Call<String> ChangeStateCompany(
            @Field("state") int state,
            @Field("company_id") int companyId);

    @FormUrlEncoded
    @POST(restURL + CANCEL_ORDER_FROM_COMPANY)
    Call<String> CancelOrderFromCompany(@Field("order_id") int orderId,
                                        @Field("company_id") int companyId);

    @FormUrlEncoded
    @POST(restURL + CANCEL_ORDER_FROM_CLIENT)
    Call<String> CancelOrderFromClient(@Field("order_id") int orderId,
                                       @Field("user_id") int userId);

}
