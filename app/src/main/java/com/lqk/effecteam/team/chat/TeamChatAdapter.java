package com.lqk.effecteam.team.chat;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;

/**
 * Create By LiuQK on 2021/4/4
 * Describe: 聊天内容的 Adapter
 */
public class TeamChatAdapter extends RecyclerView.Adapter<TeamChatAdapter.TeamCharViewHolder> {


    @NonNull
    @Override
    public TeamCharViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamCharViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class TeamCharViewHolder extends RecyclerView.ViewHolder{

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


        public TeamCharViewHolder(@NonNull View itemView) {
            super(itemView);

            mDateText = itemView.findViewById(R.id.viewholder_team_chat_date);
            mTimeText = itemView.findViewById(R.id.viewholder_team_chat_time);
            mReceiveLinear = itemView.findViewById(R.id.viewholder_team_chat_linear_receive);
            mReceiveHeadText = itemView.findViewById(R.id.viewholder_team_chat_receive_head);
            mReceiveNameText = itemView.findViewById(R.id.viewholder_team_chat_receive_name);
            mReceiveContentText = itemView.findViewById(R.id.viewholder_team_chat_receive_content);
            mSendLinear = itemView.findViewById(R.id.viewholder_team_chat_linear_send);
            mSendHeadText = itemView.findViewById(R.id.viewholder_team_chat_send_name);
            mSendNameText = itemView.findViewById(R.id.viewholder_team_chat_send_content);
            mSendContentText = itemView.findViewById(R.id.viewholder_team_chat_send_head);

        }
    }
}
