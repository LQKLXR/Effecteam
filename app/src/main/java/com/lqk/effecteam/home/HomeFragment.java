package com.lqk.effecteam.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lqk.effecteam.R;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.textview.badge.Badge;
import com.xuexiang.xui.widget.textview.badge.BadgeView;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

public class HomeFragment extends Fragment {

    private TitleBar mHomeTitleBar;

    /*团队管理助手*/
    private SuperTextView mHomeTeamAssistant;
    private Badge mTeamAssistantBadge;

    /*任务助手*/
    private SuperTextView mHomeTaskAssistant;
    private Badge mTaskAssistantBadge;

    /*待办助手*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mHomeTitleBar = view.findViewById(R.id.home_title_bar);
        mHomeTeamAssistant = view.findViewById(R.id.home_team_assistant);
        mHomeTitleBar.disableLeftView();
        mHomeTaskAssistant = view.findViewById(R.id.home_task_assistant);

        mHomeTaskAssistant.setRightString("          ");
        mTaskAssistantBadge = new BadgeView(getContext())
                .bindTarget(mHomeTaskAssistant.getRightTextView())
                .setBadgeGravity(Gravity.END | Gravity.CENTER)
                .setBadgePadding(2, true)
                .setBadgeTextSize(10, true)
                .setBadgeNumber(3);
    }
}