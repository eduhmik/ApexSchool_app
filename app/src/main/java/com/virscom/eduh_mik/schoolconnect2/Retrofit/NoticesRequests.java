package com.virscom.eduh_mik.schoolconnect2.Retrofit;

import com.virscom.eduh_mik.schoolconnect2.models.Notices;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NoticesRequests {
    @FormUrlEncoded
    @POST("events")
    Call<ListResponse<Notices>> addNotices(
            @Field("date") String date,
            @Field("time") String time,
            @Field("title") String title,
            @Field("descr") String descr
    );
    @GET("events")
    Call<ListResponse<Notices>> getNotices();
}
