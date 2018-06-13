package com.example.eduh_mik.schoolconnect2.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.adapters.viewholders.MyActvitiesViewHolder;
import com.example.eduh_mik.schoolconnect2.models.MyActvities;

import java.util.ArrayList;

public class MyActivitiesAdapter extends RecyclerView.Adapter<MyActvitiesViewHolder> {

    private Context context;
    private ArrayList<MyActvities> activitiesList;


    public MyActivitiesAdapter(Context context, ArrayList<MyActvities> activitiesList) {
        this.activitiesList = activitiesList;
        this.context = context;
    }

    @Override
    public MyActvitiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_activities, parent, false);
        return new MyActvitiesViewHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(final MyActvitiesViewHolder holder, int position) {
        holder.bind(activitiesList.get(position));

    }

    @Override
    public int getItemCount() {
        return activitiesList.size();
    }
}

