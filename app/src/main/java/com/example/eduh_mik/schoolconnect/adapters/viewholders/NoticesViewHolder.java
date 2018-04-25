package com.example.eduh_mik.schoolconnect.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.eduh_mik.schoolconnect.R;
import com.example.eduh_mik.schoolconnect.models.Notices;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Eduh_mik on 4/21/2018.
 */

public class NoticesViewHolder extends RecyclerView.ViewHolder {
    //public TextView tvName,tvDescription, tvStartDate;
    @BindView(R.id.tv_notice)
    TextView tvName;
    @BindView(R.id.tv_Description)
    TextView tvDescription;
    @BindView(R.id.tv_startDate)
    TextView tvStartDate;




    private Context _context;

    public NoticesViewHolder(Context context, View view) {
        super(view);
        this._context = context;
        ButterKnife.bind(this, view);
    }

    public void bind(Notices notices) {
        tvName.setText(notices.getNotice());
        tvDescription.setText(notices.getNotDescription());
        tvStartDate.setText(notices.getNotDate());
        //Calendar calendarNow = Calendar.getInstance();
        //tvStartDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(calendarNow));
        //date = tvStartDate.toString();
    }
    @OnClick(R.id.card_notice)
    public void onCardClicked() {

    }
}
