package com.virscom.eduh_mik.schoolconnect2.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.virscom.eduh_mik.schoolconnect2.R;
import com.virscom.eduh_mik.schoolconnect2.base.BaseActivity;
import com.virscom.eduh_mik.schoolconnect2.fragments.LoginFragment;
import com.virscom.eduh_mik.schoolconnect2.fragments.RegisterFragment;
import com.virscom.eduh_mik.schoolconnect2.interfaces.OnFragmentInteractionListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity implements OnFragmentInteractionListener {

    @BindView(R.id.frame) FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        replaceFragment(LoginFragment.newInstance(), "Login");
    }

    public void replaceFragment(Fragment fragment, String title) {
        setTitle(title);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(frame.getId(), fragment).commit();
    }

    public Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(frame.getId());
    }

    @Override
    public void onFragmentInteraction(int i) {
        switch(i){
            case 0: break;
            case 1: break;
            case 2: replaceFragment(RegisterFragment.newInstance(), "Register"); break;
            case 3: replaceFragment(LoginFragment.newInstance(), "Login"); break;
            case 4: startNewActivity(MainActivity.class); finish(); break;
            default: break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getCurrentFragment() instanceof RegisterFragment){
            onFragmentInteraction(3);
        }else{
            super.onBackPressed();
        }
    }
}
