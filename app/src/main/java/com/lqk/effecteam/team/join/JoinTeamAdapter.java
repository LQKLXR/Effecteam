package com.lqk.effecteam.team.join;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By LiuQK on 2021/3/31
 * Describe:
 */
public class JoinTeamAdapter extends RecyclerView.Adapter<JoinTeamAdapter.JoinTeamViewHolder> {



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
        holder.mTeamName.setText(holder.mTeamName.getText().toString() + teamNameList.get(position));
        holder.mTeamOrganization.setText(holder.mTeamOrganization.getText().toString() + teamOrganizationList.get(position));
        holder.mTeamOwner.setText(holder.mTeamOwner.getText().toString() + teamOwnerList.get(position));
        holder.mTeamNumber.setText(holder.mTeamNumber.getText().toString() + teamNumberList.get(position));
        holder.mTeamCount.setText(holder.mTeamCount.getText().toString() + teamCountList.get(position) + "人");
    }

    @Override
    public int getItemCount() {
        /* TODO 模拟数据待修改为真实数据 */
        return teamNameList.size();
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

    /*模拟数据****************************************************************/
    /* TODO 以后要删除的虚拟数据 */
    public void search(String string){
        
    }

    /*模拟全部团队*/
    private static List<String> teamNameList = new ArrayList<>();
    private static List<String> teamOwnerList = new ArrayList<>();
    private static List<String> teamOrganizationList = new ArrayList<>();
    private static List<String> teamNumberList = new ArrayList<>();
    private static List<String> teamCountList = new ArrayList<>();

    static {
        /*第1支团队*/
        teamNameList.add("测试团队1");
        teamOwnerList.add("测试拥有者1");
        teamOrganizationList.add("测试机构1");
        teamNumberList.add("1111111");
        teamCountList.add("111");
        /*第2支团队*/
        teamNameList.add("测试团队2");
        teamOwnerList.add("测试拥有者2");
        teamOrganizationList.add("测试机构2");
        teamNumberList.add("2222");
        teamCountList.add("222");
        /*第3支团队*/
        teamNameList.add("测试团队3");
        teamOwnerList.add("测试拥有者3");
        teamOrganizationList.add("测试机构3");
        teamNumberList.add("333333");
        teamCountList.add("33");
        /*第4支团队*/
        teamNameList.add("测试团队4");
        teamOwnerList.add("测试拥有者4");
        teamOrganizationList.add("测试机构4");
        teamNumberList.add("4444444");
        teamCountList.add("444");
        /*第5支团队*/
        teamNameList.add("测试团队5");
        teamOwnerList.add("测试拥有者5");
        teamOrganizationList.add("测试机构5");
        teamNumberList.add("5555555");
        teamCountList.add("5");
        /*第6支团队*/
        teamNameList.add("测试团队6");
        teamOwnerList.add("测试拥有者6");
        teamOrganizationList.add("测试机构6");
        teamNumberList.add("6666666");
        teamCountList.add("666");

    }
}
