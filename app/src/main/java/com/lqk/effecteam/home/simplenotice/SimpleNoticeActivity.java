package com.lqk.effecteam.home.simplenotice;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.common.entity.notice.SimpleNotice;
import com.lqk.effecteam.common.util.HttpUtil;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SimpleNoticeActivity extends BaseActivity {

    private TitleBar mTitleBar;
    private RecyclerView mRecyclerView;
    private SimpleNoticeAdapter mSimpleNoticeAdapter;
    private List<SimpleNotice> simpleNoticeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_notice);

        initView();
    }

    private void initView() {
        mTitleBar = findViewById(R.id.simple_notice_title);
        mTitleBar.setLeftClickListener(v -> finish());
        mRecyclerView = findViewById(R.id.simple_notice_recyclerview);
        mSimpleNoticeAdapter = new SimpleNoticeAdapter(new ArrayList<>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(SimpleNoticeActivity.this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(SimpleNoticeActivity.this, 1));
        mRecyclerView.setAdapter(mSimpleNoticeAdapter);
        Gson gson = new Gson();
        String json = getIntent().getStringExtra("simpleNoticeListJson");
        List<SimpleNotice> simpleNotices = gson.fromJson(json, new TypeToken<List<SimpleNotice>>(){}.getType());
        simpleNoticeList = simpleNotices;
        mSimpleNoticeAdapter.setSimpleNoticeList(simpleNoticeList);
        mSimpleNoticeAdapter.notifyDataSetChanged();

        SharedPreferences sp = getSharedPreferences(HttpUtil.Shared_File_Name, MODE_PRIVATE);
        int userId = sp.getInt("userId", 0);

        for (int i = 0; i < simpleNoticeList.size(); i++) {
            String url = "readSimpleNotice?userId=" + userId + "&noticeId=" + simpleNoticeList.get(i).getId();
            HttpUtil.connectInternet(url, null, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }
            });
        }
    }
}