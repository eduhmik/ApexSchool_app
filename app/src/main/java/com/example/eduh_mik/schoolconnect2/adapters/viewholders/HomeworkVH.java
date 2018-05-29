package com.example.eduh_mik.schoolconnect2.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.models.Homework;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeworkVH extends RecyclerView.ViewHolder {
    //public TextView tvName,tvDescription, tvStartDate;
    @BindView(R.id.hw_date)
    TextView hwDate;
    @BindView(R.id.hw_maths)
    TextView hwMaths;
    @BindView(R.id.hw_english)
    TextView hwEnglish;
    @BindView(R.id.hw_kisw)
    TextView hwKisw;
    @BindView(R.id.hw_scie)
    TextView hwScie;
    @BindView(R.id.hw_sst)
    TextView hwSst;
    @BindView(R.id.hw_comp)
    TextView hwComp;
    @BindView(R.id.hw_done)
    TextView hwDone;




    private Context _context;

    public HomeworkVH(Context context, View view) {
        super(view);
        this._context = context;
        ButterKnife.bind(this, view);
    }

    public void bind(Homework homework) {
        hwDate.setText((homework.getDate()));
        hwMaths.setText(homework.getDescr());
        hwEnglish.setText(homework.getDescr());
    }

}
