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
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.eduh_mik.schoolconnect.R;
import com.example.eduh_mik.schoolconnect.adapters.DiaryAdapter;
import com.example.eduh_mik.schoolconnect.interfaces.OnFragmentInteractionListener;
import com.example.eduh_mik.schoolconnect.models.Diary;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eduh_mik on 4/19/2018.
 */

public class DiaryFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.simple_spinner_kids)
    Spinner simpleSpinnerKids;
    //Unbinder unbinder;
    private OnFragmentInteractionListener mListener;
    private DiaryAdapter diaryAdapter;
    private List<Diary> diaryList = new ArrayList<>();


    public DiaryFragment() {
        // Required empty public constructor
    }

    public static DiaryFragment newInstance() {
        return new DiaryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diary, container, false);
        ButterKnife.bind(this, view);
        simpleSpinnerKids = view.findViewById(R.id.simple_spinner_kids);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.kids));
        simpleSpinnerKids.setAdapter(spinnerArrayAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        prepareDiaryData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        diaryAdapter = new DiaryAdapter(getActivity(), diaryList);
        recyclerView.setAdapter(diaryAdapter);
        prepareDiaryData();
    }

    private void prepareDiaryData() {
        Diary diary = new Diary("Jayden Munene", "Littel Gems", "01-03-18");
        diaryList.add(diary);
        diary = new Diary("Jayden Munene", "Littel Gems", "01-03-18");
        diaryList.add(diary);
        diary = new Diary("Jayden Munene", "Littel Gems", "01-03-18");
        diaryList.add(diary);
        diary = new Diary("Jayden Munene", "Littel Gems", "01-03-18");
        diaryList.add(diary);
        diary = new Diary("Jayden Munene", "Littel Gems", "01-03-18");
        diaryList.add(diary);
        diary = new Diary("Jayden Munene", "Littel Gems", "01-03-18");
        diaryList.add(diary);
        diary = new Diary("Jayden Munene", "Littel Gems", "01-03-18");
        diaryList.add(diary);
        diary = new Diary("Jayden Munene", "Littel Gems", "01-03-18");
        diaryList.add(diary);


        diaryAdapter.notifyDataSetChanged();
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



//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//    }

}
