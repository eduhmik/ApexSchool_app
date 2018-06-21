package com.virscom.eduh_mik.schoolconnect2.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import com.virscom.eduh_mik.schoolconnect2.R;
import com.virscom.eduh_mik.schoolconnect2.Retrofit.CommentsRequest;
import com.virscom.eduh_mik.schoolconnect2.Retrofit.ListResponse;
import com.virscom.eduh_mik.schoolconnect2.Retrofit.ServiceGenerator;
import com.virscom.eduh_mik.schoolconnect2.base.BaseActivity;
import com.virscom.eduh_mik.schoolconnect2.models.Comment;
import com.virscom.eduh_mik.schoolconnect2.tools.SweetAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.et_notice_name)
    EditText etNoticeName;
    @BindView(R.id.et_notice_title)
    EditText etNoticeTitle;
    @BindView(R.id.et_Description)
    EditText etDescription;
    @BindView(R.id.btn_submit_notice)
    ImageButton btnSubmitNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
    }

    public void validate(){
        String name = etNoticeName.getText().toString().trim();
        String title = etNoticeTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            etNoticeName.requestFocus();
            etNoticeName.setError("Name should not be empty");
        } else if (TextUtils.isEmpty(title)){
            etNoticeTitle.requestFocus();
            etNoticeTitle.setError("Title cannot be empty");
        } else if (TextUtils.isEmpty(description)){
            etDescription.requestFocus();
            etDescription.setError("Please enter description");
        } else {
            addComment(name, title, description);
        }
    }

    public void addComment(String name, String title, String description) {
        showSweetDialog("Add Feedback", "Adding new Feedback. Please wait...", SweetAlertDialog.PROGRESS_TYPE);
        CommentsRequest service = ServiceGenerator.createService(CommentsRequest.class);
        Call<ListResponse<Comment>> call = service.addComment(name, title, description);
        call.enqueue(new Callback<ListResponse<Comment>>() {
            @Override
            public void onResponse(Call<ListResponse<Comment>> call, Response<ListResponse<Comment>> response) {
                _sweetAlertDialog.dismissWithAnimation();
                Log.e("Notices", gson.toJson(response.body()));
                if (response.body() != null) {
                    if (TextUtils.equals(response.body().getStatus(), "success")) {
                        showToast(response.body().getMessage());
                        etNoticeName.setText(" ");
                        etNoticeTitle.setText(" ");
                        etDescription.setText(" ");
                    } else {
                        showToast(response.body().getMessage());
                        showSweetDialog("Failed!", "Sending feedback failed.", SweetAlertDialog.ERROR_TYPE);
                    }
                } else {
                    showToast("No response from server");
                }
            }

            @Override
            public void onFailure(Call<ListResponse<Comment>> call, Throwable t) {
                Log.e("Notices", t.getMessage());
                _sweetAlertDialog.dismissWithAnimation();

            }
        });
    }

    @OnClick(R.id.btn_submit_notice)
    public void onViewClicked() {
        validate();
    }
}
