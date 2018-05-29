package com.example.eduh_mik.schoolconnect2.Retrofit;

import com.example.eduh_mik.schoolconnect2.models.Diary;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DiaryRequests {
    @FormUrlEncoded
    @POST("diary")
    Call<ListResponse<Diary>> newDiary(
            @Field("descr") String description,
            @Field("student_id") String student_id
    );

    @GET("diary/id/{id}")
    Call<ListResponse<Diary>>  getDiary(@Path("id") String id);
}
