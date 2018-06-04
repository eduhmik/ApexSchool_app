package com.example.eduh_mik.schoolconnect2.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.adapters.viewholders.NoticesViewHolder;
import com.example.eduh_mik.schoolconnect2.models.Notices;

import java.util.ArrayList;

/**
 * Created by Eduh_mik on 4/21/2018.
 */

public class NoticesAdapter extends RecyclerView.Adapter<NoticesViewHolder> {

    private Context context;
    private ArrayList<Notices> noticesList;


    public NoticesAdapter(Context context, ArrayList<Notices> noticesList) {
        this.noticesList = noticesList;
        this.context = context;
    }

    @Override
    public NoticesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_noticeboard_item, parent, false);
        return new NoticesViewHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(final NoticesViewHolder holder, int position) {
        holder.bind(noticesList.get(position));

    }

    @Override
    public int getItemCount() {
        return noticesList.size();
    }
}