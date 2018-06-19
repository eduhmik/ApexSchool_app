package com.virscom.eduh_mik.schoolconnect2.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;
import android.view.View;

import com.virscom.eduh_mik.schoolconnect2.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("About Us");
        simulateDayNight(/* DAY */ 0);
        Element adsElement = new Element();

        View aboutPage = new AboutPage(this)
                .isRTL(false)

                .setImage(R.drawable.apexlogo)
                .addItem(new Element().setTitle("Version 6.2"))
                .addGroup("Connect with us")
                .addEmail("email@gmail.com")
                .addWebsite("http://medyo.github.io/")
                .addFacebook("facebook.com")
                .addTwitter("twitter.com")
                .addPlayStore("com.ideashower.yummy.pro")
                //.addItem(getCopyRightsElement())
                .create();

        setContentView(aboutPage);
    }

//
//    Element getCopyRightsElement() {
//        Element copyRightsElement = new Element();
//        final String copyrights =String.format(getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR));
//        copyRightsElement.setTitle(copyrights);
//        copyRightsElement.setIconDrawable(R.mipmap.ic_launcher);
//        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
//        copyRightsElement.setIconNightTint(android.R.color.white);
//        copyRightsElement.setGravity(Gravity.CENTER);
//        copyRightsElement.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(AboutUs.this, copyrights, Toast.LENGTH_SHORT).show();
//            }
//        });
//        return copyRightsElement;
//    }
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

    void simulateDayNight(int currentSetting) {
        final int DAY = 0;
        final int NIGHT = 1;
        final int FOLLOW_SYSTEM = 3;

        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        if (currentSetting == DAY && currentNightMode != Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        } else if (currentSetting == NIGHT && currentNightMode != Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else if (currentSetting == FOLLOW_SYSTEM) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }
}