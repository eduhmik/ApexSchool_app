package com.example.eduh_mik.schoolconnect2.Retrofit;

import com.example.eduh_mik.schoolconnect2.models.Contact;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ContactsRequests {
    @GET("contacts")
    Call<ListResponse<Contact>> getContacts();
}
