package com.virscom.eduh_mik.schoolconnect2.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.virscom.eduh_mik.schoolconnect2.models.Diary;
import com.virscom.eduh_mik.schoolconnect2.adapters.viewholders.DiaryViewHolder;
import com.virscom.eduh_mik.schoolconnect2.R;

import java.util.List;

/**
 * Created by Eduh_mik on 4/19/2018.
 */

public class DiaryAdapter extends RecyclerView.Adapter<DiaryViewHolder>  {

    private Context context;
    private List<Diary> diaryList;


    public DiaryAdapter(Context context, List<Diary> diaryList) {
        this.diaryList = diaryList;
        this.context = context;
    }

    @Override
    public DiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_diary_item, parent, false);
        return new DiaryViewHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(final DiaryViewHolder holder, int position) {
        holder.bind(diaryList.get(position));

    }

    @Override
    public int getItemCount() {
        return diaryList.size();
    }

}