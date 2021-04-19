package com.lqk.effecteam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.home.HomeFragment;
import com.lqk.effecteam.mine.MineFragment;
import com.lqk.effecteam.project.list.ProjectFragment;
import com.lqk.effecteam.team.chat.TeamChatFragment;
import com.lqk.effecteam.team.join.JoinTeamActivity;
import com.lqk.effecteam.team.list.TeamFragment;
import com.xuexiang.xui.XUI;

/**
 * 主界面的Activity
 */
public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private HomeFragment mHomeFragment;
    private TeamFragment mTeamFragment;
    private ProjectFragment mProjectFragment;
    private MineFragment mMineFragment;

    private FragmentManager fragmentManager;
    /*底部导航栏*/
    private BottomNavigationView mBottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    @SuppressLint("WrongConstant")
    private void initView() {
        fragmentManager = getSupportFragmentManager();
        mHomeFragment = new HomeFragment();
        mTeamFragment = new TeamFragment();
        mProjectFragment = new ProjectFragment();
        mMineFragment = new MineFragment();
        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, mHomeFragment).commit();
        mBottomNavigationView = findViewById(R.id.main_bottom_menu);
        mBottomNavigationView.setLabelVisibilityMode(1);
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home_item:
                    Log.i(TAG, "点击了首页");
                    fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, mHomeFragment).commit();
                    return true;
                case R.id.bottom_projects_item:
                    Log.i(TAG, "点击了项目列表");
                    fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, mProjectFragment).commit();
                    return true;
                case R.id.bottom_teams_item:
                    Log.i(TAG, "点击了团队列表");
                    fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, mTeamFragment).commit();
                    return true;
                case R.id.bottom_person_item:
                    Log.i(TAG, "点击了个人中心");
                    fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, mMineFragment).commit();
                    return true;
            }
            return false;
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == JoinTeamActivity.BACK_TO_TEAM_RESULT) {
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, mTeamFragment).commit();
            }
        }
    }


}