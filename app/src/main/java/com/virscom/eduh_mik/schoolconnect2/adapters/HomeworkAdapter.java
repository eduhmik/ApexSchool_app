package com.virscom.eduh_mik.schoolconnect2.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.virscom.eduh_mik.schoolconnect2.R;
import com.virscom.eduh_mik.schoolconnect2.adapters.viewholders.HomeworkVH;
import com.virscom.eduh_mik.schoolconnect2.models.Homework;

import java.util.List;

import butterknife.OnClick;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkVH> {


    private Context context;
    private List<Homework> homeworkList;


    public HomeworkAdapter(Context context, List<Homework> homeworkList) {
        this.homeworkList = homeworkList;
        this.context = context;
    }

    @Override
    public HomeworkVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.homework_layout_item, parent, false);
        return new HomeworkVH(context, itemView);
    }

    @Override
    public void onBindViewHolder(final HomeworkVH holder, int position) {
        holder.bind(homeworkList.get(position));

    }

    @Override
    public int getItemCount() {
        return homeworkList.size();
    }

    @OnClick(R.id.card_diary)
    public void onViewClicked() {
    }
}