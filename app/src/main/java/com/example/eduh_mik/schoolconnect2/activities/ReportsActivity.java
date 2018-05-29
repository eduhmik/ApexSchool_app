package com.example.eduh_mik.schoolconnect2.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.Retrofit.SectionRequests;
import com.example.eduh_mik.schoolconnect2.Retrofit.ServiceGenerator;
import com.example.eduh_mik.schoolconnect2.base.BaseActivity;
import com.example.eduh_mik.schoolconnect2.models.Section;
import com.example.eduh_mik.schoolconnect2.picker.SectionsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class ReportsActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private SectionsAdapter sectionsAdapter;
    private ArrayList<Section> sectionArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        ButterKnife.bind(this);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);
        //sectionsAdapter = new SectionsAdapter(sections);
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }

    public void loadJSON(){
        SectionRequests service = ServiceGenerator.createService(SectionRequests.class);
        Call<ArrayList<Section>> call = service.getJSON();
        call.enqueue(new Callback<ArrayList<Section>>() {
            @Override
            public void onResponse(Call<ArrayList<Section>> call, retrofit2.Response<ArrayList<Section>> response) {
                Log.e("Section", String.valueOf(response.body()));
                try {
                    sectionArrayList = new ArrayList<>(response.body());
                    Log.e("Sections", gson.toJson(response.body()));
                    sectionsAdapter = new SectionsAdapter(sectionArrayList);
                    recyclerView.setAdapter(sectionsAdapter);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Section>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
