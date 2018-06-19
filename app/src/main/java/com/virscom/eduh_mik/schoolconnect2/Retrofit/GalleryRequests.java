package com.virscom.eduh_mik.schoolconnect2.Retrofit;

import com.virscom.eduh_mik.schoolconnect2.models.Gallery;
import com.virscom.eduh_mik.schoolconnect2.models.MyGallery;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GalleryRequests {
    @GET("gallery")
    Call<ListResponse<Gallery>> getGallery();

    @GET("pgallery/id/{id}")
    Call<ListResponse<MyGallery>> getMyGallery(@Path("id") String id);
}
