package com.virscom.eduh_mik.schoolconnect2.Retrofit;

import com.virscom.eduh_mik.schoolconnect2.models.Subject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SubjectsRequests {
    @GET("subjects")
    Call<ArrayList<Subject>> getSubjects();
}
