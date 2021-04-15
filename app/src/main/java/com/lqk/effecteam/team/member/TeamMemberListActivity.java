package com.lqk.effecteam.team.member;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.team.home.TeamHomeActivity;
import com.lqk.effecteam.team.home.TeamHomeInfoFragment;
import com.xuexiang.xui.widget.actionbar.TitleBar;

/**
 *  查看团队成员列表的 Activity
 */
public class TeamMemberListActivity extends BaseActivity {

    /*顶部导航栏*/
    private TitleBar mTitleBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_member_list);
        initView();
    }

    private void initView() {
        mTitleBar = findViewById(R.id.team_member_title_bar);

        addListener();
    }

    private void addListener() {
        mTitleBar.setLeftClickListener(v -> {
            Intent intent = new Intent(TeamMemberListActivity.this, TeamHomeActivity.class);
            /*返回详细信息界面*/
            intent.putExtra("BackToInfo", true);
            startActivity(intent);
            finish();
        });
    }


}