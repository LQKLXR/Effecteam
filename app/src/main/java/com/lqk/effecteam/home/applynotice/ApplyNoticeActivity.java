package com.lqk.effecteam.home.applynotice;

import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.common.entity.notice.ApplyNotice;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.List;

public class ApplyNoticeActivity extends BaseActivity {

    private TitleBar mTitleBar;
    private RecyclerView mRecyclerView;
    private ApplyNoticeAdapter mApplyNoticeAdapter;
    private List<ApplyNotice> applyNoticeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_notice);

        initView();

        /* 拿到从前面传过来的 json */
        String json = getIntent().getStringExtra("applyNoticeListJson");
        Gson gson = new Gson();
        applyNoticeList = gson.fromJson(json, new TypeToken<List<ApplyNotice>>(){}.getType());
        mApplyNoticeAdapter.setApplyNoticeList(applyNoticeList);
        mApplyNoticeAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mTitleBar = findViewById(R.id.apply_notice_title);
        mTitleBar.setTitle("团队申请通知");
        mTitleBar.setLeftClickListener(v -> finish());
        mRecyclerView = findViewById(R.id.apply_notice_recyclerview);
        mApplyNoticeAdapter = new ApplyNoticeAdapter(ApplyNoticeActivity.this);
        mApplyNoticeAdapter.setApplyNoticeList(applyNoticeList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ApplyNoticeActivity.this));
        mRecyclerView.setAdapter(mApplyNoticeAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(ApplyNoticeActivity.this, 1));
    }

}