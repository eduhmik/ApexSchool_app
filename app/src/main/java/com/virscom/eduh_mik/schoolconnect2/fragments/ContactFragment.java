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
import android.widget.EditText;
import android.widget.ImageButton;

import com.virscom.eduh_mik.schoolconnect2.R;
import com.virscom.eduh_mik.schoolconnect2.Retrofit.ContactsRequests;
import com.virscom.eduh_mik.schoolconnect2.Retrofit.ListResponse;
import com.virscom.eduh_mik.schoolconnect2.Retrofit.ServiceGenerator;
import com.virscom.eduh_mik.schoolconnect2.adapters.ContactAdapter;
import com.virscom.eduh_mik.schoolconnect2.base.BaseFragment;
import com.virscom.eduh_mik.schoolconnect2.interfaces.OnFragmentInteractionListener;
import com.virscom.eduh_mik.schoolconnect2.models.Contact;
import com.virscom.eduh_mik.schoolconnect2.tools.SweetAlertDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Eduh_mik on 4/21/2018.
 */

public class ContactFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.simpleSwipeRefreshLayout)
    SwipeRefreshLayout simpleSwipeRefreshLayout;
    //Unbinder unbinder;
    private OnFragmentInteractionListener mListener;
    private ContactAdapter contactAdapter;
    private ArrayList<Contact> contactList = new ArrayList<>();
    EditText etRole, etName, etPhone, etPhone2, etEmail;
    ImageButton imageButton;


    public ContactFragment() {
        // Required empty public constructor
    }

    public static ContactFragment newInstance() {
        return new ContactFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
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
                prepareContactData();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        prepareContactData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        contactAdapter = new ContactAdapter(getActivity(), contactList);
        recyclerView.setAdapter(contactAdapter);
        prepareContactData();
    }

    public void validate() {
        String name = etName.getText().toString().trim();
        String role = etRole.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String phone2 = etPhone2.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etName.requestFocus();
            etName.setError("Please enter full name");
        } else if (TextUtils.isEmpty(role)) {
            etRole.requestFocus();
            etRole.setError("Please enter role");
        } else if (TextUtils.isEmpty(phone)) {
            etPhone.requestFocus();
            etPhone.setError("Please enter role");
        } else if (TextUtils.isEmpty(email)) {
            etEmail.requestFocus();
            etEmail.setError("Please enter role");
        } else {
            addContact(role, name, phone, phone2, email);
        }
    }

    public void addContact(String role, String name, String phone, String phone2, String email) {
        showSweetDialog("Add Contact", "Adding contact. Please wait...", SweetAlertDialog.PROGRESS_TYPE);
        ContactsRequests service = ServiceGenerator.createService(ContactsRequests.class);
        Call<ListResponse<Contact>> call = service.addContacts(role, name, phone, phone2, email);
        call.enqueue(new Callback<ListResponse<Contact>>() {
            @Override
            public void onResponse(Call<ListResponse<Contact>> call, Response<ListResponse<Contact>> response) {
                _sweetAlertDialog.dismissWithAnimation();
                if (response.body() != null) {
                    if (TextUtils.equals(response.body().getStatus(), "success")) {
                        showToast(response.body().getMessage());
                    } else {
                        showToast(response.body().getMessage());
                        showSweetDialog("Failed!", "Sending Contact failed.", SweetAlertDialog.ERROR_TYPE);
                    }
                } else {
                    showToast("No response from server");
                }
            }

            @Override
            public void onFailure(Call<ListResponse<Contact>> call, Throwable t) {
                Log.e("Conacts", t.getMessage());
                _sweetAlertDialog.dismissWithAnimation();
            }
        });
    }


    private void prepareContactData() {
        simpleSwipeRefreshLayout.setRefreshing(true);
        ContactsRequests service = ServiceGenerator.createService(ContactsRequests.class);
        Call<ListResponse<Contact>> call = service.getContacts();
        call.enqueue(new Callback<ListResponse<Contact>>() {
            @Override
            public void onResponse(Call<ListResponse<Contact>> call, Response<ListResponse<Contact>> response) {
                simpleSwipeRefreshLayout.setRefreshing(false);
                try {
                    Log.e("fees", gson.toJson(response.body()));
                    Log.e("Status", response.body().getStatus());
                    if (TextUtils.equals(response.body().getStatus(), "success")) {
                        ArrayList<Contact> response1 = response.body().getList();
                        contactList.clear();
                        contactList.addAll(response1);
                        Log.e("Fees", gson.toJson(response.body()));
                        contactAdapter = new ContactAdapter(getContext(), contactList);
                        recyclerView.setAdapter(contactAdapter);
                    } else {
                        showToast("Please try again");
                    }
                } catch (Exception e) {
                    contactList.clear();
                    contactAdapter.notifyDataSetChanged();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ListResponse<Contact>> call, Throwable t) {
                simpleSwipeRefreshLayout.setRefreshing(false);
                Log.e("Failure", t.getMessage());
            }

        });
    }

    private void showdialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_add_contact, null);
        etEmail = (EditText) dialogView.findViewById(R.id.et_email);
        etPhone = (EditText) dialogView.findViewById(R.id.et_phonne);
        etPhone2 = (EditText) dialogView.findViewById(R.id.et_phone2);
        etRole = (EditText) dialogView.findViewById(R.id.et_role);
        etName = (EditText) dialogView.findViewById(R.id.et_name);
        imageButton = (ImageButton) dialogView.findViewById(R.id.btn_submit_contacts);
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
