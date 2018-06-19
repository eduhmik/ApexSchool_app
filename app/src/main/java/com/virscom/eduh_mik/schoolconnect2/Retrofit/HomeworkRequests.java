package com.virscom.eduh_mik.schoolconnect2.Retrofit;

import com.virscom.eduh_mik.schoolconnect2.models.Homework;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HomeworkRequests {
    @FormUrlEncoded
    @POST("homeworks")
    Call<ListResponse<Homework>> addHomework(
            @Field("descr") String descr,
            @Field("student_id") String student_id
    );

    @GET("homeworks/id/{id}")
    Call<ListResponse<Homework>> getHomework(@Path("id")String id);
}
