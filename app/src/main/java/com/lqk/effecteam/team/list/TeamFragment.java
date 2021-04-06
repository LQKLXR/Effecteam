package com.lqk.effecteam.team.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;
import com.lqk.effecteam.team.TeamVirtualData;
import com.lqk.effecteam.team.create.CreateTeamActivity;
import com.lqk.effecteam.team.join.JoinTeamActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By LiuQK on 2021/3/29
 * Describe: 团队界面的列表
 */
public class TeamFragment extends Fragment {
    /*右上角添加菜单按钮*/
    private ImageButton mAddButton;
    /*搜索框*/
    private SearchView mSearchView;
    /*弹出式菜单*/
    private PopupMenu mPopupMenu;
    /*循环视图显示团队*/
    private RecyclerView mRecyclerView;
    /*Adapter*/
    private TeamAdapter mTeamAdapter;
    /*要显示的团队列表*/
    private List<Team> mTeamList;

    private PopupMenu.OnMenuItemClickListener onMenuItemClickListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Intent intent = null;
            switch (item.getItemId()){
                case R.id.team_list_join_team_menu:
                    /*点击了加入团队的菜单项，进入加入团队页面*/
                    intent = new Intent(getActivity(), JoinTeamActivity.class);
                    startActivity(intent);
                    break;
                case R.id.team_list_create_team_menu:
                    /*点击了创建团队的菜单项，进入创建团队页面 */
                    intent = new Intent(getActivity(), CreateTeamActivity.class);
                    startActivity(intent);
                    break;
            }
            return false;
        }
    };

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
        mAddButton = view.findViewById(R.id.team_list_add_button);
        mSearchView = view.findViewById(R.id.team_list_search);
        mRecyclerView = view.findViewById(R.id.team_list_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        /* TODO 是模拟的虚假数据 */
        mTeamList = TeamVirtualData.teamArrayList;
        mTeamAdapter = new TeamAdapter(mTeamList);
        /* 添加分割线 */
        mRecyclerView.setAdapter(mTeamAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),1));
        /*添加各种监听器*/
        addListener();
        
    }

    /**
     * 添加监听器
     */
    private void addListener(){
        /*添加按钮增加监听器*/
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*创建一个弹出式菜单*/
                mPopupMenu = new PopupMenu(getActivity(), mAddButton);
                /*注入菜单内容*/
                mPopupMenu.inflate(R.menu.team_list_add_menu);
                /*绑定监听器*/
                mPopupMenu.setOnMenuItemClickListener(onMenuItemClickListener);
                /*显示出来*/
                mPopupMenu.show();
            }
        });

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

    }

    /**
     * 过滤函数，实现团队根据名称过滤
     * @param original
     * @param text
     * @return
     */
    public List<Team> teamStringFilter(List<Team> original, String text){
        List<Team> newTeamList = new ArrayList<>();
        for (Team t : original){
            if(t.getTeamName().contains(text)){
                newTeamList.add(t);
            }
        }
        return newTeamList;
    }

}
