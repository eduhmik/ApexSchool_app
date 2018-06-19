package com.virscom.eduh_mik.schoolconnect2.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.virscom.eduh_mik.schoolconnect2.R;
import com.virscom.eduh_mik.schoolconnect2.adapters.viewholders.ActivitiesViewHolder;
import com.virscom.eduh_mik.schoolconnect2.models.Activities;

import java.util.ArrayList;

/**
 * Created by Eduh_mik on 4/21/2018.
 */

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesViewHolder> {

    private Context context;
    private ArrayList<Activities> activitiesList;


    public ActivitiesAdapter(Context context, ArrayList<Activities> activitiesList) {
        this.activitiesList = activitiesList;
        this.context = context;
    }

    @Override
    public ActivitiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_activities, parent, false);
        return new ActivitiesViewHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(final ActivitiesViewHolder holder, int position) {
        holder.bind(activitiesList.get(position));

    }

    @Override
    public int getItemCount() {
        return activitiesList.size();
    }
}
