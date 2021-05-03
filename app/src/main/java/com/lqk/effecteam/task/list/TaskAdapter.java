package com.lqk.effecteam.task.list;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.comparator.TaskComparator;
import com.lqk.effecteam.common.entity.Task;
import com.lqk.effecteam.task.create.TaskCreateActivity;
import com.xuexiang.xui.widget.button.shadowbutton.ShadowButton;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

/**
 * Create By LiuQK on 2021/4/14
 * Describe: 任务列表的适配器
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private static final String TAG = "TaskAdapter";

    private Activity activity;
    private Fragment fragment;
    private List<Task> taskList;

    /*任务排序器*/
    private TaskComparator taskComparator;


    public TaskAdapter(Activity activity, Fragment fragment, List<Task> taskList) {
        this.activity = activity;
        this.fragment = fragment;
        this.taskList = taskList;
        taskComparator = new TaskComparator("Priority");
    }

    public void sortType(String type) {
        taskComparator.setType(type);
    }

    public void sort() {
        Collections.sort(taskList, taskComparator);
        notifyDataSetChanged();
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_task_list, parent, false);
        TaskViewHolder taskViewHolder = new TaskViewHolder(view);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        holder.mTaskName.setText(taskList.get(position).getName());
        holder.mTaskId = taskList.get(position).getId();
        //任务列表的详细信息
        StringBuffer stringBuffer = new StringBuffer("优先级: ");
        if (taskList.get(position).getPriority() == 1) {
            stringBuffer.append("高  ");
        } else if (taskList.get(position).getPriority() == 2) {
            stringBuffer.append("中  ");
        } else if (taskList.get(position).getPriority() == 3) {
            stringBuffer.append("低  ");
        }
        stringBuffer.append("截止时间: ");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        stringBuffer.append(simpleDateFormat.format(taskList.get(position).getMaxDate()));
        holder.mTaskInfo.setText(stringBuffer.toString());
        holder.mTaskId = taskList.get(position).getId();
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


    class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView mTaskName;
        private TextView mTaskInfo;
        private ShadowButton mSuperButton;

        private int mTaskId;
        private int position;


        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTaskName = itemView.findViewById(R.id.viewholder_task_list_name);
            mTaskInfo = itemView.findViewById(R.id.viewholder_task_list_info);
            mSuperButton = itemView.findViewById(R.id.viewholder_task_list_button);

            mSuperButton.setOnClickListener(v -> {
                new MaterialDialog.Builder(activity)
                        .title(R.string.taskAlertMenuTitle)
                        .items(R.array.taskAlertMenuContent)
                        .itemsCallback((dialog, itemView1, position, text) -> {
                            switch (position) {
                                case 0:
                                    break;
                                case 1:
                                    Intent intent = new Intent(activity, TaskCreateActivity.class);
                                    intent.putExtra("type", TaskCreateActivity.ALTER);
                                    intent.putExtra("taskName", taskList.get(this.position).getName());
                                    intent.putExtra("taskContent", taskList.get(this.position).getContent());
                                    intent.putExtra("taskPriority", taskList.get(this.position).getPriority());
                                    intent.putExtra("projectId", taskList.get(this.position).getProjectId());
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    String maxDateString = sdf.format(taskList.get(this.position).getMaxDate());
                                    intent.putExtra("maxDate", maxDateString);
                                    intent.putExtra("taskId", taskList.get(this.position).getId());
                                    activity.startActivity(intent);
                                    break;
                                case 2:
                                    /*弹出菜单，完成项目的确认*/
                                    new MaterialDialog.Builder(activity)
                                            .content(R.string.taskCompleteAlertMenuTitle)
                                            .positiveText(R.string.Yes)
                                            .negativeText(R.string.No)
                                            .onPositive((dialog1, which) -> {
                                                /*点了确定*/
                                                TaskFragment taskFragment = (TaskFragment) TaskAdapter.this.fragment;
                                                taskFragment.operateTask(mTaskId, TaskFragment.TASK_COMPLETE);
                                                taskFragment.loadTaskList();
                                                Log.i(TAG, "正在完成任务: ");
                                            })
                                            .show();
                                    break;
                                case 3:
                                    /*弹出菜单，删除项目的确认*/
                                    new MaterialDialog.Builder(activity)
                                            .content(R.string.taskDeleteAlertMenuTitle)
                                            .positiveText(R.string.Yes)
                                            .negativeText(R.string.No)
                                            .onPositive((dialog1, which) -> {
                                                /*点了确定*/
                                                TaskFragment taskFragment = (TaskFragment) TaskAdapter.this.fragment;
                                                taskFragment.operateTask(mTaskId, TaskFragment.TASK_DELETE);
                                                taskFragment.loadTaskList();
                                                notifyDataSetChanged();
                                                Log.i(TAG, "正在删除任务: ");
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
