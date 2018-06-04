package com.example.eduh_mik.schoolconnect2.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.models.Notices;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Eduh_mik on 4/21/2018.
 */

public class NoticesViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.tv_description)
    TextView tvDescription;

    private Context _context;

    public NoticesViewHolder(Context context, View view) {
        super(view);
        this._context = context;
        ButterKnife.bind(this, view);
    }

    public void bind(Notices notices) {
        tvNotice.setText(notices.getTitle());
        tvDescription.setText(notices.getDescription());
        tvDate.setText(notices.getDate());
        tvDay.setText(notices.getDay());
        tvTime.setText(notices.getTime());

        //Calendar calendarNow = Calendar.getInstance();
        //tvStartDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(calendarNow));
        //date = tvStartDate.toString();
    }
    @OnClick(R.id.card_notice)
    public void onCardClicked() {

    }
}
