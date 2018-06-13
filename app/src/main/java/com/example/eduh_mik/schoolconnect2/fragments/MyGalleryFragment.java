package com.example.eduh_mik.schoolconnect2.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.Retrofit.GalleryRequests;
import com.example.eduh_mik.schoolconnect2.Retrofit.ListResponse;
import com.example.eduh_mik.schoolconnect2.Retrofit.ServiceGenerator;
import com.example.eduh_mik.schoolconnect2.adapters.MyGalleryAdapter;
import com.example.eduh_mik.schoolconnect2.base.BaseFragment;
import com.example.eduh_mik.schoolconnect2.interfaces.OnFragmentInteractionListener;
import com.example.eduh_mik.schoolconnect2.models.ListModel;
import com.example.eduh_mik.schoolconnect2.models.MyGallery;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyGalleryFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.simpleSwipeRefreshLayout)
    SwipeRefreshLayout simpleSwipeRefreshLayout;
    private OnFragmentInteractionListener mListener;
    private MyGalleryAdapter galleryAdapter;
    private ArrayList<MyGallery> galleryList = new ArrayList<>();
    private ListModel _listModel;

    public MyGalleryFragment() {
        //Reguired empty constructor
    }

    public static MyGalleryFragment newInstance(ListModel listModel) {
        MyGalleryFragment fragment = new MyGalleryFragment();
        Bundle args = new Bundle();
        args.putString("list", new Gson().toJson(listModel));
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_mygallery, container, false);
        ButterKnife.bind(this, view);
        simpleSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                simpleSwipeRefreshLayout.setRefreshing(true);
                prepareMyGalleryData(_listModel.getStudent_id());
            }
        });

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        prepareMyGalleryData(_listModel.getStudent_id());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //sectionsAdapter = new SectionsAdapter(sections);
        recyclerView.setLayoutManager(layoutManager);
        prepareMyGalleryData(_listModel.getStudent_id());
    }

    private void prepareMyGalleryData(String id) {
        simpleSwipeRefreshLayout.setRefreshing(true);
        GalleryRequests service = ServiceGenerator.createService(GalleryRequests.class);
        Call<ListResponse<MyGallery>> call = service.getMyGallery(id);
        call.enqueue(new Callback<ListResponse<MyGallery>>() {
            @Override
            public void onResponse(Call<ListResponse<MyGallery>> call, Response<ListResponse<MyGallery>> response) {
                simpleSwipeRefreshLayout.setRefreshing(false);
                try {
                    Log.e("fees", gson.toJson(response.body()));
                    Log.e("Status", response.body().getStatus());
                    if (TextUtils.equals(response.body().getStatus(), "success")) {
                        ArrayList<MyGallery> response1 = response.body().getList();
                        galleryList.clear();
                        galleryList.addAll(response1);
                        Log.e("Fees", gson.toJson(response.body()));
                        galleryAdapter = new MyGalleryAdapter(getContext(), galleryList);
                        recyclerView.setAdapter(galleryAdapter);
                    } else {
                        showToast("Please try again");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    galleryList.clear();
                    galleryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ListResponse<MyGallery>> call, Throwable t) {
                simpleSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public static GridLayoutManager getLayoutManager(Context context) {
        GridLayoutManager l;
        if (checkIfPhone(context)) {
            if (getScreenOrientation(context) == 0) {
                l = new GridLayoutManager(context, 3);
                return l;
            }
            if (getScreenOrientation(context) == 1) {
                l = new GridLayoutManager(context, 3);
                return l;
            }
            if (getScreenOrientation(context) == 2) {
                l = new GridLayoutManager(context, 5);
                return l;
            }
        } else {
            if (getScreenOrientation(context) == 0) {
                l = new GridLayoutManager(context, 4);
                return l;
            }
            if (getScreenOrientation(context) == 1) {
                l = new GridLayoutManager(context, 4);
                return l;
            }
            if (getScreenOrientation(context) == 2) {
                l = new GridLayoutManager(context, 5);
                return l;
            }
        }
        return new GridLayoutManager(context, 1);

    }

    public static int getScreenOrientation(Context context) {
        Display getOrient = ((Activity) context).getWindowManager().getDefaultDisplay();
        int orientation = Configuration.ORIENTATION_UNDEFINED;
        if (getOrient.getWidth() == getOrient.getHeight()) {
            orientation = Configuration.ORIENTATION_SQUARE; //0 for square orientation
        } else {
            if (getOrient.getWidth() < getOrient.getHeight()) {
                orientation = Configuration.ORIENTATION_PORTRAIT; //1 for potrait
            } else {
                orientation = Configuration.ORIENTATION_LANDSCAPE;  //2 for landscape
            }
        }
        return orientation;
    }

    public static Boolean checkIfPhone(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        if (diagonalInches > 6.5)  //6.5 inch device or bigger
        {
            return false;
        } else    //standard device
        {
            return true;
        }
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

