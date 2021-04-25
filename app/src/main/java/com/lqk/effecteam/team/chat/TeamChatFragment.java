package com.lqk.effecteam.team.chat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;

import java.util.ArrayList;
import java.util.List;

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
    private List<Message> mMessageList;

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

        mTeamChatAdapter = new TeamChatAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mTeamChatAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        addListener(view);
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
    }
}
