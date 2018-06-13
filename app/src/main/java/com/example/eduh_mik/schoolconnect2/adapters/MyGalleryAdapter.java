package com.example.eduh_mik.schoolconnect2.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.adapters.viewholders.MyGalleryViewHolder;
import com.example.eduh_mik.schoolconnect2.models.MyGallery;

import java.util.ArrayList;

public class MyGalleryAdapter extends RecyclerView.Adapter<MyGalleryViewHolder>  {

private Context context;
private ArrayList<MyGallery> galleryList;


public MyGalleryAdapter(Context context, ArrayList<MyGallery> galleryList) {
        this.galleryList = galleryList;
        this.context = context;
        }

@Override
public MyGalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.layout_gallery_item, parent, false);
        return new MyGalleryViewHolder(context, itemView);
        }

@Override
public void onBindViewHolder(final MyGalleryViewHolder holder, int position) {
        holder.bind(galleryList.get(position));

        }

@Override
public int getItemCount() {
        return galleryList.size();
        }
}
