package com.lqk.effecteam.team.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.lqk.effecteam.R;
import com.lqk.effecteam.common.util.HttpUtil;
import com.lqk.effecteam.common.data.TeamData;
import com.lqk.effecteam.team.member.TeamMemberListActivity;
import com.xuexiang.xui.widget.layout.ExpandableLayout;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.io.IOException;
import java.text.SimpleDateFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Create By LiuQK on 2021/4/9
 * Describe:
 */
public class TeamHomeInfoFragment extends Fragment {

    /*团队号码*/
    private SuperTextView mTeamNumber;
    /*团队名称*/
    private SuperTextView mTeamName;
    /*团队机构*/
    private SuperTextView mTeamOrganization;
    /*团队拥有者*/
    private SuperTextView mTeamOwner;
    /*团队创建时间*/
    private SuperTextView mTeamCreateTime;
    /*团队人数*/
    private SuperTextView mTeamPeople;
    /*团队简介提示栏*/
    private SuperTextView mTeamInfo;
    /*团队简介指示箭头-弹出的Layout*/
    private ExpandableLayout mTeamInfoExpandLayout;
    /*团队简介弹出信息-弹出Layout内容*/
    private TextView mTeamInfoText;



    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1: // 异常网络情况
                    Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2: // 设置网络请求的队伍信息
                    TeamData teamData = (TeamData) msg.obj;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    mTeamName.setRightString(teamData.getName());
                    mTeamNumber.setRightString(teamData.getNumber());
                    mTeamOrganization.setRightString(teamData.getInstitution());
                    mTeamOwner.setRightString(teamData.getOwnerName());
                    mTeamPeople.setRightString(teamData.getPeopleNumber() + "人");
                    mTeamCreateTime.setRightString(simpleDateFormat.format(teamData.getCreateTime()));
                    mTeamInfoText.setText(teamData.getTeamInfo());
                    break;
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_home_info, container, false);
        initView(view);
        loadTeamInfo();
        return view;
    }

    private void initView(View view) {
        mTeamName = view.findViewById(R.id.team_home_info_team_name);
        mTeamNumber = view.findViewById(R.id.team_home_info_team_number);
        mTeamOrganization = view.findViewById(R.id.team_home_info_team_organization);
        mTeamOwner = view.findViewById(R.id.team_home_info_team_owner);
        mTeamCreateTime = view.findViewById(R.id.team_home_info_team_create_time);
        mTeamPeople = view.findViewById(R.id.team_home_info_team_people);
        mTeamInfo = view.findViewById(R.id.team_home_info_team_info);
        mTeamInfoExpandLayout = view.findViewById(R.id.team_home_info_team_info_expand);
        mTeamInfoText = view.findViewById(R.id.team_home_info_team_info_text);

        /*添加监听器*/
        addListener();
    }

    private void addListener() {

        int teamId = getActivity().getIntent().getIntExtra("teamId", 0);

        mTeamPeople.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TeamMemberListActivity.class);
            intent.putExtra("teamId", teamId);
            startActivity(intent);
        });

        /*弹出式 Layout 的效果设置*/
        mTeamInfoExpandLayout.setOnExpansionChangedListener((expansion, state) -> {
            if (mTeamInfo != null && mTeamInfo.getRightIconIV() != null) {
                mTeamInfo.getRightIconIV().setRotation(expansion * 90);
            }
        });
        mTeamInfo.setOnSuperTextViewClickListener(superTextView -> {
            if (mTeamInfoExpandLayout != null) {
                mTeamInfoExpandLayout.toggle();
            }
        });
    }


    /**
     * 加载队伍信息
     */
    private void loadTeamInfo(){
        int teamId = getActivity().getIntent().getIntExtra("teamId", 0);
        String url = "getTeamInfo?teamId=" + teamId;
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
                // 把 Team 的 json 转成对象，带到 Handler 处理
                TeamData teamData = gson.fromJson(body, TeamData.class);
                Message message = Message.obtain();
                message.what = 2;
                message.obj = teamData;
                handler.sendMessage(message);
            }
        });
    }


}
