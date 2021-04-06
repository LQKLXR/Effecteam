package com.lqk.effecteam.team.home;

import android.os.Bundle;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;

/**
 * 点击队伍的时候，进入该Activity
 */
public class TeamHomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_home);
    }
}