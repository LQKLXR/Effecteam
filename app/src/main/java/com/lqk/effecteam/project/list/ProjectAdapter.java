package com.lqk.effecteam.project.list;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.comparator.ProjectComparator;
import com.lqk.effecteam.common.entity.Project;
import com.lqk.effecteam.project.alter.ProjectAlertActivity;
import com.lqk.effecteam.project.home.ProjectHomeActivity;
import com.lqk.effecteam.team.home.TeamHomeProjectFragment;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

/**
 * Create By LiuQK on 2021/4/7
 * Describe:
 */
public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

    private static final String TAG = "ProjectAdapter";

    private ProjectComparator projectComparator;
    private List<Project> projectList;
    private Activity activity;
    private Fragment fragment;


    public ProjectAdapter(List<Project> projectList, Activity activity, Fragment fragment) {
        this.projectList = projectList;
        this.activity = activity;
        projectComparator = new ProjectComparator("TeamName");
        this.fragment = fragment;

    }

    /**
     * 设置排序类型
     * @param type
     */
    public void setType(String type){
        projectComparator.setType(type);
    }

    /**
     * 进行排序和数据重新设置
     */
    public void sort(){
        Collections.sort(projectList, projectComparator);
        setProjectList(projectList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_project_list, parent, false);
        ProjectViewHolder holder = new ProjectViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        /*队伍名字是否显示的设置*/
        if (position == 0 || projectList.get(position).getTeamId() != projectList.get(position - 1).getTeamId()) {
            holder.mTeamName.setVisibility(View.VISIBLE);
            holder.mTeamName.setText(projectList.get(position).getTeamName());
        } else {
            holder.mTeamName.setVisibility(View.GONE);
        }
        /*设置项目名称的显示*/
        holder.mProjectName.setText(projectList.get(position).getName());
        /*剩余的天数*/
        long leftTime = projectList.get(position).getMaxDate().getTime() - System.currentTimeMillis();
        int leftDay = (int) (leftTime / (24 * 60 * 60 * 1000));
        if (leftDay >= 30) {
            holder.mProjectName.setBackgroundColor(activity.getResources().getColor(R.color.projectUrgent3));
            holder.mMaxTime.setBackgroundColor(activity.getResources().getColor(R.color.projectUrgent3));
            holder.mProjectButton.setBackgroundColor(activity.getResources().getColor(R.color.projectUrgent3));
        } else if (leftDay >= 10) {
            holder.mProjectName.setBackgroundColor(activity.getResources().getColor(R.color.projectUrgent2));
            holder.mMaxTime.setBackgroundColor(activity.getResources().getColor(R.color.projectUrgent2));
            holder.mProjectButton.setBackgroundColor(activity.getResources().getColor(R.color.projectUrgent2));
        } else if (leftDay < 0){
            holder.mProjectName.setBackgroundColor(activity.getResources().getColor(R.color.projectOutDate));
            holder.mMaxTime.setBackgroundColor(activity.getResources().getColor(R.color.projectOutDate));
            holder.mProjectButton.setBackgroundColor(activity.getResources().getColor(R.color.projectOutDate));
        } else {
            holder.mProjectName.setBackgroundColor(activity.getResources().getColor(R.color.projectUrgent1));
            holder.mMaxTime.setBackgroundColor(activity.getResources().getColor(R.color.projectUrgent1));
            holder.mProjectButton.setBackgroundColor(activity.getResources().getColor(R.color.projectUrgent1));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        holder.mMaxTime.setText("截止日期: " + simpleDateFormat.format(projectList.get(position).getMaxDate()) + "     剩余: " + leftDay + " 天");
        holder.projectId = projectList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder {

        private TextView mTeamName;
        private TextView mProjectName;
        private TextView mMaxTime;
        private TextView mProjectButton;
        //项目ID，负责菜单操作等等
        private int projectId;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            mTeamName = itemView.findViewById(R.id.viewholder_project_list_team_name);
            mProjectName = itemView.findViewById(R.id.viewholder_project_list_project_name);
            mProjectButton = itemView.findViewById(R.id.viewholder_project_list_button);
            mMaxTime = itemView.findViewById(R.id.viewholder_project_list_max_time);

            mProjectName.setOnClickListener(v -> {
                Intent intent = new Intent(activity, ProjectHomeActivity.class);
                intent.putExtra("projectId", projectId);
                activity.startActivity(intent);
            });

            mProjectButton.setOnClickListener(v -> {
                new MaterialDialog.Builder(activity)
                        .title(R.string.projectAlertMenuTitle)
                        .items(R.array.projectAlertMenuContent)
                        .itemsCallback((dialog, itemView2, position, text) -> {
                            Intent intent = null;
                            switch (position) {
                                case 0:
                                    /* 把项目ID带入下一个Activity */
                                    intent = new Intent(activity, ProjectHomeActivity.class);
                                    intent.putExtra("projectId", projectId);
                                    activity.startActivity(intent);
                                    break;
                                case 1:
                                    /* 把项目ID带入下一个Activity */
                                    intent = new Intent(activity, ProjectAlertActivity.class);
                                    intent.putExtra("projectId", projectId);
                                    activity.startActivity(intent);
                                    break;
                                case 2:
                                    /*弹出菜单，归档项目的确认*/
                                    new MaterialDialog.Builder(activity)
                                            .content(R.string.projectCompleteAlertMenuTitle)
                                            .positiveText(R.string.Yes)
                                            .negativeText(R.string.No)
                                            .onPositive((dialog1, which) -> {
                                                /*点了确定*/
                                                if (fragment instanceof ProjectFragment){
                                                    ProjectFragment projectFragment = (ProjectFragment) ProjectAdapter.this.fragment;
                                                    projectFragment.completeProject(projectId);
                                                }
                                                else if (fragment instanceof TeamHomeProjectFragment){
                                                    TeamHomeProjectFragment teamHomeProjectFragment = (TeamHomeProjectFragment) ProjectAdapter.this.fragment;
                                                    // TODO
                                                }
                                            })
                                            .show();
                                    break;
                                case 3:
                                    /*弹出菜单，删除项目的确认*/
                                    new MaterialDialog.Builder(activity)
                                            .content(R.string.projectDeleteAlertMenuTitle)
                                            .positiveText(R.string.Yes)
                                            .negativeText(R.string.No)
                                            .onPositive((dialog1, which) -> {
                                                /*点了确定*/
                                                if (fragment instanceof ProjectFragment){
                                                    ProjectFragment projectFragment = (ProjectFragment) ProjectAdapter.this.fragment;
                                                    projectFragment.deleteProject(projectId);
                                                }
                                                else if (fragment instanceof TeamHomeProjectFragment){
                                                    TeamHomeProjectFragment teamHomeProjectFragment = (TeamHomeProjectFragment) ProjectAdapter.this.fragment;
                                                    // TODO
                                                }
                                            })
                                            .show();
                                    break;
                            }
                        })
                        .show();
            });
        }
    }
}
