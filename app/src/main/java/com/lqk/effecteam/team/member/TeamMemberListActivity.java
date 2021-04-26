package com.lqk.effecteam.team.member;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.lqk.effecteam.task.create.PickItem;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 *  查看团队成员列表的 Activity
 */
public class TeamMemberListActivity extends BaseActivity {

    /*顶部导航栏*/
    private TitleBar mTitleBar;

    private RecyclerView mTeamMemberRecyclerview;
    private TeamMemberAdapter mTeamMemberAdapter;

    private List<PickItem> pickItemList;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(TeamMemberListActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    List<PickItem> pickItems = (List<PickItem>) msg.obj;
                    pickItemList = pickItems;
                    mTeamMemberAdapter.setPickItemList(pickItemList);
                    mTeamMemberAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_member_list);
        initView();
        loadTeamMember();
    }

    private void initView() {
        mTitleBar = findViewById(R.id.team_member_title_bar);
        mTeamMemberRecyclerview = findViewById(R.id.team_member_recyclerview);

        mTeamMemberAdapter = new TeamMemberAdapter(new ArrayList<>());
        mTeamMemberRecyclerview.setAdapter(mTeamMemberAdapter);
        mTeamMemberRecyclerview.setLayoutManager(new LinearLayoutManager(TeamMemberListActivity.this));
        mTeamMemberRecyclerview.addItemDecoration(new DividerItemDecoration(TeamMemberListActivity.this,1));

        addListener();
    }

    private void addListener() {
        mTitleBar.setLeftClickListener(v -> {
            finish();
        });
    }

    private void loadTeamMember(){
        int teamId = getIntent().getIntExtra("teamId", 0);
        String url = "getTeamUserList?teamId=" + teamId;
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