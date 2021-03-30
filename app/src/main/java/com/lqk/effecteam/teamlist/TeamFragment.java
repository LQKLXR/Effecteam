package com.lqk.effecteam.teamlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

/**
 * Create By LiuQK on 2021/3/29
 * Describe: 团队界面的列表
 */
public class TeamFragment extends Fragment {

    private ImageButton mAddButton;
    private SearchView mSearchView;
    private PopupMenu mPopupMenu;
    private RecyclerView mRecyclerView;

    private PopupMenu.OnMenuItemClickListener onMenuItemClickListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.team_list_join_team_menu:
                    /*点击了加入团队的菜单项*/
                    break;
                case R.id.team_list_create_team_menu:
                    /*点击了创建团队的菜单项*/
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
        TeamAdapter teamAdapter = new TeamAdapter();
        mRecyclerView.setAdapter(teamAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),1));
        addListener();
        
    }

    /**
     * 添加监听器
     */
    private void addListener(){
        /*搜索框加上监听器*/
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /*搜索框搜索提交*/
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                /*搜索框文字变化*/
                return false;
            }
        });

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
    }
}
