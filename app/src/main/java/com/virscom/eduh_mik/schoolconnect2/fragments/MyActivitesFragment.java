package com.virscom.eduh_mik.schoolconnect2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.virscom.eduh_mik.schoolconnect2.R;
import com.virscom.eduh_mik.schoolconnect2.Retrofit.ActivitiesRequest;
import com.virscom.eduh_mik.schoolconnect2.Retrofit.ListResponse;
import com.virscom.eduh_mik.schoolconnect2.Retrofit.ServiceGenerator;
import com.virscom.eduh_mik.schoolconnect2.adapters.MyActivitiesAdapter;
import com.virscom.eduh_mik.schoolconnect2.base.BaseFragment;
import com.virscom.eduh_mik.schoolconnect2.interfaces.OnFragmentInteractionListener;
import com.virscom.eduh_mik.schoolconnect2.models.ListModel;
import com.virscom.eduh_mik.schoolconnect2.models.MyActvities;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyActivitesFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.simpleSwipeRefreshLayout)
    SwipeRefreshLayout simpleSwipeRefreshLayout;
    private OnFragmentInteractionListener mListener;
    private MyActivitiesAdapter activitiesAdapter;
    private ArrayList<MyActvities> activitiesList = new ArrayList<>();
    private ListModel _listModel;

    public MyActivitesFragment(){

    }
    public static MyActivitesFragment newInstance() {
        return new MyActivitesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _listModel = new Gson().fromJson(getArguments().getString("list"), ListModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activities, container, false);
        ButterKnife.bind(this, view);
        simpleSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                simpleSwipeRefreshLayout.setRefreshing(true);
                prepareMyActivitiesData(_listModel.getStudent_id());
            }
        });

        return view;
    }

    public static MyActivitesFragment newInstance(ListModel listModel) {
        MyActivitesFragment fragment = new MyActivitesFragment();
        Bundle args = new Bundle();
        args.putString("list", new Gson().toJson(listModel));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        prepareMyActivitiesData(_listModel.getStudent_id());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //sectionsAdapter = new SectionsAdapter(sections);
        recyclerView.setLayoutManager(layoutManager);
        prepareMyActivitiesData(_listModel.getStudent_id());
    }

    private void prepareMyActivitiesData(String id) {
        simpleSwipeRefreshLayout.setRefreshing(true);
        ActivitiesRequest service = ServiceGenerator.createService(ActivitiesRequest.class);
        Call<ListResponse<MyActvities>> call = service.getMyActivities(id);
        call.enqueue(new Callback<ListResponse<MyActvities>>() {
            @Override
            public void onResponse(Call<ListResponse<MyActvities>> call, Response<ListResponse<MyActvities>> response) {
                simpleSwipeRefreshLayout.setRefreshing(false);
                try {
                    Log.e("fees", gson.toJson(response.body()));
                    Log.e("Status", response.body().getStatus());
                    if (TextUtils.equals(response.body().getStatus(), "success")) {
                        ArrayList<MyActvities> response1 = response.body().getList();
                        activitiesList.clear();
                        activitiesList.addAll(response1);
                        Log.e("Fees", gson.toJson(response.body()));
                        activitiesAdapter = new MyActivitiesAdapter(getContext(), activitiesList);
                        recyclerView.setAdapter(activitiesAdapter);
                    } else {
                        showToast("Please try again");
                    }
                } catch (Exception e) {
                    activitiesList.clear();
                    activitiesAdapter.notifyDataSetChanged();
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ListResponse<MyActvities>> call, Throwable t) {
                simpleSwipeRefreshLayout.setRefreshing(false);
                Log.e("", t.getMessage());
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
