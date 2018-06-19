package com.virscom.eduh_mik.schoolconnect2.Retrofit;

import com.virscom.eduh_mik.schoolconnect2.models.Activities;
import com.virscom.eduh_mik.schoolconnect2.models.MyActvities;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ActivitiesRequest {
    @FormUrlEncoded
    @POST("activities")
    Call<ListResponse<Activities>> addActivies(
            @Field("act1") String act1,
            @Field("act2") String act2,
            @Field("act3") String act3,
            @Field("act4") String act4,
            @Field("date") String date,
            @Field("time") String time
    );
    @GET("activities")
    Call<ListResponse<Activities>> getActivities();

    @GET("paid/id/{id}")
    Call<ListResponse<MyActvities>> getMyActivities(@Path("id") String id);
}
