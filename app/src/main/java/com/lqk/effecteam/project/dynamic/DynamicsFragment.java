package com.lqk.effecteam.project.dynamic;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lqk.effecteam.R;
import com.lqk.effecteam.common.data.DynamicData;
import com.lqk.effecteam.common.entity.Dynamic;
import com.lqk.effecteam.common.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 成员动态
 */
public class DynamicsFragment extends Fragment {

    public static final int PROJECT = 0;
    public static final int MINE = 1;

    private RecyclerView mProjectDynamicRecyclerview;
    private DynamicAdapter mDynamicAdapter;
    private List<DynamicData> mDynamicDataList;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private int type;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(),"网络连接失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    mSwipeRefreshLayout.setRefreshing(false);
                    List<DynamicData> dynamicDatas = (List<DynamicData>) msg.obj;
                    if (dynamicDatas.size() == 0){

                    }
                    else {
                        mDynamicDataList = dynamicDatas;
                        Collections.sort(mDynamicDataList, new Comparator<DynamicData>() {
                            @Override
                            public int compare(DynamicData o1, DynamicData o2) {
                                return o2.getDate().compareTo(o1.getDate());
                            }
                        });
                        mDynamicAdapter.setDynamicDataList(mDynamicDataList);
                        mDynamicAdapter.notifyDataSetChanged();
                        break;
                    }
            }
        }
    };


    public void setType(int type) {
        this.type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_project_dynamics, container, false);
        initView(view);
        loadDynamic();
        return view;
    }

    private void initView(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.dynamic_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this::loadDynamic);
        mProjectDynamicRecyclerview = view.findViewById(R.id.dynamic_recyclerview);
        mDynamicAdapter = new DynamicAdapter(new ArrayList<>(), getActivity());
        mProjectDynamicRecyclerview.setAdapter(mDynamicAdapter);
        mProjectDynamicRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDynamicAdapter.setType(this.type);

    }

    private void loadDynamic() {
        mSwipeRefreshLayout.setRefreshing(true);
        String url = null;
        int userId = getActivity().getSharedPreferences(HttpUtil.Shared_File_Name, Context.MODE_PRIVATE).getInt("userId", 0);
        if (type == PROJECT) {
            int projectId = getActivity().getIntent().getIntExtra("projectId", 0);
            url = "getProjectDynamics?userId=" + userId + "&projectId=" + projectId;
        } else if (type == MINE) {
            url = "getMyDynamics?userId=" + userId;
        }
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
                List<DynamicData> dynamicDatas =  gson.fromJson(body, new TypeToken<List<DynamicData>>(){}.getType());
                Message message = Message.obtain();
                message.what = 2;
                message.obj = dynamicDatas;
                handler.sendMessage(message);
            }
        });
    }


}