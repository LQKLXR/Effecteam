package com.lqk.effecteam.mine.dynamic;

import android.os.Bundle;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.project.dynamic.DynamicsFragment;
import com.xuexiang.xui.widget.actionbar.TitleBar;

public class MineDynamicActivity extends BaseActivity {

    private TitleBar mMineDynamicTitleBar;
    private DynamicsFragment mDynamicsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_dynamic);
        initView();
    }

    private void initView() {
        mMineDynamicTitleBar = findViewById(R.id.mine_dynamic_title_bar);
        mMineDynamicTitleBar.setLeftClickListener(v -> finish());
        mDynamicsFragment = new DynamicsFragment();
        mDynamicsFragment.setType(DynamicsFragment.MINE);
        getSupportFragmentManager().beginTransaction().add(R.id.mine_dynamic_fragment_layout, mDynamicsFragment).commit();
    }
}