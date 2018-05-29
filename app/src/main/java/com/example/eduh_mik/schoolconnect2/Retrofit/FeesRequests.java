package com.example.eduh_mik.schoolconnect2.Retrofit;

import com.example.eduh_mik.schoolconnect2.models.Fees;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FeesRequests {
    @GET("fees/id/{id}")
    Call<ListResponse<Fees>> getFees(@Path("id") String id);
}
