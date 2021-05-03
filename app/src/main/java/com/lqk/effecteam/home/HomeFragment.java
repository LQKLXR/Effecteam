package com.lqk.effecteam.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lqk.effecteam.R;
import com.lqk.effecteam.common.data.MessageNoticeData;
import com.lqk.effecteam.common.entity.notice.ApplyNotice;
import com.lqk.effecteam.common.entity.notice.MessageNotice;
import com.lqk.effecteam.common.entity.notice.SimpleNotice;
import com.lqk.effecteam.common.util.HttpUtil;
import com.lqk.effecteam.home.applynotice.ApplyNoticeActivity;
import com.lqk.effecteam.home.messagenotice.MessageNoticeAdapter;
import com.lqk.effecteam.home.simplenotice.SimpleNoticeActivity;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.textview.badge.Badge;
import com.xuexiang.xui.widget.textview.badge.BadgeView;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private TitleBar mHomeTitleBar;

    /*团队申请管理*/
    private SuperTextView mApplyNoticeAssistant;
    private Badge mApplyNoticeBadge;
    private List<ApplyNotice> applyNoticeList;
    private int applyNoticeCount;

    /*通知助手*/
    private SuperTextView mSimpleNoticeAssistant;
    private Badge mSimpleNoticeBadge;
    private List<SimpleNotice> simpleNoticeList;
    private int simpleNoticeCount;

    /*消息列表*/
    private RecyclerView mMessageNoticeRecycler;
    private MessageNoticeAdapter mMessageNoticeAdapter;
    private List<MessageNoticeData> messageNoticeDataList;

    // 定时任务
    private TimerTask timerTask;
    private Timer timer;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    // Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2: // 针对团队申请的
                    List<ApplyNotice> applyNotices = (List<ApplyNotice>) msg.obj;
                    applyNoticeCount = 0;
                    for (ApplyNotice applyNotice : applyNotices) {
                        if (applyNotice.getStatus() == SimpleNotice.DOING) {
                            applyNoticeCount++;
                        }
                    }
                    applyNoticeList = applyNotices;
                    mApplyNoticeBadge = mApplyNoticeBadge.setBadgeNumber(applyNoticeCount);
                    break;
                case 3: //普通的通知
                    List<SimpleNotice> simpleNotices = (List<SimpleNotice>) msg.obj;
                    simpleNoticeCount = 0;
                    for (SimpleNotice simpleNotice : simpleNotices) {
                        if (simpleNotice.getStatus() == SimpleNotice.DOING) {
                            simpleNoticeCount++;
                        }
                    }
                    simpleNoticeList = simpleNotices;
                    mSimpleNoticeBadge = mSimpleNoticeBadge.setBadgeNumber(simpleNoticeCount);
                    break;
                case 4: // 消息通知
                    List<MessageNoticeData> messageNoticeDatas = (List<MessageNoticeData>) msg.obj;
                    messageNoticeDataList = messageNoticeDatas;
                    mMessageNoticeAdapter.setMessageNoticeDataList(messageNoticeDataList);
                    mMessageNoticeAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mHomeTitleBar = view.findViewById(R.id.home_title_bar);
        mApplyNoticeAssistant = view.findViewById(R.id.home_team_assistant);
        mHomeTitleBar.disableLeftView();
        mSimpleNoticeAssistant = view.findViewById(R.id.home_notice_assistant);

        mApplyNoticeAssistant.setRightString("          ");
        mApplyNoticeBadge = new BadgeView(getContext())
                .bindTarget(mApplyNoticeAssistant.getRightTextView())
                .setBadgeGravity(Gravity.END | Gravity.CENTER)
                .setBadgePadding(2, true)
                .setBadgeTextSize(10, true);
        //setBadgeNumber(3)
        mSimpleNoticeAssistant.setRightString("          ");
        mSimpleNoticeBadge = new BadgeView(getContext())
                .bindTarget(mSimpleNoticeAssistant.getRightTextView())
                .setBadgeGravity(Gravity.END | Gravity.CENTER)
                .setBadgePadding(2, true)
                .setBadgeTextSize(10, true);
        applyNoticeList = new ArrayList<>();
        simpleNoticeList = new ArrayList<>();
        mApplyNoticeAssistant.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ApplyNoticeActivity.class);
            Gson gson = new Gson();
            Collections.reverse(applyNoticeList);
            String json = gson.toJson(applyNoticeList);
            intent.putExtra("applyNoticeListJson", json);
            startActivity(intent);
        });

        mSimpleNoticeAssistant.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SimpleNoticeActivity.class);
            Gson gson = new Gson();
            Collections.reverse(simpleNoticeList);
            String json = gson.toJson(simpleNoticeList);
            intent.putExtra("simpleNoticeListJson", json);
            startActivity(intent);
        });

        mMessageNoticeRecycler = view.findViewById(R.id.home_recyclerview);
        mMessageNoticeAdapter = new MessageNoticeAdapter(new ArrayList<>(), getActivity());
        mMessageNoticeRecycler.setAdapter(mMessageNoticeAdapter);
        mMessageNoticeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMessageNoticeRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), 1));

        // 配置循环线程
        SharedPreferences sp = getActivity().getSharedPreferences(HttpUtil.Shared_File_Name, Context.MODE_PRIVATE);
        int userId = sp.getInt("userId", 0);


        timerTask = new TimerTask() {
            @Override
            public void run() {
                for (int i = 2; i <= 4; i++) {
                    String url = null;
                    if (i == 2) {
                        url = "getApplyNotice?userId=" + userId;
                    } else if (i == 3) {
                        url = "getSimplyNotice?userId=" + userId;
                    } else if (i == 4) {
                        url = "getMessageNotice?userId=" + userId;
                    }
                    int finalI = i;
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
                            Gson gson = new Gson();
                            Message message = Message.obtain();
                            if (finalI == 2) {
                                List<ApplyNotice> applyNotices = gson.fromJson(body, new TypeToken<List<ApplyNotice>>() {
                                }.getType());
                                message.obj = applyNotices;
                            } else if (finalI == 3) {
                                List<SimpleNotice> simpleNotices = gson.fromJson(body, new TypeToken<List<SimpleNotice>>() {
                                }.getType());
                                message.obj = simpleNotices;
                            } else if (finalI == 4) {
                                List<MessageNoticeData> messageNoticeDatas = gson.fromJson(body, new TypeToken<List<MessageNoticeData>>() {
                                }.getType());
                                message.obj = messageNoticeDatas;
                            }
                            message.what = finalI;
                            handler.sendMessage(message);
                        }
                    });
                }
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0L, 5000L);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        timer.cancel();
    }
}


