package com.lqk.effecteam.home.messagenotice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.data.MessageNoticeData;
import com.lqk.effecteam.common.entity.notice.SimpleNotice;
import com.lqk.effecteam.common.util.HttpUtil;
import com.lqk.effecteam.team.home.TeamHomeActivity;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Create By LiuQK on 2021/4/30
 * Describe:
 */
public class MessageNoticeAdapter extends RecyclerView.Adapter<MessageNoticeAdapter.MessageNoticeViewHolder>{

    private List<MessageNoticeData> messageNoticeDataList;
    private Activity activity;

    public MessageNoticeAdapter(List<MessageNoticeData> messageNoticeDataList, Activity activity) {
        this.messageNoticeDataList = messageNoticeDataList;
        this.activity = activity;
    }

    public List<MessageNoticeData> getMessageNoticeDataList() {
        return messageNoticeDataList;
    }

    public void setMessageNoticeDataList(List<MessageNoticeData> messageNoticeDataList) {
        this.messageNoticeDataList = messageNoticeDataList;
    }

    @NonNull
    @Override
    public MessageNoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_message_notice, parent, false);
        MessageNoticeViewHolder holder = new MessageNoticeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageNoticeViewHolder holder, int position) {
        String teamName = messageNoticeDataList.get(position).getTeamName();
        int teamNameLength = teamName.length();
        if (teamNameLength > 2){
            holder.mNoticeTeamhead.setText(teamName.substring(0, 2));
        }
        else {
            holder.mNoticeTeamhead.setText(teamName);
        }
        holder.mNoticeTeamname.setText(teamName);
        holder.noticeId = messageNoticeDataList.get(position).getId();
        holder.position = position;
        if (messageNoticeDataList.get(position).getStatus() == SimpleNotice.DOING){
            holder.mNoticeTeaminfo.setText(messageNoticeDataList.get(position).getContent());
            holder.mNoticeTeaminfo.setTextColor(Color.parseColor("#FF0000"));
        }
        else {
            holder.mNoticeTeaminfo.setText("");
            holder.mNoticeTeaminfo.setTextColor(Color.parseColor("#000000"));
        }
    }

    @Override
    public int getItemCount() {
        return messageNoticeDataList.size();
    }

    class MessageNoticeViewHolder extends RecyclerView.ViewHolder{

        private TextView mNoticeTeamhead;
        private TextView mNoticeTeamname;
        private TextView mNoticeTeaminfo;
        private RelativeLayout mNoticeRelative;
        private int noticeId;
        private int position;



        public MessageNoticeViewHolder(@NonNull View itemView) {
            super(itemView);
            mNoticeTeamhead = itemView.findViewById(R.id.viewholder_message_notice_teamhead);
            mNoticeTeamname = itemView.findViewById(R.id.viewholder_message_notice_teamname);
            mNoticeTeaminfo = itemView.findViewById(R.id.viewholder_message_notice_teaminfo);
            mNoticeRelative = itemView.findViewById(R.id.viewholder_message_notice_relative);

            mNoticeRelative.setOnClickListener(v -> {
                int userId = activity.getSharedPreferences(HttpUtil.Shared_File_Name, Context.MODE_PRIVATE).getInt("userId", 0);

                String url = "readMessageNotice?userId="+ userId + "&noticeId=" + noticeId;
                HttpUtil.connectInternet(url, null, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });

                Intent intent = new Intent(v.getContext(), TeamHomeActivity.class);
                intent.putExtra("teamId", messageNoticeDataList.get(position).getTeamId());
                intent.putExtra("teamName", messageNoticeDataList.get(position).getTeamName());
                activity.startActivity(intent);
            });
        }
    }
}
