package com.lqk.effecteam.home.simplenotice;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.entity.notice.SimpleNotice;

import java.util.List;

/**
 * Create By LiuQK on 2021/4/29
 * Describe:
 */
public class SimpleNoticeAdapter extends RecyclerView.Adapter<SimpleNoticeAdapter.SimpleNoticeViewHolder>{

    private List<SimpleNotice> simpleNoticeList;

    public SimpleNoticeAdapter(List<SimpleNotice> simpleNoticeList) {
        this.simpleNoticeList = simpleNoticeList;
    }

    public void setSimpleNoticeList(List<SimpleNotice> simpleNoticeList) {
        this.simpleNoticeList = simpleNoticeList;
    }

    @NonNull
    @Override
    public SimpleNoticeAdapter.SimpleNoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_simple_notice, parent, false);
        SimpleNoticeViewHolder holder = new SimpleNoticeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleNoticeAdapter.SimpleNoticeViewHolder holder, int position) {
        holder.mSimpleNoticeContent.setText(simpleNoticeList.get(position).getContent());
        holder.position = position;
        holder.simpleNoticeId = simpleNoticeList.get(position).getId();
        if (simpleNoticeList.get(position).getStatus() == SimpleNotice.DOING){
            holder.mSimpleNoticeStatus.setText("未读");
            holder.mSimpleNoticeStatus.setTextColor(Color.parseColor("#FF0000"));
        }
        else {
            holder.mSimpleNoticeStatus.setText("已读");
            holder.mSimpleNoticeStatus.setTextColor(Color.parseColor("#000000"));
        }
    }

    @Override
    public int getItemCount() {
        return simpleNoticeList.size();
    }



    class SimpleNoticeViewHolder extends RecyclerView.ViewHolder {

        private TextView mSimpleNoticeContent;
        private TextView mSimpleNoticeStatus;
        private int simpleNoticeId;
        private int position;

        public SimpleNoticeViewHolder(@NonNull View itemView) {
            super(itemView);
            mSimpleNoticeContent = itemView.findViewById(R.id.viewholder_simple_notice_content);
            mSimpleNoticeStatus = itemView.findViewById(R.id.viewholder_simple_notice_status);
        }
    }
}
