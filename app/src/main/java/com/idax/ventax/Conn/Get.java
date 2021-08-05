package com.idax.ventax.Conn;

import com.idax.ventax.Entity.Company;
import com.idax.ventax.Entity.EntrepreneurModel;
import com.idax.ventax.Entity.LoginModel;
import com.idax.ventax.Entity.OrderModel;
import com.idax.ventax.Entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.idax.ventax.Extra.Constansts.restURL;
import static com.idax.ventax.Extra.EndPoints.BUSINESS;
import static com.idax.ventax.Extra.EndPoints.ORDERS_BY_COMPANY_ID;
import static com.idax.ventax.Extra.EndPoints.ORDERS_BY_USER_ID;
import static com.idax.ventax.Extra.EndPoints.CHECK_USER_BY_USER_CREDENTIALS;
import static com.idax.ventax.Extra.EndPoints.ENTREPRENEUR_MODEL;
import static com.idax.ventax.Extra.EndPoints.GET_CREDENTIALS;
import static com.idax.ventax.Extra.EndPoints.GET_CREDENTIALS_AFFILIATE;

public interface Get {

    //@FormUrlEncoded
    @POST(restURL + "Test")
    Call<String> Test(@Body User user);

    @POST(restURL + "test")
    Call<String> Test(@Body OrderModel model);

    @FormUrlEncoded
    @POST(restURL + GET_CREDENTIALS)
    Call<User> getUserByCredentials(
            @Field("user_name") String user_name,
            @Field("password") String password);

    @FormUrlEncoded
    @POST(restURL + GET_CREDENTIALS_AFFILIATE)
    Call<LoginModel> GetUserByCredentialsAndCheckAffiliate(
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("token") String token,
            @Field("os") int os);

    @GET(restURL + BUSINESS)
    Call<List<Company>> getAllBusiness();

    //GetCompanyEnrepeneursModel
    //@FormUrlEncoded
    @GET(restURL + ENTREPRENEUR_MODEL)
    Call<EntrepreneurModel> getCompanyEntrepreneurModel(
            @Query("company_id") int compnay_id);

    @FormUrlEncoded
    @POST(restURL + CHECK_USER_BY_USER_CREDENTIALS)
    Call<LoginModel> GetCheckUserByUserCredentials(@Field("phone") String phone,
                                                   @Field("id") int id);

    @FormUrlEncoded
    @POST(restURL + ORDERS_BY_USER_ID)
    Call<List<OrderModel>> GetAllOrdersByUserId(@Field("user_id") int userId);

    @FormUrlEncoded
    @POST(restURL + ORDERS_BY_COMPANY_ID)
    Call<List<OrderModel>> GetAllOrdersByCompanyId(@Field("company_id") int companyId);
}
