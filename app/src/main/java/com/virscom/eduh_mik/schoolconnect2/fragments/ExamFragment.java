package com.virscom.eduh_mik.schoolconnect2.fragments;

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

import com.virscom.eduh_mik.schoolconnect2.R;
import com.virscom.eduh_mik.schoolconnect2.Retrofit.ExamsRequest;
import com.virscom.eduh_mik.schoolconnect2.Retrofit.ListResponse;
import com.virscom.eduh_mik.schoolconnect2.Retrofit.ServiceGenerator;
import com.virscom.eduh_mik.schoolconnect2.adapters.ExamAdapter;
import com.virscom.eduh_mik.schoolconnect2.base.BaseFragment;
import com.virscom.eduh_mik.schoolconnect2.interfaces.OnFragmentInteractionListener;
import com.virscom.eduh_mik.schoolconnect2.models.Exam;
import com.virscom.eduh_mik.schoolconnect2.models.ListModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.simpleSwipeRefreshLayout)
    SwipeRefreshLayout simpleSwipeRefreshLayout;
    //Unbinder unbinder;
    private OnFragmentInteractionListener mListener;
    private ExamAdapter examAdapter;
    private ArrayList<Exam> examList = new ArrayList<>();
    private ListModel _listModel;

    public ExamFragment() {
        // Required empty public constructor
    }

    public static ExamFragment newInstance(ListModel listModel) {
        ExamFragment fragment = new ExamFragment();
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
        View view = inflater.inflate(R.layout.fragment_exam, container, false);
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
        ExamsRequest service = ServiceGenerator.createService(ExamsRequest.class);
        Call<ListResponse<Exam>> call = service.getExams(id);
        call.enqueue(new Callback<ListResponse<Exam>>() {
            @Override
            public void onResponse(Call<ListResponse<Exam>> call, Response<ListResponse<Exam>> response) {
                Log.e("fees", gson.toJson(response.body()));
                Log.e("Status", response.body().getStatus());
                simpleSwipeRefreshLayout.setRefreshing(false);
                try {
                    if (TextUtils.equals(response.body().getStatus(), "success")) {
                        ArrayList<Exam> response1 = response.body().getList();
                        examList.clear();
                        examList.addAll(response1);
                        Log.e("Fees", gson.toJson(response.body()));
                        examAdapter = new ExamAdapter(getContext(), examList);
                        recyclerView.setAdapter(examAdapter);
                    } else {
                        showToast("Please try again");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    examList.clear();
                    examAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ListResponse<Exam>> call, Throwable t) {
                simpleSwipeRefreshLayout.setRefreshing(false);
                Log.e("fees", t.getMessage());
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
