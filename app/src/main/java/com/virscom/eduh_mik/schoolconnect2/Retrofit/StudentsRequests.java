package com.virscom.eduh_mik.schoolconnect2.Retrofit;

import com.virscom.eduh_mik.schoolconnect2.models.ListModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StudentsRequests {
    @GET("students")
    Call<ArrayList<ListModel>> getPrepareListData();

    @GET("stlist/id/{phone}")
    Call<ListResponse<ListModel>> getFilteredListData(@Path("phone") String phone);
}
