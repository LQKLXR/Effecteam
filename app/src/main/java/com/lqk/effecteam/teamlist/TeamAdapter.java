package com.lqk.effecteam.teamlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By LiuQK on 2021/3/29
 * Describe: 团队列表的Adapter，现在还在模拟数据
 */
public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {
    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_team_list, parent, false);
        TeamViewHolder teamViewHolder = new TeamViewHolder(view);
        return teamViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        String teamName = teamNameList.get(position);
        String teamInfo = teamInfoList.get(position);
        holder.mTeamName.setText(teamName);
        holder.mTeamInfo.setText(teamInfo);
        if(teamName.length() > 2){
            holder.mTeamHead.setText(teamName.substring(teamName.length() - 2));
        }
        else {
            holder.mTeamHead.setText(teamName);
        }

    }

    @Override
    public int getItemCount() {
        return teamInfoList.size();
    }

    class TeamViewHolder extends RecyclerView.ViewHolder{

        private TextView mTeamHead;
        private TextView mTeamName;
        private TextView mTeamInfo;

        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);
            mTeamHead = itemView.findViewById(R.id.viewholder_team_list_teamhead);
            mTeamName = itemView.findViewById(R.id.viewholder_team_list_teamname);
            mTeamInfo = itemView.findViewById(R.id.viewholder_team_list_teaminfo);
        }

    }


    public static List<String> teamNameList = new ArrayList<>();
    public static List<String> teamInfoList = new ArrayList<>();
    static {
        teamNameList.add("我的兄弟叫顺溜");
        teamNameList.add("彩色电视机");
        teamNameList.add("芜湖起飞");
        teamNameList.add("捞得不谈");

        teamInfoList.add("神枪手");
        teamInfoList.add("不看");
        teamInfoList.add("这波啊，这波啊，这波啊。这波是肉蛋冲击，这波是肉蛋冲击，这波是肉蛋冲击");
        teamInfoList.add("捞得淌口水");
    }
}
