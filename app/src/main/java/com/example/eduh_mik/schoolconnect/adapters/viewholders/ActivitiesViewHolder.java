package com.example.eduh_mik.schoolconnect.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.eduh_mik.schoolconnect.R;
import com.example.eduh_mik.schoolconnect.models.Activities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Eduh_mik on 4/21/2018.
 */

public class ActivitiesViewHolder extends RecyclerView.ViewHolder {
    //public TextView tvName,tvDescription, tvStartDate;
    @BindView(R.id.tv_Name)
    TextView tvName;
    @BindView(R.id.tv_Description)
    TextView tvDescription;
    @BindView(R.id.tv_startDate)
    TextView tvStartDate;
    @BindView(R.id.et_activity1)
    EditText etActivity1;
    @BindView(R.id.et_activity2)
    EditText etActivity2;
    @BindView(R.id.et_activity3)
    EditText etActivity3;





    private Context _context;

    public ActivitiesViewHolder(Context context, View view) {
        super(view);
        this._context = context;
        ButterKnife.bind(this, view);
    }

    public void bind(Activities activities) {
        tvName.setText(activities.getName());
        tvDescription.setText(activities.getGrade());
        etActivity1.setText(activities.getActivities1());
        etActivity2.setText(activities.getActivities2());
        etActivity3.setText(activities.getActivities3());

        //Calendar calendarNow = Calendar.getInstance();
        //tvStartDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(calendarNow));
        //date = tvStartDate.toString();
    }
    @OnClick(R.id.card_activities)
    public void onCardClicked() {

    }
}

