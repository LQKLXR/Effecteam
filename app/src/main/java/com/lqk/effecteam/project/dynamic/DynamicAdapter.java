package com.lqk.effecteam.project.dynamic;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.data.DynamicData;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Create By LiuQK on 2021/4/16
 * Describe:
 */
public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.DynamicViewHolder>{

    private List<DynamicData> dynamicDataList;
    private Activity activity;
    private int type;

    public DynamicAdapter(List<DynamicData> dynamicDataList, Activity activity) {
        this.dynamicDataList = dynamicDataList;
        this.activity = activity;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setDynamicDataList(List<DynamicData> dynamicDataList) {
        this.dynamicDataList = dynamicDataList;
    }

    @NonNull
    @Override
    public DynamicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_dynamic, parent, false);
        DynamicViewHolder dynamicViewHolder = new DynamicViewHolder(view);
        return dynamicViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DynamicViewHolder holder, int position) {
        String colorUser = activity.getResources().getString(R.string.dynamicUser);
        String colorAction = activity.getResources().getString(R.string.dynamicAction);
        String colorObject = activity.getResources().getString(R.string.dynamicObject);

        StringBuffer stringBuffer = new StringBuffer();
        if (type == DynamicsFragment.MINE){
            stringBuffer.append("<font color='").append(colorUser).append("'>").append("我").append("</font>");
        }
        else if(type == DynamicsFragment.PROJECT){
            stringBuffer.append("<font color='").append(colorUser).append("'>").append(dynamicDataList.get(position).getUserName()).append("</font>");
        }

        if (type == DynamicsFragment.MINE){
            stringBuffer.append("  <font color='").append(colorAction).append("'>").append(" 在项目 ").append("</font>");
            stringBuffer.append("  <font color='").append(colorObject).append("'>[")
                    .append(dynamicDataList.get(position).getProjectName()).append("]</font>");
            stringBuffer.append("  <font color='").append(colorAction).append("'>").append("中 ").append("</font>");
        }
        stringBuffer.append("  <font color='").append(colorAction).append("'>").append(dynamicDataList.get(position).getAction()).append("</font>");
        stringBuffer.append("  <font color='").append(colorObject).append("'>").append(dynamicDataList.get(position).getObject()).append("</font>");
        String htmlString = stringBuffer.toString();
        holder.mContent.setText(Html.fromHtml(htmlString));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        holder.mDate.setText(simpleDateFormat.format(dynamicDataList.get(position).getDate()));
    }

    @Override
    public int getItemCount() {
        return dynamicDataList.size();
    }




    class DynamicViewHolder extends RecyclerView.ViewHolder{

        private TextView mDate;
        private TextView mContent;

        public DynamicViewHolder(@NonNull View itemView) {
            super(itemView);
            mDate = itemView.findViewById(R.id.viewholder_dynamic_date);
            mContent = itemView.findViewById(R.id.viewholder_dynamic_content);
        }
    }
}
