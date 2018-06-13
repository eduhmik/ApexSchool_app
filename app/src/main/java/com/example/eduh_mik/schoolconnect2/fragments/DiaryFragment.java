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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.Retrofit.DiaryRequests;
import com.example.eduh_mik.schoolconnect2.Retrofit.ListResponse;
import com.example.eduh_mik.schoolconnect2.Retrofit.ServiceGenerator;
import com.example.eduh_mik.schoolconnect2.adapters.DiaryAdapter;
import com.example.eduh_mik.schoolconnect2.base.BaseFragment;
import com.example.eduh_mik.schoolconnect2.interfaces.OnFragmentInteractionListener;
import com.example.eduh_mik.schoolconnect2.models.Diary;
import com.example.eduh_mik.schoolconnect2.models.ListModel;
import com.example.eduh_mik.schoolconnect2.tools.SweetAlertDialog;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Eduh_mik on 4/19/2018.
 */

public class DiaryFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {
    int position;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    //    @BindView(R.id.simple_spinner_kids)
    Spinner simpleSpinnerKids;

    EditText tvContent;
    ImageButton sendDiary;
    @BindView(R.id.simpleSwipeRefreshLayout)
    SwipeRefreshLayout simpleSwipeRefreshLayout;
    //Unbinder unbinder;
    private OnFragmentInteractionListener mListener;
    private DiaryAdapter diaryAdapter;
    private ArrayList<Diary> diaryList = new ArrayList<>();
    private ListModel _listModel;


    public DiaryFragment() {
        // Required empty public constructor
    }

    public void validate() {
        String descr = tvContent.getText().toString().trim();
        if (TextUtils.isEmpty(descr)) {
            tvContent.requestFocus();
            tvContent.setError("You haven't entered any comments");
        } else {
            addDiary(descr, _listModel.getStudent_id());
        }

    }

    public void addDiary(String descr, String student_id) {
        showSweetDialog("Add Diary", "Sending Diary. Please wait...", SweetAlertDialog.PROGRESS_TYPE);
        DiaryRequests service = ServiceGenerator.createService(DiaryRequests.class);
        Call<ListResponse<Diary>> call = service.newDiary(descr, student_id);
        call.enqueue(new Callback<ListResponse<Diary>>() {
            @Override
            public void onResponse(Call<ListResponse<Diary>> call, Response<ListResponse<Diary>> response) {
                _sweetAlertDialog.dismissWithAnimation();
                if (response.body() != null) {
                    String id = diaryList.get(position).getStudent_id();
                    if (TextUtils.equals(response.body().getStatus(), "success")) {
                        showToast(response.body().getMessage());
                    } else {
                        showToast(response.body().getMessage());
                        showSweetDialog("Failed!", "Sending Diary failed.", SweetAlertDialog.ERROR_TYPE);
                    }
                } else {
                    showToast("No response from server");
                }

            }

            @Override
            public void onFailure(Call<ListResponse<Diary>> call, Throwable t) {

            }
        });

    }

    public static DiaryFragment newInstance(ListModel listModel) {
        DiaryFragment fragment = new DiaryFragment();
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
        View view = inflater.inflate(R.layout.fragment_diary, container, false);
        ButterKnife.bind(this, view);
//        FloatingActionButton myFab = (FloatingActionButton) view.findViewById(R.id.fab);
//        myFab.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                showdialog();
//            }
//        });
        simpleSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                simpleSwipeRefreshLayout.setRefreshing(false);
                prepareDiaryData(_listModel.getStudent_id());
            }
        });
        return view;
    }


    private void showdialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_add_diary, null);
        tvContent = (EditText) dialogView.findViewById(R.id.tv_content);
        sendDiary = (ImageButton) dialogView.findViewById(R.id.send_diary);
        if (sendDiary != null) {
            sendDiary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    validate();
                }
            });
        }
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();

    }

    @Override
    public void onResume() {
        super.onResume();
        prepareDiaryData(_listModel.getStudent_id());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.position = position;
        Diary diary = diaryList.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        diaryAdapter = new DiaryAdapter(getActivity(), diaryList);
        recyclerView.setAdapter(diaryAdapter);
    }

    private void prepareDiaryData(String id) {
        showSweetDialog("Fetching Diary", "Please Wait. Fetching Diary info...", SweetAlertDialog.PROGRESS_TYPE);
        DiaryRequests service = ServiceGenerator.createService(DiaryRequests.class);
        Call<ListResponse<Diary>> call = service.getDiary(id);
        call.enqueue(new Callback<ListResponse<Diary>>() {
            @Override
            public void onResponse(Call<ListResponse<Diary>> call, Response<ListResponse<Diary>> response) {
                _sweetAlertDialog.dismissWithAnimation();
                try {
                    Log.e("fees", gson.toJson(response.body()));
                    Log.e("Status", response.body().getStatus());
                    if (TextUtils.equals(response.body().getStatus(), "success")) {
                        ArrayList<Diary> response1 = response.body().getList();
                        diaryList.clear();
                        diaryList.addAll(response1);
                        Log.e("Fees", gson.toJson(response.body()));
                        diaryAdapter = new DiaryAdapter(getContext(), diaryList);
                        recyclerView.setAdapter(diaryAdapter);
                    } else {
                        showToast("Please try again");
                        showSweetDialog("Failed!", "Failed fetching diary info", SweetAlertDialog.ERROR_TYPE);
                    }
                } catch (Exception e) {
                    diaryList.clear();
                    diaryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ListResponse<Diary>> call, Throwable t) {
                Log.e("Diary", t.getMessage());
                _sweetAlertDialog.dismissWithAnimation();
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
