package com.idax.ventax.Conn;

import com.idax.ventax.Entity.OrderModel;
import com.idax.ventax.Entity.Product;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.idax.ventax.Extra.Constansts.restURL;
import static com.idax.ventax.Extra.EndPoints.INSERT_COMPANY;
import static com.idax.ventax.Extra.EndPoints.INSERT_PRODUCT;
import static com.idax.ventax.Extra.EndPoints.INSERT_SUGGESTION;
import static com.idax.ventax.Extra.EndPoints.INSERT_USER;
import static com.idax.ventax.Extra.EndPoints.NEW_ORDER;
import static com.idax.ventax.Extra.EndPoints.NOTIFICATION_ORDER_REQUEST;

public interface Insert {

    @FormUrlEncoded
    @POST(restURL + INSERT_USER)
    Call<String> EnrollUser(@Field("name") String name,
                          @Field("last_name") String lastName,
                          @Field("phone") String phone,
                          @Field("ext") String ext,
                          @Field("password") String password);

    @FormUrlEncoded
    @POST(restURL + INSERT_COMPANY)
    Call<String> EnrollCompany(@Field("user_id") int userId,
                               @Field("name") String name,
                               @Field("description") String description,
                               @Field("category_id") int category,
                               @Field("token") String token,
                               @Field("address") String address,
                               @Field("email") String email,
                               @Field("image") String image);

    @FormUrlEncoded
    @POST(restURL + NOTIFICATION_ORDER_REQUEST)
    Call<Void> InsertOrderSingleRequest(@Field("user_id") int userId,
                                        @Field("company_id") int companyId,
                                        @Field("product_id") int productId,
                                        @Field("qty") int qty,
                                        @Field("comment") String comment);

    //isset($_POST['image']) && isset($_POST['company_id']) &&
    // isset($_POST['product_name']) && isset($_POST['price']) &&
    // isset($_POST['description']) && isset($_POST['details']) &&
    // isset($_POST['position'])
    @FormUrlEncoded
    @POST(restURL + INSERT_PRODUCT)
    Call<Product> InsertProduct(@Field("company_id") int company_id, @Field("product_name") String product_name,
                                @Field("price") double price, @Field("description") String description,
                                @Field("details") String details, @Field("position") int position,
                                @Field("company_name") String companyName, @Field("image") String image);

    @POST(restURL + NEW_ORDER)
    Call<String> InsertNewOrder(@Body OrderModel orderModel);

    @FormUrlEncoded
    @POST(restURL + INSERT_SUGGESTION)
    Call<String> InsertSuggestion(@Field("user_id") int userId,
                                  @Field("suggestion") String suggestion);
}
