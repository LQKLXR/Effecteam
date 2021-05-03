package com.lqk.effecteam.task.create;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.common.util.HttpUtil;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PickActivity extends BaseActivity {

    private TitleBar mTitleBar;
    private RecyclerView mRecyclerView;
    private PickAdapter mPickAdapter;

    private ArrayList<Integer> resultList;

    private static final String TAG = "PickActivity";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1: //网络异常情况
                    Toast.makeText(PickActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    List<PickItem> pickItems = (List<PickItem>) msg.obj;
                    mPickAdapter.setPickItemList(pickItems);
                    mPickAdapter.notifyDataSetChanged();
                    break;
                case 3:
                    break;
            }
        }
    };

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
        mPickAdapter = new PickAdapter(new ArrayList<>(), resultList);
        mRecyclerView.setAdapter(mPickAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, 1));
        addListener();
    }


    private void addListener() {
        SharedPreferences sharedPreferences = getSharedPreferences(HttpUtil.Shared_File_Name, MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);
        /*点击返回按钮的时候，携带数据返回上一个Activity*/
        mTitleBar.setLeftClickListener(v -> {
            finish();
        });
        int projectId = getIntent().getIntExtra("projectId", 0);
        String url = null;

        /*选择人员*/
        if (getIntent().getIntExtra("type", 0) == 1) {
            mTitleBar.setTitle("选择参与人员");
            url = "getProjectUserList?projectId=" + projectId ;
            /*点击确定的时候*/
            mTitleBar.addAction(new TitleBar.TextAction("确定") {
                @Override
                public void performAction(View view) {
                    Intent intent = new Intent();
                    intent.putIntegerArrayListExtra("taskUserList", resultList);
                    setResult(0, intent);
                    finish();
                }
            });
        }
        /*选择文档*/
        else {
            mTitleBar.setTitle("选择参考文档");
            url = "getProjectDocList?projectId=" + projectId + "&userId=" + userId;
            /*点击确定的时候*/
            mTitleBar.addAction(new TitleBar.TextAction("确定") {
                @Override
                public void performAction(View view) {
                    Intent intent = new Intent();
                    intent.putIntegerArrayListExtra("taskDocList", resultList);
                    setResult(0, intent);
                    finish();
                }
            });
        }

        HttpUtil.connectInternet(url, null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = Message.obtain();
                message.what = 1;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = new String(response.body().bytes());
                Gson gson = new Gson();
                List<PickItem> pickItems = gson.fromJson(body, new TypeToken<List<PickItem>>(){}.getType());
                Message message = Message.obtain();
                message.what = 2;
                message.obj = pickItems;
                handler.sendMessage(message);
            }
        });



    }

}