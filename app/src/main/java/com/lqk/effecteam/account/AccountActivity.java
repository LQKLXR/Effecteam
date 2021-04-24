package com.lqk.effecteam.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.lqk.effecteam.MainActivity;
import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.common.HttpUtil;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

/**
 * Create By LiuQK on 2021/3/23
 * Describe: 登录注册界面对应的Activity
 */
public class AccountActivity extends BaseActivity {
    /*储存内容的fragment*/
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        SharedPreferences sharedPreferences = getSharedPreferences(HttpUtil.Shared_File_Name, MODE_PRIVATE);
        if (sharedPreferences.getInt("userId", 0) != 0) {
            Intent intent = new Intent(AccountActivity.this, MainActivity.class);
            intent.putExtra("actualName", sharedPreferences.getString("actualName", ""));
            startActivity(intent);
            finish();
        }
    }

    /**
     * 初始化界面
     */
    public void initView() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        /*默认是登录的Fragment*/
        mFragment = new LoginFragment();
        supportFragmentManager.beginTransaction().add(R.id.fragment_login, mFragment).commit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == 1) {
                Toast.makeText(AccountActivity.this, "注册成功", Toast.LENGTH_LONG);
            }
        }
    }
}