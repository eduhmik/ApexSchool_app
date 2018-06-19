package com.virscom.eduh_mik.schoolconnect2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.virscom.eduh_mik.schoolconnect2.R;
import com.virscom.eduh_mik.schoolconnect2.Retrofit.AccountRequests;
import com.virscom.eduh_mik.schoolconnect2.Retrofit.ListResponse;
import com.virscom.eduh_mik.schoolconnect2.Retrofit.ServiceGenerator;
import com.virscom.eduh_mik.schoolconnect2.base.BaseFragment;
import com.virscom.eduh_mik.schoolconnect2.interfaces.OnFragmentInteractionListener;
import com.virscom.eduh_mik.schoolconnect2.models.User;
import com.virscom.eduh_mik.schoolconnect2.tools.SweetAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends BaseFragment {
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etPassword)
    EditText etPassword;
    Unbinder unbinder;
    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
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

    @OnClick(R.id.txtLostPassword)
    public void onTxtLostPasswordClicked() {
        mListener.onFragmentInteraction(1);
    }

    @OnClick(R.id.txtSignUp)
    public void onTxtSignUpClicked() {
        mListener.onFragmentInteraction(2);
    }

    @OnClick(R.id.btn_Submit)
    public void onBtnSubmitClicked() {
        validate();
    }
    public void validate(){
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if(TextUtils.isEmpty(phone)){
            etPhone.requestFocus();
            etPhone.setError("Enter phone number");
        }else if(TextUtils.isEmpty(password)){
            etPassword.requestFocus();
            etPassword.setError("Enter password");
        }else{
            login(phone,password);
        }
    }
    public void login(String phone,String password){
        AccountRequests service = ServiceGenerator.createService(AccountRequests.class);
        Call<ListResponse<User>> call = service.getLoginDetails(phone,password);
        call.enqueue(new Callback<ListResponse<User>>() {
            @Override
            public void onResponse(Call<ListResponse<User>> call, final retrofit2.Response<ListResponse<User>> response) {
                _sweetAlertDialog.dismissWithAnimation();
                if(response.body() != null) {
                    Log.e("Login",gson.toJson(response.body()));
                    if(TextUtils.equals(response.body().getStatus(),"success")){
                        showSweetDialog("Success", "Login Succesful", SweetAlertDialog.SUCCESS_TYPE, "Got it!",new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                sharedPrefs.setIsloggedIn(true);
                                sharedPrefs.setUser(response.body().getList().get(0));
                                mListener.onFragmentInteraction(4);
                            }
                        });
                    }else{
                        showSweetDialog("Failure", response.body().getMessage(), SweetAlertDialog.ERROR_TYPE, "Got it!",new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        });
                    }
                }else{
                    showSweetDialog("Failure", "No response from server", SweetAlertDialog.ERROR_TYPE, "Got it!",new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ListResponse<User>> call, Throwable t) {
                _sweetAlertDialog.dismissWithAnimation();
                showToast("Please try again");
                t.printStackTrace();
            }
        });
        showSweetDialog("Login","Logging in. Please wait...", SweetAlertDialog.PROGRESS_TYPE);
    }
}
