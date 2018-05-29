package com.example.eduh_mik.schoolconnect2.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.Retrofit.ServiceGenerator;
import com.example.eduh_mik.schoolconnect2.Retrofit.StudentsRequests;
import com.example.eduh_mik.schoolconnect2.adapters.ListAdapter;
import com.example.eduh_mik.schoolconnect2.base.BaseActivity;
import com.example.eduh_mik.schoolconnect2.models.ListModel;
import com.example.eduh_mik.schoolconnect2.models.Section;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ListAdapter listAdapter;
    private ArrayList<ListModel> galleryList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);
        //listAdapter = new ListAdapter(galleryList);
        recyclerView.setLayoutManager(layoutManager);
        Section section = new Gson().fromJson(getIntent().getExtras().getString("section"), Section.class);
        filteredData(section.getC_id());
    }
    public void filteredData(int id) {
        StudentsRequests service = ServiceGenerator.createService(StudentsRequests.class);
        Call<ArrayList<ListModel>> call = service.getFilteredListData(id);
        call.enqueue(new Callback<ArrayList<ListModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ListModel>> call, Response<ArrayList<ListModel>> response) {
                try {
                    galleryList.clear();
                    galleryList.addAll(response.body());
                    listAdapter = new ListAdapter(getApplicationContext(), galleryList);
                    recyclerView.setAdapter(listAdapter);
                }catch(Exception e){
                    galleryList.clear();
                    listAdapter.notifyDataSetChanged();
                    e.printStackTrace();
                }
                Log.e("Size",String.valueOf(galleryList.size()));
            }

            @Override
            public void onFailure(Call<ArrayList<ListModel>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startNewActivity(StudentActivity.class);
    }
}