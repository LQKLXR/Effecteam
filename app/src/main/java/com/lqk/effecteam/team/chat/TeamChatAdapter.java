package com.lqk.effecteam.team.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;
import com.lqk.effecteam.team.TeamVirtualData;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Create By LiuQK on 2021/4/4
 * Describe: 聊天内容的 Adapter
 */
public class TeamChatAdapter extends RecyclerView.Adapter<TeamChatAdapter.TeamChatViewHolder> {

    private List<Message> mMessageList;

    public TeamChatAdapter(List<Message> mMessageList) {
        this.mMessageList = mMessageList;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        //对日期和时间的处理开始
        if (position == 0){
            holder.mDateText.setText(dateFormat.format(mMessageList.get(position).getDate()));
            holder.mTimeText.setText(timeFormat.format(mMessageList.get(position).getDate()));
        }
        else{
            //如果日期发生了变化，就要重新显示日期
            String curDate = dateFormat.format(mMessageList.get(position).getDate());
            String pasDate = dateFormat.format(mMessageList.get(position - 1).getDate());
            String curTime = timeFormat.format(mMessageList.get(position).getDate());
            String pasTime = dateFormat.format(mMessageList.get(position - 1).getDate());
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

        // TODO 当前用户ID虚拟
        if(mMessageList.get(position).getSenderId() == 0){
            holder.mReceiveLinear.setVisibility(View.GONE);
            holder.mSendContentText.setText(mMessageList.get(position).getContent());
            //String sendUserName = TeamVirtualData.userArrayList.get(mMessageList.get(position).getSenderId()).getUserName();
            //holder.mSendHeadText.setText(sendUserName.substring(sendUserName.length() - 2, sendUserName.length()));
            //holder.mSendNameText.setText(sendUserName);
        }
        else {
            holder.mSendLinear.setVisibility(View.GONE);
            holder.mReceiveContentText.setText(mMessageList.get(position).getContent());
            //String sendUserName = TeamVirtualData.userArrayList.get(mMessageList.get(position).getSenderId()).getUserName();
            //holder.mReceiveHeadText.setText(sendUserName.substring(sendUserName.length() - 2, sendUserName.length()));
            //holder.mReceiveNameText.setText(sendUserName);
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
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
