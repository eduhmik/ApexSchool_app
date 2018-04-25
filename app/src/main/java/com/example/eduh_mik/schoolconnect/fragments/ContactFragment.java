package com.example.eduh_mik.schoolconnect.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eduh_mik.schoolconnect.R;
import com.example.eduh_mik.schoolconnect.adapters.ContactAdapter;
import com.example.eduh_mik.schoolconnect.interfaces.OnFragmentInteractionListener;
import com.example.eduh_mik.schoolconnect.models.Contact;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eduh_mik on 4/21/2018.
 */

public class ContactFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    //Unbinder unbinder;
    private OnFragmentInteractionListener mListener;
    private ContactAdapter contactAdapter;
    private List<Contact> contactList = new ArrayList<>();


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
    private void prepareContactData() {
        Contact contact = new Contact("John Doe", "Principal", "0712345678", "johndoe@gmail.com");
        contactList.add(contact);
        contact = new Contact("Charles Darwin ", "Deputy Principal", "0712345678", "charlsdarwin@gmail.com");
        contactList.add(contact);
        contact = new Contact("Martha Rubia", "Principal's Secretary", "0712345678", "mrubia@gmail.com");
        contactList.add(contact);
        contact = new Contact("Stanley Ford", "Senior Teacher", "0712345678", "jstanley@gmail.com");
        contactList.add(contact);
        contact = new Contact("Fox Wills", "Matron", "0712345678", "willsfox@gmail.com");
        contactList.add(contact);
        contact = new Contact("Whitney Sophie", "BOG Chairperson", "0712345678", "whitney@gmail.com");
        contactList.add(contact);
        contact = new Contact("John Doe", "Principal", "0712345678", "johndoe@gmail.com");
        contactList.add(contact);
        contact = new Contact("Chuck Norris", "Head of Security", "0712345678", "norrischuck@gmail.com");
        contactList.add(contact);
        contact = new Contact("Kyler Jenner", "School Nurse", "0712345678", "kyler@gmail.com");
        contactList.add(contact);

        contactAdapter.notifyDataSetChanged();
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
