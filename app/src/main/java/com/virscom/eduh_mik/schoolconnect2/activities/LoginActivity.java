package com.virscom.eduh_mik.schoolconnect2.activities;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.virscom.eduh_mik.schoolconnect2.R;
import com.virscom.eduh_mik.schoolconnect2.Retrofit.AccountRequests;
import com.virscom.eduh_mik.schoolconnect2.Retrofit.ListResponse;
import com.virscom.eduh_mik.schoolconnect2.Retrofit.ServiceGenerator;
import com.virscom.eduh_mik.schoolconnect2.base.BaseActivity;
import com.virscom.eduh_mik.schoolconnect2.models.User;
import com.virscom.eduh_mik.schoolconnect2.tools.SweetAlertDialog;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "Google Sign In";
    private static String PHOTO_URL = "";
    @BindView(R.id.et_Email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_submit)
    AppCompatButton btnSubmit;
    @BindView(R.id.iv_ProfileImage)
    ImageView ivProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    public void validate() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            etEmail.requestFocus();
            etEmail.setError("Phone number cannot be empty");
        } else if (TextUtils.isEmpty(password)) {
            etPassword.requestFocus();
            etPassword.setError("Password cannot be empty");
        } else {
            getLoginDetails(email,password);
        }
    }

    public void getLoginDetails(String phone, String password) {
        showSweetDialog("Signing In", "Signing in. Please wait...", SweetAlertDialog.PROGRESS_TYPE);
        AccountRequests service = ServiceGenerator.createService(AccountRequests.class);
        Call<ListResponse<User>> call = service.getLoginDetails(phone, password);
        call.enqueue(new Callback<ListResponse<User>>() {
            @Override
            public void onResponse(Call<ListResponse<User>> call, Response<ListResponse<User>> response) {
                _sweetAlertDialog.dismissWithAnimation();
                Log.e("Login", gson.toJson(response.body()));
                if (response.body() != null) {
                    if (TextUtils.equals(response.body().getStatus(), "success")) {
                        sharedPrefs.setIsloggedIn(true);
                        sharedPrefs.setUser(response.body().list.get(0));
                        Log.e("phone", sharedPrefs.getUser().getPhone());
                        showSweetDialog("Successul!!!", "Login Success.", SweetAlertDialog.SUCCESS_TYPE);
                        startNewActivity(MainActivity.class);
                    } else {
                        showToast(response.body().getMessage());
                        showSweetDialog("Login Failed!", "Login failed. Please check your internet connection!", SweetAlertDialog.ERROR_TYPE);
                    }
                } else {
                    showToast("No response from server");
                }
            }

            @Override
            public void onFailure(Call<ListResponse<User>> call, Throwable t) {
                Log.e("", t.getMessage());
                _sweetAlertDialog.dismissWithAnimation();

            }
        });
    }

    public void handleSignInResult(GoogleSignInResult result) {
        Log.e(TAG, "handleSignInResult:" + result.isSuccess() + ":" + result.getStatus().toString());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.e("", acct.getEmail());
            etEmail.setText(acct.getEmail());
            if (acct.getPhotoUrl() != null) {
                Glide.with(this).load(acct.getPhotoUrl().toString()).apply(RequestOptions.circleCropTransform()).into(ivProfileImage);
                PHOTO_URL = acct.getPhotoUrl().toString();
            } else {

            }
        } else {
            showToast("Sign In Failure");
        }
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        validate();
    }
}
