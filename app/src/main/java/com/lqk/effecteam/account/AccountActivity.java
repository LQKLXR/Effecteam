package com.lqk.effecteam.account;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
/**
 * Create By LiuQK on 2021/3/23
 * Describe: 登录注册界面对应的Activity
 */
public class AccountActivity extends BaseActivity {
    /*储存内容的fragment*/
    private Fragment mFragment;
    /*加载进度条*/
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }
    /**
     * 初始化界面
     */
    public void initView() {
        mProgressBar = findViewById(R.id.progressbar_login);
        mProgressBar.setVisibility(View.INVISIBLE);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        /*默认是登录的Fragment*/
        mFragment = new LoginFragment();
        supportFragmentManager.beginTransaction().add(R.id.fragment_login, mFragment).commit();
    }


    public void openLoading(){
        mProgressBar.setVisibility(View.VISIBLE);
        mFragment.getView().setVisibility(View.INVISIBLE);
    }

    public void closeLoading(){

    }
}