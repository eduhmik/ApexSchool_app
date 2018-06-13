package com.example.eduh_mik.schoolconnect2.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity {


    @BindView(R.id.lin_Google)
    LinearLayout linGoogle;
    @OnClick(R.id.lin_Google)
    public void onViewClicked() {
        startNewActivity(RegisterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        assert getSupportActionBar() != null;
        getSupportActionBar().hide();
        //linGoogle.setVisibility(sharedPrefs.getIsloggedIn() ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startNewActivity(sharedPrefs.getIsloggedIn() ? MainActivity.class : RegisterActivity.class);
                SplashActivity.this.finish();
            }
        }, 3000);
    }

}

