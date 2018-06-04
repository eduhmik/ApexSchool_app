package com.example.eduh_mik.schoolconnect2.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import com.example.eduh_mik.schoolconnect2.Retrofit.ListResponse;
import com.example.eduh_mik.schoolconnect2.Retrofit.NoticesRequests;
import com.example.eduh_mik.schoolconnect2.Retrofit.ServiceGenerator;
import com.example.eduh_mik.schoolconnect2.adapters.NoticesAdapter;
import com.example.eduh_mik.schoolconnect2.base.BaseFragment;
import com.example.eduh_mik.schoolconnect2.interfaces.OnFragmentInteractionListener;
import com.example.eduh_mik.schoolconnect2.models.Notices;
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

public class NoticesFragment extends BaseFragment {
    public Calendar calendarDate, calendarTime;
    public boolean pickDateSet, pickTimeSet;
    public int interval = 1;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    //Unbinder unbinder;
    private OnFragmentInteractionListener mListener;
    ProgressDialog progressDialog;
    private NoticesAdapter noticesAdapter;
    private ArrayList<Notices> noticesList = new ArrayList<>();
    EditText etTitle, etDescr;
    TextView tvPickDate, tvPickTime;
    ImageButton imageButton;
    LinearLayout linEndDate, linEndTime;


    public NoticesFragment() {
        // Required empty public constructor
    }

    public static NoticesFragment newInstance() {
        return new NoticesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice, container, false);
        ButterKnife.bind(this, view);
        FloatingActionButton myFab = (FloatingActionButton) view.findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                showdialog();
            }
        });
        return view;
    }

    public void validate() {
        String title = etTitle.getText().toString().trim();
        String descr = etDescr.getText().toString().trim();
        String date = tvPickDate.getText().toString().trim();
        String time = tvPickTime.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            etTitle.requestFocus();
            etTitle.setError("You haven't entered a title");
        } else if (TextUtils.isEmpty(descr)) {
            etDescr.requestFocus();
            etDescr.setError("You haven't entered any description");
        } else if (!pickDateSet) {
            tvPickTime.requestFocus();
            showToast("Set Notice Date");
        } else if (!pickTimeSet) {
            tvPickTime.requestFocus();
            showToast("Set Notice Time");
        } else {
            addNotice(date, time, title, descr);
        }
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

    private void showdialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_add_notice, null);
        etTitle = (EditText) dialogView.findViewById(R.id.et_notice_title);
        etDescr = (EditText) dialogView.findViewById(R.id.et_Description);
        tvPickDate = (TextView) dialogView.findViewById(R.id.tv_pickDate);
        tvPickTime = (TextView) dialogView.findViewById(R.id.tv_PickTime);
        linEndDate = (LinearLayout) dialogView.findViewById(R.id.lin_EndDate);
        linEndTime = (LinearLayout) dialogView.findViewById(R.id.lin_endTime);
        imageButton = (ImageButton) dialogView.findViewById(R.id.btn_submit_notice);
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

    public void addNotice(String date, String time, String title, String descr) {
        showSweetDialog("Add Notice", "Adding new Notice. Please wait...", SweetAlertDialog.PROGRESS_TYPE);
        NoticesRequests service = ServiceGenerator.createService(NoticesRequests.class);
        Call<ListResponse<Notices>> call = service.addNotices(date, time, title, descr);
        call.enqueue(new Callback<ListResponse<Notices>>() {
            @Override
            public void onResponse(Call<ListResponse<Notices>> call, Response<ListResponse<Notices>> response) {
                _sweetAlertDialog.dismissWithAnimation();
                Log.e("Notices", gson.toJson(response.body()));
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
            public void onFailure(Call<ListResponse<Notices>> call, Throwable t) {
                Log.e("Notices", t.getMessage());
                _sweetAlertDialog.dismissWithAnimation();

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        prepareNoticesData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //sectionsAdapter = new SectionsAdapter(sections);
        recyclerView.setLayoutManager(layoutManager);
        prepareNoticesData();
    }

    private void prepareNoticesData() {
       progressDialog = new ProgressDialog(getActivity());
       progressDialog.setTitle("Fetching Notices");
       progressDialog.setMessage("Fetching Notices. Please wait...");
       progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
       progressDialog.show();
        NoticesRequests service = ServiceGenerator.createService(NoticesRequests.class);
        Call<ListResponse<Notices>> call = service.getNotices();
        call.enqueue(new Callback<ListResponse<Notices>>() {
            @Override
            public void onResponse(Call<ListResponse<Notices>> call, Response<ListResponse<Notices>> response) {
                    progressDialog.dismiss();
                    Log.e("fees", gson.toJson(response.body()));
                    Log.e("Status", response.body().getStatus());
                    try {
                        if (TextUtils.equals(response.body().getStatus(), "success")) {
                            ArrayList<Notices> response1 = response.body().getList();
                            noticesList.clear();
                            noticesList.addAll(response1);
                            Log.e("Fees", gson.toJson(response.body()));
                            noticesAdapter = new NoticesAdapter(getContext(), noticesList);
                            recyclerView.setAdapter(noticesAdapter);
                        }
                            else {
                                showToast("Please try again");
                                showSweetDialog("Failed!", "Failed to fetch Notices!", SweetAlertDialog.ERROR_TYPE);
                            }

                    }catch (Exception e){
                        noticesList.clear();
                        noticesAdapter.notifyDataSetChanged();
                        e.printStackTrace();
                    }
            }

            @Override
            public void onFailure(Call<ListResponse<Notices>> call, Throwable t) {
                Log.e("Notices", t.getMessage());
                progressDialog.dismiss();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
