package com.virscom.eduh_mik.schoolconnect2.Retrofit;

import com.virscom.eduh_mik.schoolconnect2.models.Comment;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CommentsRequest {
    @FormUrlEncoded
    @POST("comments")
    Call<ListResponse<Comment>> addComment(
            @Field("name") String name,
            @Field("title") String title,
            @Field("description") String description
    );
}
