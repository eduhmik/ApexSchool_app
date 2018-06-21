package com.virscom.eduh_mik.schoolconnect2.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.intentfilter.androidpermissions.PermissionManager;
import com.virscom.eduh_mik.schoolconnect2.R;
import com.virscom.eduh_mik.schoolconnect2.Retrofit.AccountRequests;
import com.virscom.eduh_mik.schoolconnect2.Retrofit.Response;
import com.virscom.eduh_mik.schoolconnect2.Retrofit.ServiceGenerator;
import com.virscom.eduh_mik.schoolconnect2.appdata.AppData;
import com.virscom.eduh_mik.schoolconnect2.base.BaseFragment;
import com.virscom.eduh_mik.schoolconnect2.interfaces.OnFragmentInteractionListener;
import com.virscom.eduh_mik.schoolconnect2.tools.SweetAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;

import static java.util.Collections.singleton;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends BaseFragment implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "Google Sign In";
    private static final int OUR_REQUEST_CODE = 4902;
    public static String PHOTO_URL = "";
    private GoogleApiClient mGoogleApiClient;
    @BindView(R.id.etfirstName)
    EditText etfirstName;
    @BindView(R.id.ivProfile)
    ImageView ivProfile;
    @BindView(R.id.etLastName)
    EditText etLastName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etRepeatPassword)
    EditText etRepeatPassword;
    Unbinder unbinder;
    private OnFragmentInteractionListener mListener;

    @OnClick(R.id.btn_Submit)
    public void onViewClicked() {
        validate();
    }

    public void validate() {
        String fname = etfirstName.getText().toString().trim();
        String lname = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String rptPassword = etRepeatPassword.getText().toString().trim();

        if (TextUtils.isEmpty(fname)) {
            etfirstName.requestFocus();
            etfirstName.setError("Enter first name");
        } else if (TextUtils.isEmpty(lname)) {
            etLastName.requestFocus();
            etLastName.setError("Enter last name");
        } else if (TextUtils.isEmpty(email)) {
            etEmail.requestFocus();
            etEmail.setError("Enter email");
        } else if (TextUtils.isEmpty(phone)) {
            etPhone.requestFocus();
            etPhone.setError("Enter phone number");
        } else if (TextUtils.isEmpty(password)) {
            etPassword.requestFocus();
            etPassword.setError("Enter password");
        } else if (TextUtils.isEmpty(rptPassword)) {
            etRepeatPassword.requestFocus();
            etRepeatPassword.setError("Repeat password");
        } else if (!TextUtils.equals(password, rptPassword)) {
            etRepeatPassword.requestFocus();
            etRepeatPassword.setError("Passwords must match");
        } else {
            register(fname, lname, email, phone, password);
        }
    }

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initGoogleClient();
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this, view);
        showSweetDialog("Google Sign In", "Siging up. Please wait!", SweetAlertDialog.PROGRESS_TYPE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _sweetAlertDialog.dismissWithAnimation();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, OUR_REQUEST_CODE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void initGoogleClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(AppData.GOOGLE_SERVER_CLIENT_ID).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String permission = Manifest.permission.GET_ACCOUNTS;
            int grant = ContextCompat.checkSelfPermission(getContext(), permission);
            if (grant != PackageManager.PERMISSION_GRANTED) {
                PermissionManager permissionManager = PermissionManager.getInstance(getActivity());
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

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (!connectionResult.hasResolution()) {
            return;
        }
    }


    public void register(String fname, String lname, String email, String phone, String password) {
        AccountRequests service = ServiceGenerator.createService(AccountRequests.class);
        Call<Response> call = service.register(email, fname, lname, password, phone, sharedPrefs.getDeviceId());
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                _sweetAlertDialog.dismissWithAnimation();
                if (response.body() != null) {
                    Log.e("Register", gson.toJson(response.body()));
                    if (TextUtils.equals(response.body().getStatus(), "success")) {
                        showSweetDialog("Success", response.body().getMessage(), SweetAlertDialog.SUCCESS_TYPE, "Got it!", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                mListener.onFragmentInteraction(3);
                            }
                        });
                    } else {
                        showSweetDialog("Failure", response.body().getMessage(), SweetAlertDialog.ERROR_TYPE, "Got it!", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        });
                    }
                } else {
                    showSweetDialog("Failure", "No response from server", SweetAlertDialog.ERROR_TYPE, "Got it!", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                _sweetAlertDialog.dismissWithAnimation();
                showToast("Please try again");
                t.printStackTrace();
            }
        });
        showSweetDialog("Register", "Creating Account. Please wait...", SweetAlertDialog.PROGRESS_TYPE);
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
            Log.e("Email", acct.getEmail());
            if (etEmail != null && acct.getEmail() != null) {
                etEmail.setText(acct.getEmail());
                etfirstName.setText(acct.getGivenName());
                etLastName.setText(acct.getFamilyName());
                if (acct.getPhotoUrl() != null) {
                    Glide.with(this).load(acct.getPhotoUrl().toString()).apply(RequestOptions.circleCropTransform()).into(ivProfile);
                    PHOTO_URL = acct.getPhotoUrl().toString();
                } else {

                }
            } else {
                showToast("Sign In Failure");
            }
        }
    }
}
