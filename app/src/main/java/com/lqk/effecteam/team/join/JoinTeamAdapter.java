package com.lqk.effecteam.team.join;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;
import com.lqk.effecteam.team.list.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By LiuQK on 2021/3/31
 * Describe:
 */
public class JoinTeamAdapter extends RecyclerView.Adapter<JoinTeamAdapter.JoinTeamViewHolder> {

    private List<Team> teamList;

    public JoinTeamAdapter(List<Team> teamList) {
        this.teamList = teamList;
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
        /* TODO 模拟数据待修改为真实数据 */
        holder.mTeamName.setText(teamList.get(position).getTeamName());
        holder.mTeamOrganization.setText("团队机构: " + teamList.get(position).getTeamOrganization());
        holder.mTeamOwner.setText("创始人: 测试");
        holder.mTeamNumber.setText("团队号码: " + teamList.get(position).getTeamNumber());
        holder.mTeamCount.setText("团队人数: 5人");
    }

    @Override
    public int getItemCount() {
        /* TODO 模拟数据待修改为真实数据 */
        return teamList.size();
    }

    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    class JoinTeamViewHolder extends RecyclerView.ViewHolder{

        private TextView mTeamName;
        private TextView mTeamNumber;
        private TextView mTeamOwner;
        private TextView mTeamOrganization;
        private TextView mTeamCount;
        private Button mRequestButton;

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
            mTeamCount = view.findViewById(R.id.viewholder_join_team_count);
            mRequestButton = view.findViewById(R.id.viewholder_join_team_request_button);
            addListener();
        }

        /**
         * 绑定监听器
         */
        private void addListener(){
            /* TODO 添加申请团队的时候的网络接口 */
            mRequestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

}
