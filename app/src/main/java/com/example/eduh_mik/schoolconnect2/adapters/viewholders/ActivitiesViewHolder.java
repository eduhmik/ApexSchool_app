package com.example.eduh_mik.schoolconnect2.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.models.Activities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Eduh_mik on 4/21/2018.
 */

public class ActivitiesViewHolder extends RecyclerView.ViewHolder {
    //public TextView tvName,tvDescription, tvStartDate;
    @BindView(R.id.tv_startDate)
    TextView tvStartDate;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.et_activity1)
    TextView etActivity1;
    @BindView(R.id.et_activity2)
    TextView etActivity2;
    @BindView(R.id.et_activity3)
    TextView etActivity3;




    private Context _context;

    public ActivitiesViewHolder(Context context, View view) {
        super(view);
        this._context = context;
        ButterKnife.bind(this, view);
    }

    public void bind(Activities activities) {
        tvStartDate.setText(activities.getDate());
        tvDay.setText(activities.getDay());
        etActivity1.setText(activities.getAct1());
        etActivity2.setText(activities.getAct2());
        etActivity3.setText(activities.getAct3());
        //Calendar calendarNow = Calendar.getInstance();
        //tvStartDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(calendarNow));
        //date = tvStartDate.toString();
    }
    @OnClick(R.id.card_activities)
    public void onCardClicked() {

    }
}

