package com.lqk.effecteam.doc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lqk.effecteam.R;
import com.lqk.effecteam.common.util.HttpUtil;
import com.lqk.effecteam.common.data.DocumentData;
import com.lqk.effecteam.common.util.Uri2PathUtil;
import com.lqk.effecteam.mine.download.FileAdapter;
import com.lqk.effecteam.team.join.JoinTeamActivity;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

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

    private FloatingActionButton mFloatButton;

    /*加载转圈*/
    private MaterialDialog mLoadingDialog;

    /* 根据当前 Fragment 所处的位置, 发挥不同的作用 */
    private int type;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1: // 网络异常的情况
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2: // 正常获得了文件列表的情况
                    mSwipeRefreshLayout.setRefreshing(false);
                    List<DocumentData> documentDatas = (List<DocumentData>) msg.obj;
                    if (documentDatas.size() == 0) {
                        // Toast.makeText(getActivity(), "当前没有文件", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        documentDataList = documentDatas;
                        mFileAdapter.setDocumentDataList(documentDataList);
                        mFileAdapter.notifyDataSetChanged();
                    }
                    break;
                case 3:
                    Toast.makeText(getActivity(), "上传文件成功", Toast.LENGTH_SHORT).show();
                    loadDoc();
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
        loadDoc();
        return view;
    }

    private void initView(View view) {

        mLoadingDialog = new MaterialDialog.Builder(getActivity()).content(R.string.downloadLoading)
                .progress(true, 0)
                .progressIndeterminateStyle(false).build();

        mMineDownloadRecyclerview = view.findViewById(R.id.doc_recyclerview);
        mFileAdapter = new FileAdapter(new ArrayList<>(), DocFragment.this);
        mMineDownloadRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMineDownloadRecyclerview.setAdapter(mFileAdapter);
        mMineDownloadRecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), 1));

        mSwipeRefreshLayout = view.findViewById(R.id.doc_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mFloatButton = view.findViewById(R.id.doc_float_button);

        addListener();
    }

    private void addListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this::loadDoc);

        /*点击悬浮按钮-进入系统文件选择器*/
        mFloatButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, 120);
        });
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
        documentDataList = new ArrayList<>();
        SharedPreferences sp = getActivity().getSharedPreferences(HttpUtil.Shared_File_Name, Context.MODE_PRIVATE);
        String email = sp.getString("email", "");
        String dirPath = HttpUtil.FileDir + File.separator + email;
        File fileDir = new File(dirPath);
        File[] files = fileDir.listFiles();
        Log.d("nowTest", "loadLocalDoc: " + files.length);
        for (File file : files) {
            documentDataList.add(new DocumentData(0, file.getName(), null, file.getAbsolutePath(), 0, null));
        }
        mFileAdapter.setDocumentDataList(documentDataList);
        mFileAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 120 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.d("ChooseFile", uri.toString());

            File filePathByUri = null;
            try {
                filePathByUri = Uri2PathUtil.getFile(getActivity(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("ChooseFile", filePathByUri.getAbsolutePath());

            File file = new File(filePathByUri.getAbsolutePath());
            SharedPreferences sp = getActivity().getSharedPreferences(HttpUtil.Shared_File_Name, Context.MODE_PRIVATE);
            int userId = sp.getInt("userId", 0);
            int projectId = getActivity().getIntent().getIntExtra("projectId", 0);
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file))
                    .addFormDataPart("userId", String.valueOf(userId))
                    .addFormDataPart("projectId", String.valueOf(projectId))
                    .build();
            HttpUtil.connectInternet("uploadDocument", requestBody, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Message message = Message.obtain();
                    message.what = 1;
                    handler.sendMessage(message);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String body = new String(response.body().bytes());
                    if (body.equals("success")) {
                        Message message = Message.obtain();
                        message.what = 3;
                        handler.sendMessage(message);
                    }
                    else {

                    }
                }
            });
        }
    }


    public void showLoading(){
        mLoadingDialog.show();
    }

    public void closeLoading(){
        mLoadingDialog.dismiss();
    }
}
