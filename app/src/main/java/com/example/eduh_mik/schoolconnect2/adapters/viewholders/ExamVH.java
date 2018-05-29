package com.example.eduh_mik.schoolconnect2.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.models.Exam;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExamVH extends RecyclerView.ViewHolder {
    //public TextView tvName,tvDescription, tvStartDate;
    @BindView(R.id.exam_year)
    TextView examYear;
    @BindView(R.id.exam_type)
    TextView examType;
    @BindView(R.id.exam_term)
    TextView examTerm;
    @BindView(R.id.maths)
    TextView maths;
    @BindView(R.id.english)
    TextView english;
    @BindView(R.id.kisw)
    TextView kisw;
    @BindView(R.id.scie)
    TextView scie;
    @BindView(R.id.sst)
    TextView sst;
    @BindView(R.id.comp)
    TextView comp;
    @BindView(R.id.total)
    TextView total;
    @BindView(R.id.pos)
    TextView pos;
    @BindView(R.id.card_exam)
    CardView cardExam;

    private Context _context;

    public ExamVH(Context context, View view) {
        super(view);
        this._context = context;
        ButterKnife.bind(this, view);
    }

    public void bind(Exam exam) {
       examTerm.setText(exam.getTerm());
       examYear.setText(exam.getYear());
       examType.setText(exam.getType());
        maths.setText(exam.getMaths());
        english.setText(exam.getEnglish());
        kisw.setText(exam.getKisw());
        sst.setText(exam.getSs());
        scie.setText(exam.getScie());
        comp.setText(exam.getComputer());



    }

}