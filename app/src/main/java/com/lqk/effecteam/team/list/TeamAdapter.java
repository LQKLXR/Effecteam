package com.lqk.effecteam.team.list;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;
import com.lqk.effecteam.team.TeamVirtualData;
import com.lqk.effecteam.team.join.JoinTeamAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By LiuQK on 2021/3/29
 * Describe: 团队列表的Adapter，现在还在模拟数据
 */
public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    /* 要显示的团队列表 */
    private List<Team> teamList;

    public TeamAdapter(List<Team> teamList) {
        this.teamList = teamList;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_team_list, parent, false);
        TeamViewHolder teamViewHolder = new TeamViewHolder(view);
        return teamViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        String teamName = teamList.get(position).getTeamName();
        String teamInfo = teamList.get(position).getTeamInfo();
        holder.mTeamName.setText(teamName);
        holder.mTeamInfo.setText(teamInfo);
        /*原形头像设置*/
        if(teamName.length() > 2){
            holder.mTeamHead.setText(teamName.substring(teamName.length() - 2));
        }
        else {
            holder.mTeamHead.setText(teamName);
        }

    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }

    /**
     * 重新设置团队列表显示数据
     * @param teamList 新传入的团队列表数据
     */
    public void setNewTeamList(List<Team> teamList){
        this.teamList = teamList;
        notifyDataSetChanged();
    }

    class TeamViewHolder extends RecyclerView.ViewHolder{
        /*头像*/
        private TextView mTeamHead;
        /*名称*/
        private TextView mTeamName;
        /*简介*/
        private TextView mTeamInfo;

        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);
            mTeamHead = itemView.findViewById(R.id.viewholder_team_list_teamhead);
            mTeamName = itemView.findViewById(R.id.viewholder_team_list_teamname);
            mTeamInfo = itemView.findViewById(R.id.viewholder_team_list_teaminfo);
            /*点击事件*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                }
            });
        }
    }

    /*private OnTeamItemClickListener onTeamItemClickListener;

    public interface OnTeamItemClickListener{
        void onTeamClick(int position);
    }

    public void setOnTeamItemClickListener(OnTeamItemClickListener onTeamItemClickListener){
        this.onTeamItemClickListener = onTeamItemClickListener;
    }*/
}
