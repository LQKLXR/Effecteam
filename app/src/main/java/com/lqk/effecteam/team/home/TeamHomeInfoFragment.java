package com.lqk.effecteam.team.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lqk.effecteam.R;
import com.lqk.effecteam.team.member.TeamMemberListActivity;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Create By LiuQK on 2021/4/9
 * Describe:
 */
public class TeamHomeInfoFragment extends Fragment {

    private SuperTextView mTeamNumber;

    private SuperTextView mTeamName;

    private SuperTextView mTeamOrganization;

    private SuperTextView mTeamOwner;

    private SuperTextView mTeamCreateTime;

    private SuperTextView mTeamPeople;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_home_info, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mTeamName = view.findViewById(R.id.team_home_info_team_name);
        mTeamNumber = view.findViewById(R.id.team_home_info_team_number);
        mTeamOrganization = view.findViewById(R.id.team_home_info_team_organization);
        mTeamOwner = view.findViewById(R.id.team_home_info_team_owner);
        mTeamCreateTime = view.findViewById(R.id.team_home_info_team_create_time);
        mTeamPeople = view.findViewById(R.id.team_home_info_team_people);

        // TODO 测试数据
        mTeamName.setRightString("测试名称");
        mTeamNumber.setRightString("测试的号码");
        mTeamOrganization.setRightString("测试的机构");
        mTeamOwner.setRightString("测试拥有者");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        mTeamCreateTime.setRightString(simpleDateFormat.format(date));
        mTeamPeople.setRightString("50人");

        /*添加监听器*/
        addListener();
    }

    private void addListener() {
        mTeamPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TeamMemberListActivity.class);
                // TODO 需要把团队UID传进去，用来获得成员列表
                startActivity(intent);
            }
        });
    }


}
