package com.example.eduh_mik.schoolconnect2.Retrofit;

import com.example.eduh_mik.schoolconnect2.models.Homework;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HomeworkRequests {
    @GET("homeworks/id/{id}")
    Call<ListResponse<Homework>> getHomework(@Path("id")String id);
}
