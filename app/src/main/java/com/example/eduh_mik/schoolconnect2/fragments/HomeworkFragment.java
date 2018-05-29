package com.example.eduh_mik.schoolconnect2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.Retrofit.HomeworkRequests;
import com.example.eduh_mik.schoolconnect2.Retrofit.ListResponse;
import com.example.eduh_mik.schoolconnect2.Retrofit.ServiceGenerator;
import com.example.eduh_mik.schoolconnect2.adapters.HomeworkAdapter;
import com.example.eduh_mik.schoolconnect2.base.BaseFragment;
import com.example.eduh_mik.schoolconnect2.interfaces.OnFragmentInteractionListener;
import com.example.eduh_mik.schoolconnect2.models.Homework;
import com.example.eduh_mik.schoolconnect2.models.ListModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeworkFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    //Unbinder unbinder;
    private OnFragmentInteractionListener mListener;
    private HomeworkAdapter homeworkAdapter;
    private List<Homework> homeworkList = new ArrayList<>();
    private ListModel _listModel;


    public HomeworkFragment() {
        // Required empty public constructor
    }

    public static HomeworkFragment newInstance(ListModel listModel) {
        HomeworkFragment fragment = new HomeworkFragment();
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
        View view = inflater.inflate(R.layout.fragment_homework, container, false);
        ButterKnife.bind(this, view);
        FloatingActionButton myFab = (FloatingActionButton) view.findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                showdialog();
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeworkAdapter = new HomeworkAdapter(getActivity(), homeworkList);
        recyclerView.setAdapter(homeworkAdapter);
        prepareContactData(_listModel.getStudent_id());
    }
    private void prepareContactData(String id) {
        HomeworkRequests service = ServiceGenerator.createService(HomeworkRequests.class);
        Call<ListResponse<Homework>> call = service.getHomework(id);
        call.enqueue(new Callback<ListResponse<Homework>>() {
            @Override
            public void onResponse(Call<ListResponse<Homework>> call, Response<ListResponse<Homework>> response) {
                Log.e("fees", gson.toJson(response.body()));
                Log.e("Status",response.body().getStatus());
                if(TextUtils.equals(response.body().getStatus(),"success")){
                    ArrayList<Homework> response1 = response.body().getList();
                    homeworkList.addAll(response1);
                    Log.e("Fees", gson.toJson(response.body()));
                    homeworkAdapter = new HomeworkAdapter(getContext(),homeworkList);
                    recyclerView.setAdapter(homeworkAdapter);
                }else{
                    showToast("Please try again");
                }
            }

            @Override
            public void onFailure(Call<ListResponse<Homework>> call, Throwable t) {
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
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            mListener = (OnFragmentInteractionListener) context;
        }else {
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
