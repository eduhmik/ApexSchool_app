package com.example.eduh_mik.schoolconnect2.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.adapters.viewholders.ExamVH;
import com.example.eduh_mik.schoolconnect2.models.Exam;

import java.util.ArrayList;

public class ExamAdapter extends RecyclerView.Adapter<ExamVH> {

    private Context context;
    private ArrayList<Exam> examList;


    public ExamAdapter(Context context, ArrayList<Exam> examList) {
        this.examList = examList;
        this.context = context;
    }

    @Override
    public ExamVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exam_layout_item, parent, false);
        return new ExamVH(context, itemView);
    }

    @Override
    public void onBindViewHolder(final ExamVH holder, int position) {
        holder.bind(examList.get(position));

    }

    @Override
    public int getItemCount() {
        return examList.size();
    }

}
