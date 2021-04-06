package com.lqk.effecteam.team.join;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.main.MainActivity;

public class JoinTeamActivity extends BaseActivity {

    private ImageButton mBackButton;
    private SearchView mSearchView;
    private RecyclerView mRecyclerView;

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
        JoinTeamAdapter joinTeamAdapter = new JoinTeamAdapter();
        mRecyclerView.setAdapter(joinTeamAdapter);
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
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}