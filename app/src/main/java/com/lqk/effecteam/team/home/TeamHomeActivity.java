package com.lqk.effecteam.team.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.MainActivity;
import com.lqk.effecteam.team.chat.TeamChatFragment;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.tabbar.EasyIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 点击队伍的时候，进入该Activity
 */
public class TeamHomeActivity extends BaseActivity {

    /*顶部标题栏*/
    private TitleBar mTitleBar;

    /*翻页式视图*/
    private EasyIndicator mEasyIndicator;
    private ViewPager mViewPager;

    /*翻页式的每个Fragment*/
    private TeamChatFragment mTeamChatFragment;
    private TeamHomeProjectFragment mTeamHomeProjectFragment;
    private TeamHomeDataFragment mTeamHomeDataFragment;
    private TeamHomeInfoFragment mTeamHomeInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_team_home);
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        mTitleBar = findViewById(R.id.team_home_title_bar);
        mEasyIndicator = findViewById(R.id.team_home_easy_indicator);
        mViewPager = findViewById(R.id.team_home_view_pager);

        String[] titles = {"团队聊天", "项目列表", "共享资料", "团队信息"};
        mEasyIndicator.setTabTitles(titles);
        List<Fragment> fragmentList = new ArrayList<>();
        mTeamChatFragment = new TeamChatFragment();
        mTeamHomeProjectFragment = new TeamHomeProjectFragment();
        mTeamHomeDataFragment = new TeamHomeDataFragment();
        mTeamHomeInfoFragment = new TeamHomeInfoFragment();
        fragmentList.add(mTeamChatFragment);
        fragmentList.add(mTeamHomeProjectFragment);
        fragmentList.add(mTeamHomeDataFragment);
        fragmentList.add(mTeamHomeInfoFragment);
        mEasyIndicator.setViewPager(mViewPager, new FragmentAdapter<>(getSupportFragmentManager(), fragmentList));

        mTitleBar.setLeftClickListener(v -> {
            Intent intent = new Intent(TeamHomeActivity.this, MainActivity.class);
            intent.putExtra("backToTeam", true);
            startActivity(intent);
            finish();
        });
    }


    /**
     * 判断进入该页面的时候应该进入那个Fragment
     */
    @Override
    protected void onStart() {
        super.onStart();
        /*回到团队信息界面*/
        if (getIntent().getBooleanExtra("BackToInfo", false)) {
            mViewPager.setCurrentItem(3);
        }
    }
}