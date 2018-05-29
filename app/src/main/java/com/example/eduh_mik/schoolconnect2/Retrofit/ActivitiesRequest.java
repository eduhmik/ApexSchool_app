package com.example.eduh_mik.schoolconnect2.Retrofit;

import com.example.eduh_mik.schoolconnect2.models.Activities;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ActivitiesRequest {
    @GET("activities")
    Call<ListResponse<Activities>> getActivities();
}
