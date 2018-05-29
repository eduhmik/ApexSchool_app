package com.example.eduh_mik.schoolconnect2.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.adapters.viewholders.ListViewHolder;
import com.example.eduh_mik.schoolconnect2.models.ListModel;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    private Context context;
    private ArrayList<ListModel> galleryList;

    public ListAdapter(Context context, ArrayList<ListModel> galleryList) {
        this.galleryList = galleryList;
        this.context = context;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_student, parent, false);
        return new ListViewHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, int position) {
        holder.bind(galleryList.get(position));

    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

}

