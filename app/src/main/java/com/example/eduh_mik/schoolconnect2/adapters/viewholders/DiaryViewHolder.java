package com.example.eduh_mik.schoolconnect2.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.models.Diary;

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
    @BindView(R.id.tv_class)
    TextView tvDescription;
    @BindView(R.id.tv_startDate)
    TextView tvStartDate;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.et_day)
    TextView etDay;




    private Context _context;

    public DiaryViewHolder(Context context, View view) {
        super(view);
        this._context = context;
        ButterKnife.bind(this, view);
    }

    public void bind(Diary diary) {
        tvName.setText(diary.getFirst_name()+" "+diary.getLast_name());
        tvContent.setText(diary.getDescription());
        tvDescription.setText(diary.getClass_id());
        tvStartDate.setText(diary.getDate());
        etDay.setText(diary.getDay());
    }
    @OnClick(R.id.card_diary)
    public void onCardClicked() {

    }
}
