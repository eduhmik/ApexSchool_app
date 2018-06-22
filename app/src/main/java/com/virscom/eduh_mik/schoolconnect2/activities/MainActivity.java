package com.virscom.eduh_mik.schoolconnect2.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.virscom.eduh_mik.schoolconnect2.adapters.MainPagerAdapter;
import com.virscom.eduh_mik.schoolconnect2.appdata.AppData;
import com.virscom.eduh_mik.schoolconnect2.base.BaseActivity;
import com.virscom.eduh_mik.schoolconnect2.fragments.ActivitiesFragment;
import com.virscom.eduh_mik.schoolconnect2.fragments.ContactFragment;
import com.virscom.eduh_mik.schoolconnect2.fragments.GalleryFragment;
import com.virscom.eduh_mik.schoolconnect2.fragments.NoticesFragment;
import com.virscom.eduh_mik.schoolconnect2.interfaces.OnFragmentInteractionListener;
import com.virscom.eduh_mik.schoolconnect2.service.Push;
import com.virscom.eduh_mik.schoolconnect2.utils.NotificationUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.util.Collections.singleton;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,GoogleApiClient.OnConnectionFailedListener, OnFragmentInteractionListener, SearchView.OnQueryTextListener {
    private static final int OUR_REQUEST_CODE = 4902;
    public static String PHOTO_URL="";
    private static final String TAG = "Google Sign In";
    private GoogleApiClient mGoogleApiClient;
    public SearchView searchView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.container)
    ViewPager mViewPager;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle("School Connect");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        loadViewPager();
        initGoogleClient();
        processIntent(getIntent());
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processIntent(intent);

    }
    public void processIntent(Intent intent){
        if(intent.getExtras() != null){
            Push push = gson.fromJson(intent.getStringExtra(NotificationUtils.PUSH_INTENT),Push.class);
            if (push.getTag() == NotificationUtils.TAG_NEW_DIARY){
                showToast(push.getBody());
                startNewActivity(StudentActivity.class);
            }else{

            }

        }

    }
    public void loadViewPager() {
        ArrayList<Fragment> mFragments = new ArrayList<>();
        mFragments.add(NoticesFragment.newInstance());
        mFragments.add(ActivitiesFragment.newInstance());
        mFragments.add(GalleryFragment.newInstance());
        mFragments.add(ContactFragment.newInstance());
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mainPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
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
            View headerView = navView.getHeaderView(0);
            TextView tvEmail = (TextView) headerView.findViewById(R.id.tv_Email);
            TextView tvName = (TextView) headerView.findViewById(R.id.tv_Name);
            ImageView ivImage = (ImageView) headerView.findViewById(R.id.iv_ProfileImage);
            tvEmail.setText(acct.getEmail());
            tvName.setText(acct.getGivenName()+" "+acct.getFamilyName());
            if (acct.getPhotoUrl() != null) {
                Glide.with(this).load(acct.getPhotoUrl().toString()).apply(RequestOptions.circleCropTransform()).into(ivImage);
                PHOTO_URL = acct.getPhotoUrl().toString();
            } else {

            }
        } else {
            showToast("Sign In Failure");
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(this);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(0);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                search("");
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_signout) {
            logOut();
        }
        return super.onOptionsItemSelected(item);
    }

    public void logOut() {
        sharedPrefs.setIsloggedIn(false);
        startNewActivity(SplashActivity.class);
        finish();
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_account) {
            startNewActivity(EditProfileActivity.class);
        }else if(id == R.id.nav_about){
           startNewActivity(AboutUs.class);
        } else if  (id == R.id.nav_reports){
            startNewActivity(ListActivity.class);
        } else if (id == R.id.nav_feedback){
            startNewActivity(FeedbackActivity.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void showdialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        //final View dialogView = inflater.inflate(R.layout.activity_schools, null);
        //dialogBuilder.setView(dialogView);


        final AlertDialog b = dialogBuilder.create();
        b.show();



    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        search(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        search(query);
        return true;
    }

    public void search(String string){
        onMedicineSearchListener.onMedicineSearched(string);
    }
    public static void initOnMedicineSearchListener(OnMedicineSearchListener _onMedicineSearchListener){
        onMedicineSearchListener = _onMedicineSearchListener;
    }
    private static OnMedicineSearchListener onMedicineSearchListener;
    public interface OnMedicineSearchListener{
        void onMedicineSearched(String name);
    }

    @Override
    public void onFragmentInteraction(int action) {

    }
}
