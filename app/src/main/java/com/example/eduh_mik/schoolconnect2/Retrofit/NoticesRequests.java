package com.example.eduh_mik.schoolconnect2.Retrofit;

import com.example.eduh_mik.schoolconnect2.models.Notices;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NoticesRequests {
    @GET("events")
    Call<ListResponse<Notices>> getNotices();
}
