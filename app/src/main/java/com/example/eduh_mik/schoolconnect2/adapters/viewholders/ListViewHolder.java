package com.example.eduh_mik.schoolconnect2.adapters.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.activities.StudentActivity;
import com.example.eduh_mik.schoolconnect2.appdata.AppData;
import com.example.eduh_mik.schoolconnect2.models.ListModel;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image_view_flag)
    ImageView imageViewFlag;
    @BindView(R.id.text_firstname)
    TextView textFirstname;
    @BindView(R.id.txt_lastname)
    TextView txtLastname;
//    @BindView(R.id.text_view_code)
//    TextView textViewCode;
    @BindView(R.id.card_class)
    CardView cardClass;

    private Context _context;

    public ListViewHolder(Context context, View view) {
        super(view);
        this._context = context;
        ButterKnife.bind(this, view);
    }

    public void bind(final ListModel listModel) {
        textFirstname.setText(listModel.getFirst_name());
        txtLastname.setText(listModel.getLast_name());
        //textViewCode.setText(listModel.getClass_id());
        Glide.with(_context).load(AppData.IMAGE_URL+listModel.getImage()).into(imageViewFlag);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_context, StudentActivity.class);
                Log.e("", String.valueOf(listModel));
                intent.putExtra("list", new Gson().toJson(listModel));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                _context.startActivity(intent);
            }
        });
    }

}