package com.lqk.effecteam.team.chat;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lqk.effecteam.R;
import com.lqk.effecteam.common.data.ChatMessageData;
import com.lqk.effecteam.common.entity.ChatMessage;
import com.lqk.effecteam.common.util.HttpUtil;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Create By LiuQK on 2021/4/8
 * Describe: 聊天界面的 Fragment
 */
public class TeamChatFragment extends Fragment {
    /* 消息的循环列表 */
    private RecyclerView mRecyclerView;
    /* 列表的Adapter */
    private TeamChatAdapter mTeamChatAdapter;
    /* 消息的编辑框 */
    private EditText mEditText;
    /* 消息的发送按钮 */
    private SuperButton mSendButton;
    /* 消息数据 */
    private List<ChatMessageData> chatMessageList = new ArrayList<>();

    private Timer timer;
    private TimerTask timerTask;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    break;
                case 2:
                    List<ChatMessageData> chatMessages = (List<ChatMessageData>) msg.obj;
                    if (chatMessages.size() > chatMessageList.size()) {
                        chatMessageList = chatMessages;
                        mTeamChatAdapter.setChatMessageList(chatMessageList);
                        mTeamChatAdapter.notifyDataSetChanged();
                        mRecyclerView.smoothScrollToPosition(chatMessageList.size() - 1);
                    }
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team_chat, container, false);
        initView(view);
        return view;
    }

    /**
     *
     * @param view
     */
    public void initView(View view){
        mRecyclerView = view.findViewById(R.id.team_chat_recyclerview);
        mEditText = view.findViewById(R.id.team_chat_edit_text);
        mSendButton = view.findViewById(R.id.team_chat_send_button);

        mTeamChatAdapter = new TeamChatAdapter(new ArrayList<>(), getActivity());
        mRecyclerView.setAdapter(mTeamChatAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        int teamId = getActivity().getIntent().getIntExtra("teamId", 0);
        addListener(view);

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {

                String url = "getTeamMessage?teamId=" + teamId;
                HttpUtil.connectInternet(url, null, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String body = new String(response.body().bytes());
                        Gson gson = new Gson();
                        List<ChatMessageData> chatMessages = gson.fromJson(body, new TypeToken<List<ChatMessageData>>(){}.getType());
                        Message message = Message.obtain();
                        message.what = 2;
                        message.obj = chatMessages;
                        handler.sendMessage(message);
                    }
                });
            }
        };

        timer.schedule(timerTask, 0, 5000L);
    }

    @Override
    public void onPause() {
        super.onPause();
        int teamId = getActivity().getIntent().getIntExtra("teamId", 0);
        String url = "getTeamMessage?teamId=" + teamId;
        HttpUtil.connectInternet(url, null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = new String(response.body().bytes());
                Gson gson = new Gson();
                List<ChatMessageData> chatMessages = gson.fromJson(body, new TypeToken<List<ChatMessageData>>(){}.getType());
                Message message = Message.obtain();
                message.what = 2;
                message.obj = chatMessages;
                handler.sendMessage(message);
            }
        });
    }

    private void addListener(View view) {
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0){
                    mSendButton.setClickable(true);
                }
                else {
                    mSendButton.setClickable(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        String actualName = getActivity().getSharedPreferences(HttpUtil.Shared_File_Name, Context.MODE_PRIVATE).getString("actualName", "");
        int teamId = getActivity().getIntent().getIntExtra("teamId", 0);
        int senderId = getActivity().getSharedPreferences(HttpUtil.Shared_File_Name, Context.MODE_PRIVATE).getInt("userId", 0);
        mSendButton.setOnClickListener(v -> {
            chatMessageList.add(new ChatMessageData(0, mEditText.getText().toString(), teamId, senderId, actualName, new Date(System.currentTimeMillis())));
            mTeamChatAdapter.notifyDataSetChanged();
            String url = "sendMessage";
            FormBody formBody = new FormBody.Builder().add("content", mEditText.getText().toString())
                    .add("senderId", String.valueOf(senderId))
                    .add("teamId", String.valueOf(teamId))
                    .build();
            HttpUtil.connectInternet(url, formBody, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }
            });
            mEditText.setText("");
        });
    }
}
