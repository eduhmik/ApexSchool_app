package com.example.eduh_mik.schoolconnect.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.eduh_mik.schoolconnect.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SchoolsActivity extends AppCompatActivity {
    public static ArrayList<String> list = new ArrayList<String>();

    ArrayAdapter<String> adapter;
    @BindView(R.id.list_schools)
    ListView listSchools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schools);
        ButterKnife.bind(this);

        list = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked,list);
        listSchools.setAdapter(adapter);
        setListSchools();
    }

    void setListSchools(){
        list.add("Kinderworld School");
        list.add("Strathmore School");
        adapter.notifyDataSetChanged();
    }
}
