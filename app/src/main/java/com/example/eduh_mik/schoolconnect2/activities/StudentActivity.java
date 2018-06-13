package com.example.eduh_mik.schoolconnect2.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eduh_mik.schoolconnect2.R;
import com.example.eduh_mik.schoolconnect2.adapters.MainPagerAdapter;
import com.example.eduh_mik.schoolconnect2.appdata.AppData;
import com.example.eduh_mik.schoolconnect2.base.BaseActivity;
import com.example.eduh_mik.schoolconnect2.fragments.DiaryFragment;
import com.example.eduh_mik.schoolconnect2.fragments.ExamFragment;
import com.example.eduh_mik.schoolconnect2.fragments.FeesFragment;
import com.example.eduh_mik.schoolconnect2.fragments.HomeworkFragment;
import com.example.eduh_mik.schoolconnect2.fragments.MyGalleryFragment;
import com.example.eduh_mik.schoolconnect2.interfaces.OnFragmentInteractionListener;
import com.example.eduh_mik.schoolconnect2.models.ListModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class StudentActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener, NavigationView.OnNavigationItemSelectedListener,
        OnFragmentInteractionListener {

    @BindView(R.id.profile_backdrop)
    ImageView profileBackdrop;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsing;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_container)
    LinearLayout titleContainer;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.profile_image)
    CircleImageView mProfileImage;
    @BindView(R.id.mTitle)
    TextView mTitle;
    @BindView(R.id.tv_class)
    TextView tvClass;

    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private boolean mIsAvatarShown = true;

    private int mMaxScrollSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle("Reports");
        ListModel listModel = new Gson().fromJson(getIntent().getExtras().getString("list"), ListModel.class);
        mTitle.setText(listModel.getFirst_name() + " " + listModel.getLast_name());
        tvClass.setText("Class" + " " + listModel.getClass_id());
        Glide.with(this).load(AppData.IMAGE_URL + listModel.getImage()).into(mProfileImage);
        loadViewPager(listModel);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        appbar.addOnOffsetChangedListener(this);
        mMaxScrollSize = appbar.getTotalScrollRange();
    }

    public void loadViewPager(ListModel listModel) {
        ArrayList<Fragment> mFragments = new ArrayList<>();
        mFragments.add(DiaryFragment.newInstance(listModel));
        mFragments.add(HomeworkFragment.newInstance(listModel));
        mFragments.add(ExamFragment.newInstance(listModel));
        mFragments.add(FeesFragment.newInstance(listModel));
        mFragments.add(MyGalleryFragment.newInstance(listModel));
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), mFragments);
        viewpager.setAdapter(mainPagerAdapter);
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewpager));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {

        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(offset)) * 100 / mMaxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;

            mProfileImage.animate()
                    .scaleY(0).scaleX(0)
                    .setDuration(200)
                    .start();
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;

            mProfileImage.animate()
                    .scaleY(1).scaleX(1)
                    .start();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_account) {
            startNewActivity(EditProfileActivity.class);
        } else if (id == R.id.nav_about) {
            startNewActivity(AboutUs.class);
        } else if (id == R.id.nav_reports) {
            startNewActivity(StudentActivity.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onFragmentInteraction(int action) {

    }
}
