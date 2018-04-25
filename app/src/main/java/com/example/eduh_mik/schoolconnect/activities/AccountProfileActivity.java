package com.example.eduh_mik.schoolconnect.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eduh_mik.schoolconnect.R;
import com.example.eduh_mik.schoolconnect.base.BaseActivity;
import com.example.eduh_mik.schoolconnect.tools.SweetAlertDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Eduh_mik on 4/23/2018.
 */

public class AccountProfileActivity extends BaseActivity {
    public View view;
    public static String PHOTO_URL = "";
    @BindView(R.id.et_Email)
    EditText etEmail;
    @BindView(R.id.et_FirstName)
    EditText etFirstName;
    @BindView(R.id.et_LastName)
    EditText etLastName;
    @BindView(R.id.iv_ProfileImage)
    ImageView ivProfileImage;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.info_2)
    CardView info2;
    @BindView(R.id.edit)
    AppCompatButton edit;



    public void validate() {
        String email = etEmail.getText().toString().trim();
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            etEmail.requestFocus();
            etEmail.setError("Email cannot be empty");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.requestFocus();
            etEmail.setError("Invalid email");
        } else if (TextUtils.isEmpty(firstName)) {
            etFirstName.requestFocus();
            etFirstName.setError("First name cannot be empty");
        } else if (TextUtils.isEmpty(lastName)) {
            etLastName.requestFocus();
            etLastName.setError("Last name cannot be empty");
        } else if (TextUtils.isEmpty(phoneNumber)) {
            etPhoneNumber.requestFocus();
            etPhoneNumber.setError("Last name cannot be empty");
        } else {
            register(email, firstName, lastName);
        }
    }

    public void register(String email, String firstName, String lastName) {
        showSweetDialog("Edit Profile", "Saving Profile. Please wait...", SweetAlertDialog.PROGRESS_TYPE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                _sweetAlertDialog.dismissWithAnimation();
                sharedPrefs.setIsloggedIn(true);
                showSweetDialog("Edit Profile", "Profile Saved Succesfully", SweetAlertDialog.SUCCESS_TYPE, "Got It!", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                        startNewActivity(MainActivity.class);
                        AccountProfileActivity.this.finish();
                    }
                });
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountinfo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Edit Profile");
        ButterKnife.bind(this);
        final ArrayList<String> list = new ArrayList<String>();

        final ArrayAdapter<String> adapter;
        ListView listView = (ListView) findViewById(R.id.simple_listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(AccountProfileActivity.this);
                final android.app.AlertDialog alertDialog;
                view = LayoutInflater.from(AccountProfileActivity.this).inflate(R.layout.dialog, null);
                Spinner spinner = (Spinner) view.findViewById(R.id.simple_spinner_class);
                ArrayAdapter<CharSequence> spinnerArrayAdapter = new ArrayAdapter<CharSequence>
                        (AccountProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.classes));
                spinner.setAdapter(spinnerArrayAdapter);
                builder.setView(view);
                builder.setTitle("Add Child");
                final EditText editText = (EditText) view.findViewById(R.id.editfull);
                final EditText etName = (EditText) view.findViewById(R.id.etSurname);
                builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertDialog = builder.create();
                alertDialog.show();

                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String oldPass = editText.getText().toString();
                        final String newPass = etName.getText().toString();
                        list.add(oldPass);
                        list.add(newPass);
                        adapter.notifyDataSetChanged();
                        if (TextUtils.isEmpty(oldPass)) {
                            editText.requestFocus();
                            editText.setError("Full name cannot be empty");
                        } else if (TextUtils.isEmpty(newPass)) {
                            etName.requestFocus();
                            etName.setError("Surname cannot be empty");
                        } else {
                            alertDialog.dismiss();
                        }
                    }
                });
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onPause() {
        super.onPause();
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

    @OnClick(R.id.edit)
    public void onViewClicked() {
        validate();
        Toast.makeText(getApplicationContext(), "Updated Information Successfully!",Toast.LENGTH_SHORT).show();
    }
}

