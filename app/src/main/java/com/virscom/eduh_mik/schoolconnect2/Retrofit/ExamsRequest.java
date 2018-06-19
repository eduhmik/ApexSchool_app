package com.virscom.eduh_mik.schoolconnect2.Retrofit;

import com.virscom.eduh_mik.schoolconnect2.models.Exam;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ExamsRequest {
    @GET("exams/id/{id}")
    Call<ListResponse<Exam>> getExams(@Path("id") String id);
}
