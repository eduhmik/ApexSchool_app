package com.virscom.eduh_mik.schoolconnect2.Retrofit;

import com.virscom.eduh_mik.schoolconnect2.models.Section;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SectionRequests {
    @GET("classes")
    Call<ArrayList<Section>> getJSON();
}
