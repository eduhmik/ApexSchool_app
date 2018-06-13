package com.example.eduh_mik.schoolconnect2.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.Retrofit.AccountRequests;
import com.example.eduh_mik.schoolconnect2.Retrofit.Response;
import com.example.eduh_mik.schoolconnect2.Retrofit.ServiceGenerator;
import com.example.eduh_mik.schoolconnect2.appdata.AppData;
import com.example.eduh_mik.schoolconnect2.base.BaseActivity;
import com.example.eduh_mik.schoolconnect2.tools.SweetAlertDialog;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.intentfilter.androidpermissions.PermissionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

import static java.util.Collections.singleton;

public class Register1Activity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "Google Sign In";
    private static final int OUR_REQUEST_CODE = 4902;
    private static String PHOTO_URL = "";
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_rePassword)
    EditText etRePassword;
    private GoogleApiClient mGoogleApiClient;
    @BindView(R.id.et_Email)
    EditText etEmail;
    @BindView(R.id.et_FirstName)
    EditText etFirstName;
    @BindView(R.id.et_LastName)
    EditText etLastName;
    @BindView(R.id.info_2)
    CardView info2;
    @BindView(R.id.btn_submit)
    AppCompatButton btnSubmit;
    @BindView(R.id.iv_ProfileImage)
    ImageView ivProfileImage;

    public void validate() {
        String email = etEmail.getText().toString().trim();
        String firstname = etFirstName.getText().toString().trim();
        String lastname = etLastName.getText().toString().trim();
        String phone = etPhoneNumber.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String rePassword = etRePassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            etEmail.requestFocus();
            etEmail.setError("Email cannot be empty");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.requestFocus();
            etEmail.setError("Invalid email");
        } else if (TextUtils.isEmpty(firstname)) {
            etFirstName.requestFocus();
            etFirstName.setError("First name cannot be empty");
        } else if (TextUtils.isEmpty(lastname)) {
            etLastName.requestFocus();
            etLastName.setError("Last name cannot be empty");
        } else if (TextUtils.isEmpty(phone)){
            etPhoneNumber.requestFocus();
            etPhoneNumber.setError("Phone Number cannot be empty");

        } else if (TextUtils.isEmpty(password)){
            etPassword.requestFocus();
            etPassword.setError("Password cannot be empty");

        } else if (TextUtils.isEmpty(rePassword)){
            etRePassword.requestFocus();
            etRePassword.setError("Confirm password cannot be empty");

        } else if (!rePassword.equals(password)){
            etRePassword.setError("Password do not match");
        }
            else {
            register(email, firstname, lastname, password, phone);
        }

    }

    public void register(String email, String firstname, String lastname, String password, String phone) {
        showSweetDialog("Register", "Creating Account. Please wait...", SweetAlertDialog.PROGRESS_TYPE);
        AccountRequests service = ServiceGenerator.createService(AccountRequests.class);
        Call<Response> call = service.register(email, firstname, lastname, password, phone, sharedPrefs.getDeviceId());
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                _sweetAlertDialog.dismissWithAnimation();
                sharedPrefs.setIsloggedIn(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        _sweetAlertDialog.dismissWithAnimation();
                        Log.e("Register", gson.toJson(response.body()));
                        if (response.body() != null) {
                            if (TextUtils.equals(response.body().getStatus(), "success")) {
                                startNewActivity(MainActivity.class);
                            } else {
                                showToast(response.body().getMessage());
                                startNewActivity(LoginActivity.class);
                            }
                        } else {
                            showToast("No response from server");
                        }
                    }
                }, 2000);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                _sweetAlertDialog.dismissWithAnimation();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        ButterKnife.bind(this);
        initGoogleClient();
        setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void initGoogleClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(AppData.GOOGLE_SERVER_CLIENT_ID).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String permission = Manifest.permission.GET_ACCOUNTS;
            int grant = ContextCompat.checkSelfPermission(this, permission);
            if (grant != PackageManager.PERMISSION_GRANTED) {
                PermissionManager permissionManager = PermissionManager.getInstance(this);
                permissionManager.checkPermissions(singleton(permission), new PermissionManager.PermissionRequestListener() {
                    @Override
                    public void onPermissionGranted() {
                        silentSignin();
                    }

                    @Override
                    public void onPermissionDenied() {
                        //showToast("Read Account Permission Denied");
                    }
                });
            } else {
                silentSignin();
            }
        } else {

            silentSignin();
        }
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        validate();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, OUR_REQUEST_CODE);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(this);
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            return;
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("Contacts", "Register Fragment" + requestCode);
        if (requestCode == OUR_REQUEST_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    public void silentSignin() {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            Log.d(TAG, "No cached sign-in");
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    public void handleSignInResult(GoogleSignInResult result) {
        Log.e(TAG, "handleSignInResult:" + result.isSuccess() + ":" + result.getStatus().toString());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.e("", acct.getEmail());
            etEmail.setText(acct.getEmail());
            etFirstName.setText(acct.getGivenName());
            etLastName.setText(acct.getFamilyName());
            if (acct.getPhotoUrl() != null) {
                Glide.with(this).load(acct.getPhotoUrl().toString()).apply(RequestOptions.circleCropTransform()).into(ivProfileImage);
                PHOTO_URL = acct.getPhotoUrl().toString();
            } else {

            }
        } else {
            showToast("Sign In Failure");
        }
    }
}
