package com.example.eduh_mik.schoolconnect2.Retrofit;

import com.example.eduh_mik.schoolconnect2.models.Gallery;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GalleryRequests {
    @GET("Gallery")
    Call<ListResponse<Gallery>> getGallery();
}
