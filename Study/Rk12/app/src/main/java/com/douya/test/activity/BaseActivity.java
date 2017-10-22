package com.douya.test.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.douya.test.R;
import com.douya.test.api.API;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        //初始化SharedPreferences
        API.init(getApplicationContext());
    }
}
