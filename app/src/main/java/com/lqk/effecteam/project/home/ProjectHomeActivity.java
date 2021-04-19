package com.lqk.effecteam.project.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.MainActivity;
import com.lqk.effecteam.project.dynamic.DynamicsFragment;
import com.lqk.effecteam.task.list.TaskFragment;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.tabbar.EasyIndicator;

import java.util.ArrayList;
import java.util.List;

public class ProjectHomeActivity extends BaseActivity {

    /*顶部标题栏*/
    private TitleBar mTitleBar;

    /*翻页式视图*/
    private EasyIndicator mEasyIndicator;
    private ViewPager mViewPager;

    private TaskFragment mTaskFragment;
    private DynamicsFragment mDynamicsFragment;
    private ProjectHomeDataFragment mProjectHomeDataFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_home);
        initView();
    }

    private void initView() {
        mTitleBar = findViewById(R.id.project_home_title_bar);
        mEasyIndicator = findViewById(R.id.project_home_easy_indicator);
        mViewPager = findViewById(R.id.project_home_view_pager);

        String[] titles = {"任务列表", "成员动态", "共享资料"};
        mEasyIndicator.setTabTitles(titles);
        List<Fragment> fragmentList = new ArrayList<>();
        mTaskFragment = new TaskFragment();
        mDynamicsFragment = new DynamicsFragment();
        mProjectHomeDataFragment = new ProjectHomeDataFragment();
        fragmentList.add(mTaskFragment);
        fragmentList.add(mDynamicsFragment);
        fragmentList.add(mProjectHomeDataFragment);
        mEasyIndicator.setViewPager(mViewPager, new FragmentAdapter<>(getSupportFragmentManager(), fragmentList));

        mTitleBar.setLeftClickListener(v -> {
            Intent intent = new Intent(ProjectHomeActivity.this, MainActivity.class);
            intent.putExtra("backToProject", true);
            startActivity(intent);
            finish();
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (getIntent().getBooleanExtra("backToTask", false)) {
            mViewPager.setCurrentItem(0);
        }
    }
}