package com.lqk.effecteam.project.list;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;
import com.lqk.effecteam.project.ProjectVirtualData;
import com.lqk.effecteam.project.home.ProjectHomeActivity;
import com.lqk.effecteam.team.TeamVirtualData;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.util.Iterator;
import java.util.List;

/**
 * Create By LiuQK on 2021/4/7
 * Describe:
 */
public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

    private static final String TAG = "ProjectAdapter";

    private List<Project> projectList;
    private Activity activity;

    public ProjectAdapter(List<Project> projectList, Activity activity) {
        this.projectList = projectList;
        this.activity = activity;
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
        if (position == 0 || projectList.get(position).getOwnerTeamId() != projectList.get(position - 1).getOwnerTeamId()) {
            /*TODO 待调整*/
            holder.mTeamName.setText(TeamVirtualData.teamArrayList.get(projectList.get(position).getOwnerTeamId()).getTeamName());
        } else {
            holder.mTeamName.setVisibility(View.GONE);
        }
        /*设置项目名称的显示*/
        holder.mProjectName.setText(projectList.get(position).getName());
        /*剩余的天数*/
        /* TODO 暂时不考虑逾期情况 */
        long leftTime = projectList.get(position).getMaxDate().getTime() - System.currentTimeMillis();
        int leftDay = (int) (leftTime / (24 * 60 * 60 * 1000));
        if (leftDay >= 30) {
            holder.mProjectName.setBackgroundColor(activity.getResources().getColor(R.color.projectUrgent3));
            holder.mProjectButton.setBackgroundColor(activity.getResources().getColor(R.color.projectUrgent3));
        } else if (leftDay >= 10) {
            holder.mProjectName.setBackgroundColor(activity.getResources().getColor(R.color.projectUrgent2));
            holder.mProjectButton.setBackgroundColor(activity.getResources().getColor(R.color.projectUrgent2));
        } else {
            holder.mProjectName.setBackgroundColor(activity.getResources().getColor(R.color.projectUrgent1));
            holder.mProjectButton.setBackgroundColor(activity.getResources().getColor(R.color.projectUrgent1));
        }
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
        private TextView mProjectButton;
        //项目ID，负责菜单操作等等
        private int projectId;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            mTeamName = itemView.findViewById(R.id.viewholder_project_list_team_name);
            mProjectName = itemView.findViewById(R.id.viewholder_project_list_project_name);
            mProjectButton = itemView.findViewById(R.id.viewholder_project_list_button);

            mProjectName.setOnClickListener(v -> {
                //TODO 携带项目UID数据进去
                Intent intent = new Intent(activity, ProjectHomeActivity.class);
                activity.startActivity(intent);
            });

            mProjectButton.setOnClickListener(v -> {
                new MaterialDialog.Builder(activity)
                        .title(R.string.projectAlertMenuTitle)
                        .items(R.array.projectAlertMenuContent)
                        .itemsCallback((dialog, itemView2, position, text) -> {
                            Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
                            switch (position) {
                                case 0:
                                    //TODO 携带项目UID数据进去
                                    Intent intent = new Intent(activity, ProjectHomeActivity.class);
                                    activity.startActivity(intent);
                                    break;
                                case 1:
                                    break;
                                case 2:
                                    /*弹出菜单，归档项目的确认*/
                                    new MaterialDialog.Builder(activity)
                                            .content(R.string.projectCompleteAlertMenuTitle)
                                            .positiveText(R.string.Yes)
                                            .negativeText(R.string.No)
                                            .onPositive((dialog1, which) -> {
                                                /*点了确定*/
                                                //TODO 当前是虚拟数据，归档项目
                                                Iterator<Project> iterator = ProjectVirtualData.projectList.iterator();
                                                while(iterator.hasNext()){
                                                    Project cur = iterator.next();
                                                    if(cur.getId() == projectId){
                                                        iterator.remove();
                                                    }
                                                }
                                                Log.i(TAG, "ProjectViewHolder: 归档项目，项目ID " + projectId);
                                                notifyDataSetChanged();
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
                                                //TODO 当前是虚拟数据，删除项目
                                                Iterator<Project> iterator = ProjectVirtualData.projectList.iterator();
                                                while(iterator.hasNext()){
                                                    Project cur = iterator.next();
                                                    if(cur.getId() == projectId){
                                                        iterator.remove();
                                                    }
                                                }
                                                Log.i(TAG, "ProjectViewHolder: 删除，项目ID " + projectId);
                                                notifyDataSetChanged();
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
