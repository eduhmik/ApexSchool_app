package com.example.eduh_mik.schoolconnect.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.eduh_mik.schoolconnect.R;
import com.example.eduh_mik.schoolconnect.models.Diary;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Eduh_mik on 4/19/2018.
 */

public class DiaryViewHolder extends RecyclerView.ViewHolder {
   //public TextView tvName,tvDescription, tvStartDate;
    @BindView(R.id.tv_Name)
    TextView tvName;
    @BindView(R.id.tv_Description)
    TextView tvDescription;
    @BindView(R.id.tv_startDate)
    TextView tvStartDate;




    private Context _context;

    public DiaryViewHolder(Context context, View view) {
        super(view);
        this._context = context;
        ButterKnife.bind(this, view);
    }

    public void bind(Diary diary) {
        tvName.setText(diary.getS());
        tvDescription.setText(diary.getS1());
        tvStartDate.setText(diary.getS2());
        //Glide.with(_context).load(R.drawable.blue_button_background).into(ImageView);
    }
    @OnClick(R.id.card_diary)
    public void onCardClicked() {

    }
}
