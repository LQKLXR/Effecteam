package com.lqk.effecteam.team.member;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;
import com.lqk.effecteam.task.create.PickItem;

import java.util.List;

/**
 * Create By LiuQK on 2021/4/24
 * Describe: 显示 Member 的 Adapter
 */
public class TeamMemberAdapter extends RecyclerView.Adapter<TeamMemberAdapter.TeamMemberHolder>{

    private List<PickItem> pickItemList;

    public TeamMemberAdapter(List<PickItem> pickItemList) {
        this.pickItemList = pickItemList;
    }

    @NonNull
    @Override
    public TeamMemberHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_team_member, parent, false);
        TeamMemberHolder teamMemberHolder = new TeamMemberHolder(view);
        return teamMemberHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamMemberHolder holder, int position) {
        holder.name.setText(pickItemList.get(position).getName());
        holder.id = pickItemList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return pickItemList.size();
    }

    public void setPickItemList(List<PickItem> pickItemList) {
        this.pickItemList = pickItemList;
    }

    class TeamMemberHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private int id;

        public TeamMemberHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.viewholder_team_member_name);
        }
    }
}
