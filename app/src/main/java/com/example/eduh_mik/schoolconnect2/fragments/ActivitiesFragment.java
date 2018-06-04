package com.example.eduh_mik.schoolconnect2.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.Retrofit.ActivitiesRequest;
import com.example.eduh_mik.schoolconnect2.Retrofit.ListResponse;
import com.example.eduh_mik.schoolconnect2.Retrofit.ServiceGenerator;
import com.example.eduh_mik.schoolconnect2.adapters.ActivitiesAdapter;
import com.example.eduh_mik.schoolconnect2.base.BaseFragment;
import com.example.eduh_mik.schoolconnect2.interfaces.OnFragmentInteractionListener;
import com.example.eduh_mik.schoolconnect2.models.Activities;
import com.example.eduh_mik.schoolconnect2.tools.SweetAlertDialog;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Eduh_mik on 4/21/2018.
 */

public class ActivitiesFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    //Unbinder unbinder;
    public Calendar calendarDate, calendarTime;
    public boolean pickDateSet, pickTimeSet;
    TextView tvPickDate, tvPickTime;
    LinearLayout linEndDate, linEndTime;
    private OnFragmentInteractionListener mListener;
    private ActivitiesAdapter activitiesAdapter;
    private ArrayList<Activities> activitiesList = new ArrayList<>();

    EditText etActivity1, etActivity2, etActivity3, etActivity4;
    ImageButton btnActivities;



    public ActivitiesFragment() {
        // Required empty public constructor
    }

    public static ActivitiesFragment newInstance() {
        return new ActivitiesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activities, container, false);
        ButterKnife.bind(this, view);
        FloatingActionButton myFab = (FloatingActionButton) view.findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                showdialog();
            }
        });
        return view;
    }
    public void validate(){
        String act1 = etActivity1.getText().toString().trim();
        String act2 = etActivity2.getText().toString().trim();
        String act3 = etActivity3.getText().toString().trim();
        String act4 = etActivity4.getText().toString().trim();
        String date = tvPickDate.getText().toString().trim();
        String time = tvPickTime.getText().toString().trim();

        if(TextUtils.isEmpty(act1)){
            etActivity1.requestFocus();
            etActivity1.setError("Please enter an activity");
        } else if (!pickDateSet) {
            tvPickTime.requestFocus();
            showToast("Set Activities Date");
        } else if (!pickTimeSet) {
            tvPickTime.requestFocus();
            showToast("Set Activities Time");
        } else {
            addActivity(act1, act2, act3, act4, date, time);
        }

    }

    public void addActivity(String act1, String act2, String act3, String act4, String date, String time){
        showSweetDialog("Add Activity", "Adding Activity. Please wait...", SweetAlertDialog.PROGRESS_TYPE);
        ActivitiesRequest service = ServiceGenerator.createService(ActivitiesRequest.class);
        Call<ListResponse<Activities>> call = service.addActivies(act1, act2, act3, act4, date, time);
        call.enqueue(new Callback<ListResponse<Activities>>() {
            @Override
            public void onResponse(Call<ListResponse<Activities>> call, Response<ListResponse<Activities>> response) {
                _sweetAlertDialog.dismissWithAnimation();
                if (response.body() != null) {
                    if (TextUtils.equals(response.body().getStatus(), "success")) {
                        showToast(response.body().getMessage());
                    } else {
                        showToast(response.body().getMessage());
                        showSweetDialog("Failed!", "Sending Activities failed.", SweetAlertDialog.ERROR_TYPE);
                    }
                } else {
                    showToast("No response from server");
                }

            }

            @Override
            public void onFailure(Call<ListResponse<Activities>> call, Throwable t) {
                Log.e("Activities", t.getMessage());
                _sweetAlertDialog.dismissWithAnimation();
            }
        });
    }
    private void showdialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_activity, null);
        etActivity1 = (EditText) dialogView.findViewById(R.id.et_activity1);
        etActivity2 = (EditText) dialogView.findViewById(R.id.et_activity2);
        etActivity3 = (EditText) dialogView.findViewById(R.id.et_activity3);
        etActivity4 = (EditText) dialogView.findViewById(R.id.et_activity4);
        tvPickDate = (TextView) dialogView.findViewById(R.id.tv_pickDate);
        tvPickTime = (TextView) dialogView.findViewById(R.id.tv_PickTime);
        linEndDate = (LinearLayout) dialogView.findViewById(R.id.lin_EndDate);
        linEndTime = (LinearLayout) dialogView.findViewById(R.id.lin_endTime);
        linEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate(1);
            }
        });
        linEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickTime(1);
            }
        });
        btnActivities = (ImageButton) dialogView.findViewById(R.id.btn_submit_activities);
        btnActivities.setOnClickListener(new View.OnClickListener() {
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
    public void onResume() {
        super.onResume();
        prepareActivitiesData();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getActivity());
        //sectionsAdapter = new SectionsAdapter(sections);
        recyclerView.setLayoutManager(layoutManager);
        prepareActivitiesData();
    }
    private void pickDate(int i) {
        if (i == 1) {
            int mYear, mMonth, mDay;
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            pickDateSet = true;
                            calendarDate = Calendar.getInstance();
                            calendarDate.set(Calendar.YEAR, year);
                            calendarDate.set(Calendar.MONTH, month);
                            calendarDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            String pickDate = year + "-" + (++month) + "-" + dayOfMonth;
                            tvPickDate.setText(pickDate);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    private void pickTime(int i) {
        if (i == 1) {
            Calendar mCurrentTime = Calendar.getInstance();
            final int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
            final int minute = mCurrentTime.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            pickTimeSet = true;
                            String time = selectedHour + ":" + selectedMinute;
                            tvPickTime.setText(time);
                            calendarTime = Calendar.getInstance();
                            calendarTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                            calendarTime.set(Calendar.MINUTE, selectedMinute);
                            calendarTime.set(Calendar.SECOND, 0);
                        }
                    }, hour, minute, true);
            timePickerDialog.setTitle("Select Time");
            timePickerDialog.show();
        }
    }
    private void prepareActivitiesData() {
        ActivitiesRequest service= ServiceGenerator.createService(ActivitiesRequest.class);
        Call<ListResponse<Activities>> call = service.getActivities();
        call.enqueue(new Callback<ListResponse<Activities>>() {
            @Override
            public void onResponse(Call<ListResponse<Activities>> call, Response<ListResponse<Activities>> response) {
                try {
                    Log.e("fees", gson.toJson(response.body()));
                    Log.e("Status", response.body().getStatus());
                    if (TextUtils.equals(response.body().getStatus(), "success")) {
                        ArrayList<Activities> response1 = response.body().getList();
                        activitiesList.clear();
                        activitiesList.addAll(response1);
                        Log.e("Fees", gson.toJson(response.body()));
                        activitiesAdapter = new ActivitiesAdapter(getContext(), activitiesList);
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
            public void onFailure(Call<ListResponse<Activities>> call, Throwable t) {
                Log.e("", t.getMessage());

            }
        });
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
