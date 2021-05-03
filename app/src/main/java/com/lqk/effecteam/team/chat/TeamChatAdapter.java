package com.lqk.effecteam.team.chat;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.data.ChatMessageData;
import com.lqk.effecteam.common.entity.ChatMessage;
import com.lqk.effecteam.common.util.HttpUtil;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Create By LiuQK on 2021/4/4
 * Describe: 聊天内容的 Adapter
 */
public class TeamChatAdapter extends RecyclerView.Adapter<TeamChatAdapter.TeamChatViewHolder> {

    private List<ChatMessageData> chatMessageList;
    private Activity activity;

    public TeamChatAdapter(List<ChatMessageData> chatMessageList, Activity activity) {
        this.chatMessageList = chatMessageList;
        this.activity = activity;
    }

    public void setChatMessageList(List<ChatMessageData> chatMessageList) {
        this.chatMessageList = chatMessageList;
    }

    @NonNull
    @Override
    public TeamChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_team_chat, parent, false);
        TeamChatViewHolder teamChatViewHolder = new TeamChatViewHolder(view);
        return teamChatViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamChatViewHolder holder, int position) {
        SharedPreferences sp = activity.getSharedPreferences(HttpUtil.Shared_File_Name, Context.MODE_PRIVATE);
        int userId = sp.getInt("userId", 0);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        //对日期和时间的处理开始
        if (position == 0){
            holder.mDateText.setText(dateFormat.format(chatMessageList.get(position).getDate()));
            holder.mTimeText.setText(timeFormat.format(chatMessageList.get(position).getDate()));
        }
        else{
            //如果日期发生了变化，就要重新显示日期
            String curDate = dateFormat.format(chatMessageList.get(position).getDate());
            String pasDate = dateFormat.format(chatMessageList.get(position - 1).getDate());
            String curTime = timeFormat.format(chatMessageList.get(position).getDate());
            String pasTime = dateFormat.format(chatMessageList.get(position - 1).getDate());
            if(curDate.equals(pasDate)){
                holder.mDateText.setVisibility(View.GONE);
            }
            else {
                holder.mDateText.setText(curDate);
            }

            if(curTime.equals(pasTime)){
                holder.mTimeText.setVisibility(View.GONE);
            }
            else {
                holder.mTimeText.setText(curTime);
            }
        }
        //对日期和时间的处理结束


        if(chatMessageList.get(position).getSenderId() == userId){
            holder.mReceiveLinear.setVisibility(View.GONE);
            holder.mSendContentText.setText(chatMessageList.get(position).getContent());
            String sendUserName = chatMessageList.get(position).getSenderName();
            holder.mSendHeadText.setText(sendUserName.substring(sendUserName.length() - 2, sendUserName.length()));
            holder.mSendNameText.setText(sendUserName);
        }
        else {
            holder.mSendLinear.setVisibility(View.GONE);
            holder.mReceiveContentText.setText(chatMessageList.get(position).getContent());
            String sendUserName =chatMessageList.get(position).getSenderName();
            holder.mReceiveHeadText.setText(sendUserName.substring(sendUserName.length() - 2, sendUserName.length()));
            holder.mReceiveNameText.setText(sendUserName);
        }
    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    class TeamChatViewHolder extends RecyclerView.ViewHolder{

        private TextView mDateText;
        private TextView mTimeText;
        /*接收的气泡信息*/
        private LinearLayout mReceiveLinear;
        private TextView mReceiveHeadText;
        private TextView mReceiveNameText;
        private TextView mReceiveContentText;
        /*发送的气泡信息*/
        private LinearLayout mSendLinear;
        private TextView mSendHeadText;
        private TextView mSendNameText;
        private TextView mSendContentText;


        public TeamChatViewHolder(@NonNull View itemView) {
            super(itemView);

            mDateText = itemView.findViewById(R.id.viewholder_team_chat_date);
            mTimeText = itemView.findViewById(R.id.viewholder_team_chat_time);
            mReceiveLinear = itemView.findViewById(R.id.viewholder_team_chat_linear_receive);
            mReceiveHeadText = itemView.findViewById(R.id.viewholder_team_chat_receive_head);
            mReceiveNameText = itemView.findViewById(R.id.viewholder_team_chat_receive_name);
            mReceiveContentText = itemView.findViewById(R.id.viewholder_team_chat_receive_content);
            mSendLinear = itemView.findViewById(R.id.viewholder_team_chat_linear_send);
            mSendHeadText = itemView.findViewById(R.id.viewholder_team_chat_send_head);
            mSendNameText = itemView.findViewById(R.id.viewholder_team_chat_send_name);
            mSendContentText = itemView.findViewById(R.id.viewholder_team_chat_send_content);

        }
    }

}
