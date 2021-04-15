package com.lqk.effecteam.task.list;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;
import com.lqk.effecteam.project.ProjectVirtualData;
import com.lqk.effecteam.task.Task;
import com.xuexiang.xui.widget.button.shadowbutton.ShadowButton;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.util.Iterator;

/**
 * Create By LiuQK on 2021/4/14
 * Describe: 任务列表的适配器
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private static final String TAG = "TaskAdapter";

    private Activity activity;

    public TaskAdapter(Activity activity) {
        this.activity = activity;
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
        //TODO
        holder.mTextView.setText(ProjectVirtualData.taskList.get(position).getName());
        holder.mTaskId = ProjectVirtualData.taskList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        //TODO
        return ProjectVirtualData.taskList.size();
    }


    class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private ShadowButton mSuperButton;

        private int mTaskId;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mTextView = itemView.findViewById(R.id.viewholder_task_list_name);
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
                                    break;
                                case 2:
                                    /*弹出菜单，完成项目的确认*/
                                    new MaterialDialog.Builder(activity)
                                            .content(R.string.taskCompleteAlertMenuTitle)
                                            .positiveText(R.string.Yes)
                                            .negativeText(R.string.No)
                                            .onPositive((dialog1, which) -> {
                                                /*点了确定*/
                                                // TODO
                                                Iterator<Task> iterator = ProjectVirtualData.taskList.iterator();
                                                while (iterator.hasNext()) {
                                                    Task cur = iterator.next();
                                                    if(cur.getId() == mTaskId){
                                                        iterator.remove();
                                                        break;
                                                    }
                                                }
                                                notifyDataSetChanged();
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
                                                // TODO
                                                Iterator<Task> iterator = ProjectVirtualData.taskList.iterator();
                                                while (iterator.hasNext()) {
                                                    Task cur = iterator.next();
                                                    if(cur.getId() == mTaskId){
                                                        iterator.remove();
                                                        break;
                                                    }
                                                }
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
