package com.lqk.effecteam.task.create;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.team.TeamVirtualData;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.ArrayList;

public class PickActivity extends BaseActivity {

    private TitleBar mTitleBar;
    private RecyclerView mRecyclerView;
    private PickAdapter mPickAdapter;

    private ArrayList<Integer> resultList;

    private static final int PICK_MEMBER_RESULT = 1;
    private static final int PICK_DOC_RESULT = 2;

    private static final String TAG = "PickActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick);
        initView();
    }

    private void initView() {
        mTitleBar = findViewById(R.id.member_pick_title_bar);
        mRecyclerView = findViewById(R.id.pick_recyclerview);
        resultList = new ArrayList<>();
        mPickAdapter = new PickAdapter(null, resultList);
        mRecyclerView.setAdapter(mPickAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,1));
        addListener();
    }


    private void addListener(){
        /*点击返回按钮的时候，携带数据返回上一个Activity*/
        mTitleBar.setLeftClickListener(v -> {
            finish();
        });
        /*选择人员*/
        if (getIntent().getIntExtra("type", 0) == 1){
            mTitleBar.setTitle("选择参与人员");
            //TODO 选择人员的虚假数据
            mPickAdapter.setPickItemList(TeamVirtualData.pickUserArrayList);
            /*点击确定的时候*/
            mTitleBar.addAction(new TitleBar.TextAction("确定") {
                @Override
                public void performAction(View view) {
                    Intent intent = new Intent();
                    //TODO 选择的人员ID list放进去
                    Log.d(TAG, "选择人员返回结果" + resultList.toString());
                    intent.putIntegerArrayListExtra("memberList", resultList);
                    setResult(PICK_MEMBER_RESULT, intent);
                    finish();
                }
            });
        }
        /*选择文档*/
        else {
            mTitleBar.setTitle("选择参考文档");
            mPickAdapter.setPickItemList(TeamVirtualData.pickDocArrayList);
            /*点击确定的时候*/
            mTitleBar.addAction(new TitleBar.TextAction("确定") {
                @Override
                public void performAction(View view) {
                    Intent intent = new Intent();
                    //TODO 选择的文档ID list放进去
                    Log.d(TAG, "选择文档返回结果" + resultList.toString());
                    intent.putIntegerArrayListExtra("docList", resultList);
                    setResult(PICK_DOC_RESULT, intent);
                    finish();
                }
            });
        }

    }
}