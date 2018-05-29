package com.example.eduh_mik.schoolconnect2.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.adapters.viewholders.FeesVH;
import com.example.eduh_mik.schoolconnect2.models.Fees;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class FeesAdapter extends RecyclerView.Adapter<FeesVH> {

    private Context context;
    private List<Fees> feesList;


    public FeesAdapter( Context context, ArrayList<Fees> feesList) {
        this.feesList = feesList;
        this.context = context;
    }

    @Override
    public FeesVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fees_layout, parent, false);
        return new FeesVH(context, itemView);
    }

    @Override
    public void onBindViewHolder(final FeesVH holder, int position) {
        holder.bind(feesList.get(position));

    }

    @Override
    public int getItemCount() {
        return feesList.size();
    }

    @OnClick(R.id.card_exam)
    public void onViewClicked() {
    }
}