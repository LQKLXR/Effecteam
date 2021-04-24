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
import com.lqk.effecteam.team.TeamVirtualData;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Create By LiuQK on 2021/4/16
 * Describe:
 */
public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.DynamicViewHolder>{

    private List<Dynamic> dynamicList;
    private Activity activity;

    public DynamicAdapter(List<Dynamic> dynamicList, Activity activity) {
        this.dynamicList = dynamicList;
        this.activity = activity;
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
        //stringBuffer.append("<font color='").append(colorUser).append("'>").append(TeamVirtualData.userArrayList.get(dynamicList.get(position).getUserId()).getUserName()).append("</font>");
        stringBuffer.append("  <font color='").append(colorAction).append("'>").append(dynamicList.get(position).getAction()).append("</font>");
        stringBuffer.append("  <font color='").append(colorObject).append("'>").append(dynamicList.get(position).getObject()).append("</font>");
        String htmlString = stringBuffer.toString();
        holder.mContent.setText(Html.fromHtml(htmlString));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        holder.mDate.setText(simpleDateFormat.format(dynamicList.get(position).getDate()));
    }

    @Override
    public int getItemCount() {
        return dynamicList.size();
    }

    public void setDynamicList(List<Dynamic> dynamicList) {
        this.dynamicList = dynamicList;
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
