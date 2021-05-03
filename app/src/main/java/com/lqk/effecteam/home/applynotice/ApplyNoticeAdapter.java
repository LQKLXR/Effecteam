package com.lqk.effecteam.home.applynotice;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.entity.notice.ApplyNotice;
import com.lqk.effecteam.common.entity.notice.SimpleNotice;
import com.lqk.effecteam.common.util.HttpUtil;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Create By LiuQK on 2021/4/29
 * Describe: 申请通知的显示 Adapter
 */
public class ApplyNoticeAdapter extends RecyclerView.Adapter<ApplyNoticeAdapter.ApplyNoticeViewHolder> {

    private List<ApplyNotice> applyNoticeList;
    private Activity activity;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(activity, "网络连接失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(activity, "同意加入", Toast.LENGTH_SHORT).show();
                    int position = (int) msg.obj;
                    applyNoticeList.get(position).setStatus(ApplyNotice.ACCEPTED);
                    notifyDataSetChanged();
                    break;
                case 3:
                    Toast.makeText(activity, "拒绝加入", Toast.LENGTH_SHORT).show();
                    int position1 = (int) msg.obj;
                    applyNoticeList.get(position1).setStatus(ApplyNotice.REFUSED);
                    notifyDataSetChanged();
                    break;
            }
        }
    };

    public ApplyNoticeAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setApplyNoticeList(List<ApplyNotice> applyNoticeList) {
        this.applyNoticeList = applyNoticeList;
    }

    @NonNull
    @Override
    public ApplyNoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_apply_notice, parent, false);
        ApplyNoticeViewHolder holder = new ApplyNoticeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ApplyNoticeViewHolder holder, int position) {
        holder.mNoticeContent.setText(applyNoticeList.get(position).getContent());
        if (applyNoticeList.get(position).getStatus() != SimpleNotice.DOING){
            holder.mAcceptButton.setVisibility(View.GONE);
            holder.mRefuseButton.setVisibility(View.GONE);
            holder.mNoticeStatus.setVisibility(View.VISIBLE);
            if (applyNoticeList.get(position).getStatus() == ApplyNotice.ACCEPTED) {
                holder.mNoticeStatus.setText("已同意");
            }
            else {
                holder.mNoticeStatus.setText("已拒绝");
            }
        }
        holder.mNoticeDate.setText(sdf.format(applyNoticeList.get(position).getDate()));
        holder.noticeId = applyNoticeList.get(position).getId();
        holder.curListId = position;
    }

    @Override
    public int getItemCount() {
        return applyNoticeList.size();
    }

    class ApplyNoticeViewHolder extends RecyclerView.ViewHolder {

        private TextView mNoticeContent;
        private TextView mNoticeDate;
        private TextView mNoticeStatus;
        private SuperButton mAcceptButton;
        private SuperButton mRefuseButton;
        private int noticeId;
        private int curListId;

        public ApplyNoticeViewHolder(@NonNull View itemView) {
            super(itemView);
            mNoticeContent = itemView.findViewById(R.id.viewholder_apply_notice_content);
            mAcceptButton = itemView.findViewById(R.id.viewholder_apply_notice_accpet);
            mRefuseButton = itemView.findViewById(R.id.viewholder_apply_notice_refuse);
            mNoticeDate = itemView.findViewById(R.id.viewholder_apply_notice_date);
            mNoticeStatus = itemView.findViewById(R.id.viewholder_apply_notice_status);

            mAcceptButton.setOnClickListener(v -> {
                String url = "acceptApplyTeam?senderId=" + applyNoticeList.get(curListId).getSenderId() + "&applyNoticeId=" + noticeId
                        +"&teamId=" + applyNoticeList.get(curListId).getTeamId();
                HttpUtil.connectInternet(url, null, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Message message = Message.obtain();
                        message.what = 1;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String body = new String(response.body().bytes());
                        if (body.equals("success")){
                            Message message = Message.obtain();
                            message.what = 2;
                            message.obj = curListId;
                            handler.sendMessage(message);
                        }
                    }
                });
            });
            mRefuseButton.setOnClickListener(v -> {
                String url = "refuseApplyTeam?senderId=" + applyNoticeList.get(curListId).getSenderId() + "&applyNoticeId=" + noticeId
                        +"&teamId=" + applyNoticeList.get(curListId).getTeamId();
                HttpUtil.connectInternet(url, null, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Message message = Message.obtain();
                        message.what = 1;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String body = new String(response.body().bytes());
                        if (body.equals("success")){
                            Message message = Message.obtain();
                            message.what = 3;
                            message.obj = curListId;
                            handler.sendMessage(message);
                        }
                    }
                });
            });
        }
    }
}
