package com.example.eduh_mik.schoolconnect2.Retrofit;

import com.example.eduh_mik.schoolconnect2.models.Contact;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ContactsRequests {
    @FormUrlEncoded
    @POST("contacts")
    Call<ListResponse<Contact>> addContacts(
            @Field("role") String role,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("phone2") String phone2,
            @Field("email") String email
    );
    @GET("contacts")
    Call<ListResponse<Contact>> getContacts();
}
