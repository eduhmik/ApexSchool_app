package com.virscom.eduh_mik.schoolconnect2.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.virscom.eduh_mik.schoolconnect2.R;
import com.virscom.eduh_mik.schoolconnect2.models.Exam;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExamVH extends RecyclerView.ViewHolder {
    //public TextView tvName,tvDescription, tvStartDate;
    @BindView(R.id.tv_startDate)
    TextView tvStartDate;
    @BindView(R.id.et_day)
    TextView etDay;
    @BindView(R.id.tv_Name)
    TextView tvName;
    @BindView(R.id.tv_class)
    TextView tvClass;
    @BindView(R.id.et_math_mark)
    TextView etMathMark;
    @BindView(R.id.et_eng_mark)
    TextView etEngMark;
    @BindView(R.id.et_kisw_mark)
    TextView etKiswMark;
    @BindView(R.id.et_scie_mark)
    TextView etScieMark;
    @BindView(R.id.et_ss_marks)
    TextView etSsMarks;
    @BindView(R.id.et_ire_mark)
    TextView etIreMark;
    @BindView(R.id.et_creative_mark)
    TextView etCreativeMark;
    @BindView(R.id.et_mus_mark)
    TextView etMusMark;
    @BindView(R.id.et_comp_mark)
    TextView etCompMark;

    private Context _context;

    public ExamVH(Context context, View view) {
        super(view);
        this._context = context;
        ButterKnife.bind(this, view);
    }

    public void bind(Exam exam) {
       tvName.setText("Term"+ " "+exam.getTerm());
       tvStartDate.setText(exam.getDate());
       etDay.setText(exam.getDay());
       tvClass.setText(exam.getType());
        etMathMark.setText(exam.getMaths());
        etEngMark.setText(exam.getEnglish());
        etKiswMark.setText(exam.getKisw());
        etSsMarks.setText(exam.getSs());
        etScieMark.setText(exam.getScie());
        etCompMark.setText(exam.getComputer());
        etIreMark.setText(exam.getIre_cre());
        etCreativeMark.setText(exam.getCreative());
        etMusMark.setText(exam.getMusical());



    }

}