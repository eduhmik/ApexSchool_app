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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.Retrofit.HomeworkRequests;
import com.example.eduh_mik.schoolconnect2.Retrofit.ListResponse;
import com.example.eduh_mik.schoolconnect2.Retrofit.ServiceGenerator;
import com.example.eduh_mik.schoolconnect2.adapters.HomeworkAdapter;
import com.example.eduh_mik.schoolconnect2.base.BaseFragment;
import com.example.eduh_mik.schoolconnect2.interfaces.OnFragmentInteractionListener;
import com.example.eduh_mik.schoolconnect2.models.Homework;
import com.example.eduh_mik.schoolconnect2.models.ListModel;
import com.example.eduh_mik.schoolconnect2.models.Subject;
import com.example.eduh_mik.schoolconnect2.tools.SweetAlertDialog;
import com.google.gson.Gson;

import java.util.ArrayList;

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
    private ArrayList<Homework> homeworkList = new ArrayList<>();
    private ArrayList<Subject> subjectArrayList = new ArrayList<>();
    private ListModel _listModel;
    Spinner spinnerSunjects;
    EditText etContent;
    ImageButton imageButton;
    String itemSelected, texts;

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
                //subjectsData();
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

    public void validate() {
        String description = etContent.getText().toString().trim();

        if (TextUtils.isEmpty(description)) {
            etContent.requestFocus();
            etContent.setError("Please enter homework description");
        } else {
            addHomework(description, _listModel.getStudent_id());
        }
    }

    private void addHomework(String descr, String student_id) {
        showSweetDialog("Add Homework", "Adding homework. Please wait", SweetAlertDialog.PROGRESS_TYPE);
        HomeworkRequests service = ServiceGenerator.createService(HomeworkRequests.class);
        Call<ListResponse<Homework>> call = service.addHomework(descr, student_id);
        call.enqueue(new Callback<ListResponse<Homework>>() {
            @Override
            public void onResponse(Call<ListResponse<Homework>> call, Response<ListResponse<Homework>> response) {
                _sweetAlertDialog.dismissWithAnimation();
                if (response.body() != null) {
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
            public void onFailure(Call<ListResponse<Homework>> call, Throwable t) {
                Log.e("Error", t.getMessage());

            }
        });
    }
//    private void subjectsData() {
//        SubjectsRequests service = ServiceGenerator.createService(SubjectsRequests.class);
//        Call<ArrayList<Subject>> call = service.getSubjects();
//        call.enqueue(new Callback<ArrayList<Subject>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Subject>> call, Response<ArrayList<Subject>> response) {
//                subjectArrayList.addAll(response.body());
//                ArrayList<String> arrayList = new ArrayList<>();
//                for (Subject subject1 : subjectArrayList) {
//                    arrayList.add(subject1.getSname());
//                }
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
//                        android.R.layout.simple_dropdown_item_1line, arrayList.toArray(new String[0]));
//                spinnerSunjects.setAdapter(adapter);
//                spinnerSunjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        itemSelected = parent.getItemAtPosition(position).toString();
//                        if (itemSelected.equals("ENGLISH")) {
//                            texts = "ENGLISH";
//                            //etEngMark.setText(homework.getDescr());
//                        } else if (itemSelected.equals("MATHEMATICS")) {
//                            texts = "MATHEMATICS";
//                            //etMathMark.setText(homework.getDescr());
//                        }
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Subject>> call, Throwable t) {
//
//            }
//        });
//    }

    private void prepareContactData(String id) {
        HomeworkRequests service = ServiceGenerator.createService(HomeworkRequests.class);
        Call<ListResponse<Homework>> call = service.getHomework(id);
        call.enqueue(new Callback<ListResponse<Homework>>() {
            @Override
            public void onResponse(Call<ListResponse<Homework>> call, Response<ListResponse<Homework>> response) {
                Log.e("fees", gson.toJson(response.body()));
                Log.e("Status", response.body().getStatus());
                try {
                    if (TextUtils.equals(response.body().getStatus(), "success")) {
                        ArrayList<Homework> response1 = response.body().getList();
                        homeworkList.clear();
                        homeworkList.addAll(response1);
                        Log.e("Fees", gson.toJson(response.body()));
                        homeworkAdapter = new HomeworkAdapter(getContext(), homeworkList);
                        recyclerView.setAdapter(homeworkAdapter);
                    } else {
                        showToast("Please try again");
                    }
                } catch (Exception e){
                    homeworkList.clear();
                    homeworkAdapter.notifyDataSetChanged();
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
        final View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.add_homework, null);
        etContent = (EditText)  dialogView.findViewById(R.id.tv_comment);
        imageButton = (ImageButton) dialogView.findViewById(R.id.send_homework);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

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
