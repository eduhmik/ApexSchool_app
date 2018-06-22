package com.virscom.eduh_mik.schoolconnect2.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.virscom.eduh_mik.schoolconnect2.R;
import com.virscom.eduh_mik.schoolconnect2.Retrofit.ListResponse;
import com.virscom.eduh_mik.schoolconnect2.Retrofit.ServiceGenerator;
import com.virscom.eduh_mik.schoolconnect2.Retrofit.StudentsRequests;
import com.virscom.eduh_mik.schoolconnect2.adapters.ListAdapter;
import com.virscom.eduh_mik.schoolconnect2.base.BaseActivity;
import com.virscom.eduh_mik.schoolconnect2.models.ListModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.simpleSwipeRefreshLayout)
    SwipeRefreshLayout simpleSwipeRefreshLayout;
    private ListAdapter listAdapter;
    private ArrayList<ListModel> galleryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        //listAdapter = new ListAdapter(galleryList);
        recyclerView.setLayoutManager(layoutManager);
        //ListModel listModel = new Gson().fromJson(getIntent().getExtras().getString("list"), ListModel.class);
        simpleSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                simpleSwipeRefreshLayout.setRefreshing(false);
                filteredData(sharedPrefs.getUser().getPhone());
            }
        });
        Log.e("Phone", String.valueOf(sharedPrefs.getUser().getPhone()));
        //Log.e("List", listModel.getClass_id());
        filteredData(sharedPrefs.getUser().getPhone());
    }

    public void filteredData(String phone) {
        simpleSwipeRefreshLayout.setRefreshing(true);
        StudentsRequests service = ServiceGenerator.createService(StudentsRequests.class);
        Call<ListResponse<ListModel>> call = service.getFilteredListData(phone);
        call.enqueue(new Callback<ListResponse<ListModel>>() {
            @Override
            public void onResponse(Call<ListResponse<ListModel>> call, Response<ListResponse<ListModel>> response) {
                Log.e("fees", gson.toJson(response.body()));
                Log.e("Status", response.body().getStatus());
                try {
                    if (TextUtils.equals(response.body().getStatus(), "success")) {
                        simpleSwipeRefreshLayout.setRefreshing(false);
                        ArrayList<ListModel> response1 = response.body().getList();
                        galleryList.clear();
                        galleryList.addAll(response1);
                        listAdapter = new ListAdapter(getApplicationContext(), galleryList);
                        recyclerView.setAdapter(listAdapter);
                    }
                } catch (Exception e) {
                    galleryList.clear();
                    listAdapter.notifyDataSetChanged();
                    e.printStackTrace();
                }
                Log.e("Size", String.valueOf(galleryList.size()));
            }

            @Override
            public void onFailure(Call<ListResponse<ListModel>> call, Throwable t) {
                simpleSwipeRefreshLayout.setRefreshing(false);
                Log.d("Error", t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startNewActivity(StudentActivity.class);
    }
}
