package com.lqk.effecteam.mine.task;

import android.os.Bundle;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.task.list.TaskFragment;
import com.xuexiang.xui.widget.actionbar.TitleBar;

public class MineTaskActivity extends BaseActivity {

    private TitleBar mTeamHomeTitleBar;
    private TaskFragment mTaskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_task);

        initView();
    }

    private void initView() {
        mTeamHomeTitleBar = findViewById(R.id.mine_task_title_bar);
        mTaskFragment = new TaskFragment();
        mTaskFragment.setType(1);
        addListener();
    }

    private void addListener() {
        mTeamHomeTitleBar.setLeftClickListener(v -> finish());
        getSupportFragmentManager().beginTransaction().add(R.id.mine_task_fragment_layout, mTaskFragment).commit();
    }

}