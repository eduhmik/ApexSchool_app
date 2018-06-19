package com.virscom.eduh_mik.schoolconnect2.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.virscom.eduh_mik.schoolconnect2.R;
import com.virscom.eduh_mik.schoolconnect2.adapters.viewholders.NoticesViewHolder;
import com.virscom.eduh_mik.schoolconnect2.models.Notices;

import java.util.ArrayList;

/**
 * Created by Eduh_mik on 4/21/2018.
 */

public class NoticesAdapter extends RecyclerView.Adapter<NoticesViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Notices> noticesList, finalNoticeList;


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
        return null == noticesList ? 0 : noticesList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                noticesList = (ArrayList<Notices>) results.values;
                NoticesAdapter.this.notifyDataSetChanged();
            }
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<Notices> filteredResults = null;
                if(constraint.length() == 0){
                    filteredResults = finalNoticeList;
                } else {
                    filteredResults = getFilteredResults(constraint.toString());
                }
                FilterResults results = new FilterResults();
                results.values = filteredResults;
                return results;
            }


        };
    }
    protected ArrayList<Notices> getFilteredResults(String constraint){
        ArrayList<Notices> results = new ArrayList<>();
        for (Notices notices : finalNoticeList){
            if(notices.getTitle().toString().toLowerCase().contains(constraint.toLowerCase())){
                results.add(notices);
            }
        }
        return results;
    }
 }