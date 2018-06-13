package com.example.eduh_mik.schoolconnect2.Retrofit;

import com.example.eduh_mik.schoolconnect2.models.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AccountRequests {
    @FormUrlEncoded
    @POST("users")
    Call<Response> register(
            @Field("email") String email,
            @Field("first_name") String firstname,
            @Field("last_name") String lastname,
            @Field("password") String password,
            @Field("mob") String phone,
            @Field("device_id") String device_id
    );

    @GET("users")
    Call<ArrayList<User>> getUsers();

    @GET("login")
    Call<ListResponse<User>> getLoginDetails(@Query("phone") String phone, @Query("password") String password);
}
