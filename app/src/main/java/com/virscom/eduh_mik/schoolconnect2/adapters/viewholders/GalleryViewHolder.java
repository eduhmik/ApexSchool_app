package com.virscom.eduh_mik.schoolconnect2.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.virscom.eduh_mik.schoolconnect2.R;
import com.virscom.eduh_mik.schoolconnect2.appdata.AppData;
import com.virscom.eduh_mik.schoolconnect2.models.Gallery;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eduh_mik on 4/23/2018.
 */

public class GalleryViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.im_gallery)
    ImageView imageView;
    @BindView(R.id.tv_text)
    TextView tvText;

    private Context _context;

    public GalleryViewHolder(Context context, View view) {
        super(view);
        this._context = context;
        ButterKnife.bind(this, view);
    }

    public void bind(Gallery gallery) {
        tvText.setText(gallery.getDescr());
        Log.e("image", AppData.GALLERY_URL+gallery.getImage());
        Glide.with(_context).load(AppData.GALLERY_URL+gallery.getImage()).into(imageView);
    }

}