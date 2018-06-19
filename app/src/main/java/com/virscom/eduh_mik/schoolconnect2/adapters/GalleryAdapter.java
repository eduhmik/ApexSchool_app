package com.virscom.eduh_mik.schoolconnect2.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.virscom.eduh_mik.schoolconnect2.R;
import com.virscom.eduh_mik.schoolconnect2.adapters.viewholders.GalleryViewHolder;
import com.virscom.eduh_mik.schoolconnect2.models.Gallery;

import java.util.ArrayList;

/**
 * Created by Eduh_mik on 4/23/2018.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryViewHolder>  {

    private Context context;
    private ArrayList<Gallery> galleryList;


    public GalleryAdapter(Context context, ArrayList<Gallery> galleryList) {
        this.galleryList = galleryList;
        this.context = context;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_gallery_item, parent, false);
        return new GalleryViewHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(final GalleryViewHolder holder, int position) {
        holder.bind(galleryList.get(position));

    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

}
