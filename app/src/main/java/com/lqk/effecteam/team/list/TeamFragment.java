package com.lqk.effecteam.team.list;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lqk.effecteam.R;
import com.lqk.effecteam.common.HttpUtil;
import com.lqk.effecteam.common.entity.Team;
import com.lqk.effecteam.team.create.CreateTeamActivity;
import com.lqk.effecteam.team.join.JoinTeamActivity;
import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.popupwindow.popup.XUISimplePopup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Create By LiuQK on 2021/3/29
 * Describe: 团队界面的列表
 */
public class TeamFragment extends Fragment {
    /*标题栏*/
    private TitleBar mTitleBar;
    /*搜索框*/
    private SearchView mSearchView;
    /*弹出式菜单*/
    private XUISimplePopup mXUISimplePopup;
    /*循环视图显示团队*/
    private RecyclerView mRecyclerView;
    /*Adapter*/
    private TeamAdapter mTeamAdapter;
    /*要显示的团队列表*/
    private List<Team> mTeamList;

    /*下拉刷新*/
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    mSwipeRefreshLayout.setRefreshing(false);
                    List<Team> teams = (List<Team>) msg.obj;
                    mTeamList = teams;
                    mTeamAdapter.setNewTeamList(teams);
                    mTeamAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    public static final int BACK_TO_TEAM_REQUEST = 3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team, container, false);
        initView(view);
        return view;
    }

    /**
     * 初始化各个组件
     * @param view
     */
    private void initView(View view){
        mTitleBar = view.findViewById(R.id.team_list_title_bar);
        mTitleBar.disableLeftView();
        mTitleBar.addAction(new TitleBar.TextAction("添加") {
            @Override
            public void performAction(View view) {
                mXUISimplePopup.showDown(view);
            }
        });
        mSearchView = view.findViewById(R.id.team_list_search);
        mRecyclerView = view.findViewById(R.id.team_list_recyclerview);
        mSwipeRefreshLayout = view.findViewById(R.id.team_list_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mXUISimplePopup = new XUISimplePopup(getContext(), new AdapterItem[]{new AdapterItem("搜索团队"), new AdapterItem("创建团队")}).create((adapter, item, position) -> {
            Intent intent = null;
            switch (position){
                case 0:
                    /*点击了加入团队的菜单项，进入加入团队页面*/
                    intent = new Intent(getActivity(), JoinTeamActivity.class);
                    startActivity(intent);
                    break;
                case 1:
                    /*点击了创建团队的菜单项，进入创建团队页面 */
                    intent = new Intent(getActivity(), CreateTeamActivity.class);
                    startActivityForResult(intent, BACK_TO_TEAM_REQUEST);
                    break;
            }
        });
        mTeamAdapter = new TeamAdapter(new ArrayList<Team>(), getActivity());
        mRecyclerView.setAdapter(mTeamAdapter);
        /* 添加分割线 */
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),1));
        /*添加各种监听器*/
        addListener();
        /*加载数据*/
        loadTeamList();
        
    }

    /**
     * 添加监听器
     */
    private void addListener(){
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /*搜索框搜索提交*/
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.trim();
                if(newText == null || newText.length() == 0){
                    mTeamAdapter.setNewTeamList(mTeamList);
                }
                else {
                    mTeamAdapter.setNewTeamList(teamStringFilter(mTeamList, newText));
                }
                return true;
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(() -> loadTeamList());

    }

    /**
     * 过滤函数，实现团队根据名称过滤
     * @param original
     * @param text
     * @return
     */
    private List<Team> teamStringFilter(List<Team> original, String text){
        List<Team> newTeamList = new ArrayList<>();
        for (Team t : original){
            if(t.getName().contains(text)){
                newTeamList.add(t);
            }
        }
        return newTeamList;
    }


    public void loadTeamList(){
        mSwipeRefreshLayout.setRefreshing(true);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(HttpUtil.Shared_File_Name, Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);
        String url = "getTeamList?userId=" + userId;
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
                List<Team> teams = gson.fromJson(body, new TypeToken<List<Team>>(){}.getType());
                Message message = Message.obtain();
                message.what = 2;
                message.obj = teams;
                handler.sendMessage(message);
            }
        });
    }
}
