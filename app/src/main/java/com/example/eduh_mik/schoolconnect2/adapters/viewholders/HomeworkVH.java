package com.example.eduh_mik.schoolconnect2.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.models.Homework;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeworkVH extends RecyclerView.ViewHolder {
    //public TextView tvName,tvDescription, tvStartDate;

    @BindView(R.id.tv_startDate)
    TextView tvStartDate;
    @BindView(R.id.et_day)
    TextView etDay;
    @BindView(R.id.tv_Name)
    TextView tvName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.card_diary)
    CardView cardDiary;

    private Context _context;

    public HomeworkVH(Context context, View view) {
        super(view);
        this._context = context;
        ButterKnife.bind(this, view);
    }

    public void bind(Homework homework) {
        tvStartDate.setText(homework.getDate());
        etDay.setText(homework.getDay());
        tvContent.setText(homework.getDescr());
    }
    @OnClick(R.id.card_diary)
    public void onViewClicked() {
    }

}
