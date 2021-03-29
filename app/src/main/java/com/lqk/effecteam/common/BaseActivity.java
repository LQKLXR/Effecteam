package com.lqk.effecteam.common;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Create By LiuQK on 2021/3/22
 * Describe: 项目中所有用到的Activity的公共父类
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityContainer.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityContainer.removeActivity(this);
    }
}