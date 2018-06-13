package com.example.eduh_mik.schoolconnect2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.Retrofit.FeesRequests;
import com.example.eduh_mik.schoolconnect2.Retrofit.ListResponse;
import com.example.eduh_mik.schoolconnect2.Retrofit.ServiceGenerator;
import com.example.eduh_mik.schoolconnect2.adapters.FeesAdapter;
import com.example.eduh_mik.schoolconnect2.base.BaseFragment;
import com.example.eduh_mik.schoolconnect2.interfaces.OnFragmentInteractionListener;
import com.example.eduh_mik.schoolconnect2.models.Fees;
import com.example.eduh_mik.schoolconnect2.models.ListModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeesFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.simpleSwipeRefreshLayout)
    SwipeRefreshLayout simpleSwipeRefreshLayout;
    //Unbinder unbinder;
    private OnFragmentInteractionListener mListener;
    private FeesAdapter feesAdapter;
    private ArrayList<Fees> feesList = new ArrayList<>();

    private ListModel _listModel;

    public FeesFragment() {
        // Required empty public constructor
    }

    public static FeesFragment newInstance(ListModel listModel) {
        FeesFragment fragment = new FeesFragment();
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
        View view = inflater.inflate(R.layout.fragment_fees, container, false);
        ButterKnife.bind(this, view);
//        FloatingActionButton myFab = (FloatingActionButton) view.findViewById(R.id.fab);
//        myFab.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                showdialog();
//            }
//        });
        simpleSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                simpleSwipeRefreshLayout.setRefreshing(true);
                prepareContactData(_listModel.getStudent_id());
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        prepareContactData(_listModel.getStudent_id());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //sectionsAdapter = new SectionsAdapter(sections);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void prepareContactData(String id) {
        simpleSwipeRefreshLayout.setRefreshing(true);
        FeesRequests service = ServiceGenerator.createService(FeesRequests.class);
        Call<ListResponse<Fees>> call = service.getFees(id);
        call.enqueue(new Callback<ListResponse<Fees>>() {
            @Override
            public void onResponse(Call<ListResponse<Fees>> call, Response<ListResponse<Fees>> response) {
                Log.e("fees", gson.toJson(response.body()));
                Log.e("Status", response.body().getStatus());
                simpleSwipeRefreshLayout.setRefreshing(false);
                try {
                    if (TextUtils.equals(response.body().getStatus(), "success")) {
                        ArrayList<Fees> response1 = response.body().getList();
                        feesList.clear();
                        feesList.addAll(response1);
                        Log.e("Fees", gson.toJson(response.body()));
                        feesAdapter = new FeesAdapter(getContext(), feesList);
                        recyclerView.setAdapter(feesAdapter);
                    } else {
                        showToast("Please try again");
                    }

                } catch (Exception e) {
                    feesList.clear();
                    feesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ListResponse<Fees>> call, Throwable t) {
                Log.e("fees", t.getMessage());
                simpleSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void showdialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_add_contact, null);
        dialogBuilder.setView(dialogView);


        final AlertDialog b = dialogBuilder.create();
        b.show();


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
