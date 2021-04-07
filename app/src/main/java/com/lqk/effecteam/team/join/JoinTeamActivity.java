package com.lqk.effecteam.team.join;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.main.MainActivity;
import com.lqk.effecteam.team.TeamVirtualData;
import com.lqk.effecteam.team.list.Team;

import java.util.ArrayList;
import java.util.List;

public class JoinTeamActivity extends BaseActivity {

    private ImageButton mBackButton;
    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private JoinTeamAdapter mJoinTeamAdapter;
    private List<Team> mTeamList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_team);
        initView();
    }

    private void initView(){
        mBackButton = findViewById(R.id.join_team_back_button);
        mSearchView = findViewById(R.id.join_team_search);
        mRecyclerView = findViewById(R.id.join_team_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(JoinTeamActivity.this);
        /* TODO 虚拟数据 */
        mTeamList = TeamVirtualData.teamArrayList;
        mJoinTeamAdapter = new JoinTeamAdapter(mTeamList);
        mRecyclerView.setAdapter(mJoinTeamAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(JoinTeamActivity.this,1));
        addListener();
    }

    private void addListener(){
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinTeamActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<Team> newTeamList = filter(mTeamList, query);
                mJoinTeamAdapter.setTeamList(newTeamList);
                mJoinTeamAdapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    /**
     * 名称搜索过滤器
     * @param teamList
     * @param name
     * @return
     */
    public List<Team> filter(List<Team> teamList, String name){
        List<Team> newTeamList = new ArrayList<>();
        for (Team t : teamList){
            if (t.getTeamName().contains(name)){
                newTeamList.add(t);
            }
        }
        return newTeamList;
    }

}