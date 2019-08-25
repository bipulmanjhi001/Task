package com.walmartlabs.task.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.walmartlabs.task.R;

public class SingleView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
