package com.lqk.effecteam.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lqk.effecteam.R;
import com.lqk.effecteam.account.AccountActivity;
import com.lqk.effecteam.common.ActivityContainer;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.common.HttpUtil;
import com.lqk.effecteam.mine.download.MineDownLoadActivity;
import com.lqk.effecteam.mine.dynamic.MineDynamicActivity;
import com.lqk.effecteam.mine.task.MineTaskActivity;
import com.xuexiang.xui.widget.button.shadowbutton.ShadowButton;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;

/**
 * Create By LiuQK on 2021/4/15
 * Describe: 个人界面的 Fragment
 */
public class MineFragment extends Fragment {


    private ShadowButton mMineTodoButton;
    private ShadowButton mMineTaskButton;
    private ShadowButton mMineDynamicButton;
    private ShadowButton mMineDownloadButton;
    private SuperButton mMineLogoutButton;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        mMineTodoButton = view.findViewById(R.id.mine_todo_button);
        mMineTaskButton = view.findViewById(R.id.mine_task_button);
        mMineDynamicButton = view.findViewById(R.id.mine_dynamic_button);
        mMineDownloadButton = view.findViewById(R.id.mine_download_button);
        mMineLogoutButton = view.findViewById(R.id.mine_logout_button);

        addListener(view);
    }

    private void addListener(View view) {


        mMineTaskButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MineTaskActivity.class);
            startActivity(intent);
        });
        mMineDynamicButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MineDynamicActivity.class);
            startActivity(intent);
        });
        mMineDownloadButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MineDownLoadActivity.class);
            startActivity(intent);
        });
        mMineLogoutButton.setOnClickListener(v -> {
            /* 把保存的数据清除 */
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(HttpUtil.Shared_File_Name, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("userId");
            editor.remove("actualName");
            editor.remove("gender");
            editor.apply();
            /* 跳转Activity */
            Intent intent = new Intent(getActivity(), AccountActivity.class);
            startActivity(intent);
            /* 摧毁之前的全部Activity */
            ActivityContainer.destroyAllActivities();
        });
    }


}
