package com.lqk.effecteam.doc;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lqk.effecteam.R;
import com.lqk.effecteam.common.HttpUtil;
import com.lqk.effecteam.common.data.DocumentData;
import com.lqk.effecteam.mine.download.FileAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Create By LiuQK on 2021/4/25
 * Describe: 文件的 Fragment
 */
public class DocFragment extends Fragment {

    public static final int PROJECT = 0;
    public static final int TEAM = 1;
    public static final int LOCAL = 2;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mMineDownloadRecyclerview;
    private FileAdapter mFileAdapter;

    private List<DocumentData> documentDataList;

    /* 根据当前 Fragment 所处的位置, 发挥不同的作用 */
    private int type;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1: // 网络异常的情况
                    Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2: // 正常获得了文件列表的情况
                    List<DocumentData> documentDatas = (List<DocumentData>) msg.obj;
                    if (documentDatas.size() == 0) {
                        Toast.makeText(getActivity(), "当前没有文件", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        documentDataList = documentDatas;
                        mFileAdapter.setDocumentDataList(documentDataList);
                        mFileAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };


    public void setType(int type) {
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doc, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        mMineDownloadRecyclerview = view.findViewById(R.id.doc_recyclerview);
        mFileAdapter = new FileAdapter(new ArrayList<>());
        mMineDownloadRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMineDownloadRecyclerview.setAdapter(mFileAdapter);
        mMineDownloadRecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), 1));
        mSwipeRefreshLayout = view.findViewById(R.id.doc_refresh);

        addListener();
    }

    private void addListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this::loadDoc);
    }


    private void loadDoc() {
        if (type == PROJECT){
            loadProjectDoc();
        }
        else if (type == TEAM){
            loadTeamDoc();
        }
        else if (type == LOCAL){
            loadLocalDoc();
        }
    }

    /**
     * 加载项目的文件
     */
    private void loadProjectDoc() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(HttpUtil.Shared_File_Name, Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);
        int projectId = getActivity().getIntent().getIntExtra("projectId", 0);
        String url = "getProjectDocs?userId=" + userId + "&projectId=" + projectId;
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
                List<DocumentData> documentDatas = gson.fromJson(body, new TypeToken<List<DocumentData>>(){}.getType());
                Message message = Message.obtain();
                message.what = 2;
                message.obj = documentDatas;
                handler.sendMessage(message);
            }
        });
    }

    /**
     * 加载队伍的文件
     */
    private void loadTeamDoc() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(HttpUtil.Shared_File_Name, Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);
        int teamId = getActivity().getIntent().getIntExtra("teamId", 0);
        String url = "getTeamDocs?userId=" + userId + "&teamId=" + teamId;
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
                List<DocumentData> documentDatas = gson.fromJson(body, new TypeToken<List<DocumentData>>(){}.getType());
                Message message = Message.obtain();
                message.what = 2;
                message.obj = documentDatas;
                handler.sendMessage(message);
            }
        });
    }

    /**
     * 加载本地的文件
     */
    private void loadLocalDoc() {

    }
}
