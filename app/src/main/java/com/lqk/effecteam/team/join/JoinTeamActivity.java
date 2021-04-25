package com.lqk.effecteam.team.join;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.common.HttpUtil;
import com.lqk.effecteam.common.data.TeamData;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 加入一个队伍的Activity
 */
public class JoinTeamActivity extends BaseActivity {

    private TitleBar mTitleBar;
    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private JoinTeamAdapter mJoinTeamAdapter;
    private List<TeamData> mTeamDataList;

    public static final int BACK_TO_TEAM_RESULT = 3;

    /*加载转圈*/
    private MaterialDialog mMaterialDialog;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1: // 处理网络异常
                    Toast.makeText(JoinTeamActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2: // 处理搜索
                    List<TeamData> teamDatas = (List<TeamData>) msg.obj;
                    if (teamDatas.size() == 0) {
                        Toast.makeText(JoinTeamActivity.this, "没有满足条件的队伍！", Toast.LENGTH_SHORT).show();
                    } else {
                        mTeamDataList = teamDatas;
                        mJoinTeamAdapter.setTeamDataList(mTeamDataList);
                        mJoinTeamAdapter.notifyDataSetChanged();
                    }
                    break;
                case 3: // 处理申请加入队伍
                    Toast.makeText(JoinTeamActivity.this, "申请成功", Toast.LENGTH_SHORT).show();
                    mMaterialDialog.dismiss();
                    break;
                case 4:
                    Toast.makeText(JoinTeamActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();
                    mMaterialDialog.dismiss();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_team);
        initView();
    }

    private void initView() {
        mMaterialDialog = new MaterialDialog.Builder(JoinTeamActivity.this).content(R.string.loadingDialog)
                .progress(true, 0)
                .progressIndeterminateStyle(false).build();
        mTitleBar = findViewById(R.id.join_team_title_bar);
        mSearchView = findViewById(R.id.join_team_search);
        mRecyclerView = findViewById(R.id.join_team_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(JoinTeamActivity.this);

        mJoinTeamAdapter = new JoinTeamAdapter(new ArrayList<>(), JoinTeamActivity.this);
        mRecyclerView.setAdapter(mJoinTeamAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(JoinTeamActivity.this, 1));
        addListener();

    }

    private void addListener() {
        mTitleBar.setLeftClickListener(v -> {
            Intent intent = new Intent();
            setResult(BACK_TO_TEAM_RESULT);
            finish();
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                SharedPreferences sharedPreferences = getSharedPreferences(HttpUtil.Shared_File_Name, Context.MODE_PRIVATE);
                int userId = sharedPreferences.getInt("userId", 0);

                String url = "searchTeam?userId=" + userId + "&name=" + query;
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
                        List<TeamData> teamDatas = gson.fromJson(body, new TypeToken<List<TeamData>>() {
                        }.getType());
                        Message message = Message.obtain();
                        message.what = 2;
                        message.obj = teamDatas;
                        handler.sendMessage(message);
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    /**
     * 申请加入队伍
     * @param teamId
     */
    public void applyForTeam(int teamId) {
        mMaterialDialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences(HttpUtil.Shared_File_Name, Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);

        String url = "applyForTeam?userId=" + userId + "&teamId=" + teamId;
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
                Message message = Message.obtain();
                if (body.equals("success")) {
                    message.what = 3;
                }
                else {
                    message.what = 4;
                    message.obj = body;
                }
                handler.sendMessage(message);
            }
        });
    }


}