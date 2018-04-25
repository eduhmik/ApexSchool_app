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
import com.example.eduh_mik.schoolconnect.adapters.NoticesAdapter;
import com.example.eduh_mik.schoolconnect.interfaces.OnFragmentInteractionListener;
import com.example.eduh_mik.schoolconnect.models.Notices;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eduh_mik on 4/21/2018.
 */

public class NoticesFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    //Unbinder unbinder;
    private OnFragmentInteractionListener mListener;
    private NoticesAdapter noticesAdapter;
    private List<Notices> noticesList = new ArrayList<>();


    public NoticesFragment(){
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
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        prepareNoticesData();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        noticesAdapter = new NoticesAdapter(getActivity(), noticesList);
        recyclerView.setAdapter(noticesAdapter);
        prepareNoticesData();
    }
    private void prepareNoticesData() {
        Notices notices = new Notices("Graduation Little Gems", "Little Gems", "01-03-2018");
        noticesList.add(notices);
        notices = new Notices("School Trip", "Grade 1", "01-03-2018");
        noticesList.add(notices);
        notices = new Notices("School Prize giving day", "All", "01-03-2018");
        noticesList.add(notices);
        notices = new Notices("Closing day", "All", "01-03-2018");
        noticesList.add(notices);
        notices = new Notices("Opening day", "All", "01-03-2018");
        noticesList.add(notices);
        notices = new Notices("Graduation Little Gems", "Little Gems", "01-03-2018");
        noticesList.add(notices);

        noticesAdapter.notifyDataSetChanged();
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
