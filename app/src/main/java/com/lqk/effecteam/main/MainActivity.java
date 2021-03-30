package com.lqk.effecteam.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.teamlist.TeamFragment;

/**
 * 主界面的Activity
 */
public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private Fragment mFragment;
    /*底部导航栏*/
    private BottomNavigationView mBottomNavigationView;
    /*底部导航栏监听器*/
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.bottom_home_item:
                    Log.d(TAG, "onNavigationItemSelected: bottom_home_item");
                    return true;
                case R.id.bottom_projects_item:
                    Log.d(TAG, "onNavigationItemSelected: bottom_projects_item");
                    return true;
                case R.id.bottom_contacts_item:
                    Log.d(TAG, "onNavigationItemSelected: bottom_contacts_item");
                    return true;
                case R.id.bottom_person_item:
                    Log.d(TAG, "onNavigationItemSelected: bottom_person_item");
                    return true;
            }
            return false;
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    @SuppressLint("WrongConstant")
    private void initView(){
        mBottomNavigationView = findViewById(R.id.main_bottom_menu);
        mBottomNavigationView.setLabelVisibilityMode(1);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        TeamFragment teamFragment = new TeamFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, teamFragment).commit();
    }
}