package com.lqk.effecteam.team.join;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.data.TeamData;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;

import java.util.List;

/**
 * Create By LiuQK on 2021/3/31
 * Describe:
 */
public class JoinTeamAdapter extends RecyclerView.Adapter<JoinTeamAdapter.JoinTeamViewHolder> {

    private List<TeamData> teamDataList;

    private JoinTeamActivity joinTeamActivity;

    public JoinTeamAdapter(List<TeamData> teamDataList, JoinTeamActivity joinTeamActivity) {
        this.teamDataList = teamDataList;
        this.joinTeamActivity = joinTeamActivity;
    }

    @NonNull
    @Override
    public JoinTeamAdapter.JoinTeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_join_team, parent, false);
        JoinTeamViewHolder joinTeamViewHolder = new JoinTeamViewHolder(view);
        return joinTeamViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull JoinTeamAdapter.JoinTeamViewHolder holder, int position) {

        holder.mTeamName.setText(teamDataList.get(position).getName());
        holder.mTeamNumber.setText("团队号码: " + teamDataList.get(position).getNumber());
        holder.mTeamOwner.setText("团队拥有者: " + teamDataList.get(position).getOwnerName());
        holder.mTeamOrganization.setText("所属机构: " + teamDataList.get(position).getInstitution());
        holder.teamId = teamDataList.get(position).getId();

    }

    @Override
    public int getItemCount() {
        return teamDataList.size();
    }

    public List<TeamData> getTeamDataList() {
        return teamDataList;
    }

    public void setTeamDataList(List<TeamData> teamDataList) {
        this.teamDataList = teamDataList;
    }

    class JoinTeamViewHolder extends RecyclerView.ViewHolder{

        private TextView mTeamName;
        private TextView mTeamNumber;
        private TextView mTeamOwner;
        private TextView mTeamOrganization;
        private SuperButton mRequestButton;

        private int teamId;

        public JoinTeamViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        /**
         * 绑定组件ID
         * @param view
         */
        private void initView(View view){
            mTeamName = view.findViewById(R.id.viewholder_join_team_name);
            mTeamNumber = view.findViewById(R.id.viewholder_join_team_number);
            mTeamOwner = view.findViewById(R.id.viewholder_join_team_ower);
            mTeamOrganization = view.findViewById(R.id.viewholder_join_team_org);
            mRequestButton = view.findViewById(R.id.viewholder_join_team_request_button);
            addListener();
        }

        /**
         * 绑定监听器
         */
        private void addListener(){

            mRequestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    joinTeamActivity.applyForTeam(teamId);
                }
            });
        }
    }

}
