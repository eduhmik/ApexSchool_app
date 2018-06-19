package com.virscom.eduh_mik.schoolconnect2.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.virscom.eduh_mik.schoolconnect2.R;
import com.virscom.eduh_mik.schoolconnect2.models.Fees;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeesVH extends RecyclerView.ViewHolder {
    //public TextView tvName,tvDescription, tvStartDate;
    @BindView(R.id.fees_year)
    TextView feesYear;
    @BindView(R.id.fees_class)
    TextView feesClass;
    @BindView(R.id.exam_term)
    TextView examTerm;
    @BindView(R.id.TextView_bal_fees)
    TextView TextViewBalFees;
    @BindView(R.id.TextView_amt)
    TextView TextViewAmt;
    @BindView(R.id.TextView_amt_paid)
    TextView TextViewAmtPaid;
    @BindView(R.id.TextView_amt_bal)
    TextView TextViewAmtBal;
    @BindView(R.id.card_exam)
    CardView cardExam;


    private Context _context;

    public FeesVH(Context context, View view) {
        super(view);
        this._context = context;
        ButterKnife.bind(this, view);
    }

    public void bind(Fees fees) {
        feesClass.setText(fees.getSection());
        feesYear.setText(fees.getYear());
        examTerm.setText(fees.getTerm());
        TextViewAmt.setText(fees.getAmount());
        TextViewAmtPaid.setText(fees.getPaid());
        TextViewAmtBal.setText(fees.getBalance());

    }

}
