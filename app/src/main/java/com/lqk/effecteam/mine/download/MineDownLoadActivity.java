package com.lqk.effecteam.mine.download;

import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.mine.dynamic.MineDynamicActivity;
import com.lqk.effecteam.project.ProjectVirtualData;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MineDownLoadActivity extends BaseActivity {

    private TitleBar mMineDownloadTitleBar;
    private RecyclerView mMineDownloadRecyclerview;
    private FileAdapter mFileAdapter;

    private List<File> fileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_down_load);
        initView();
    }

    private void initView() {
        mMineDownloadTitleBar = findViewById(R.id.mine_download_title_bar);
        mMineDownloadRecyclerview = findViewById(R.id.mine_download_recyclerview);
        // TODO 虚假数据
        fileList = new ArrayList<>();
        File file = getFilesDir();
        File[] files = file.listFiles();
        for (File file1 : files) {
            fileList.add(file1);
        }
        mFileAdapter = new FileAdapter(fileList);
        mMineDownloadRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mMineDownloadRecyclerview.setAdapter(mFileAdapter);
        mMineDownloadRecyclerview.addItemDecoration(new DividerItemDecoration(this,1));
        addListener();
    }

    private void addListener() {
        mMineDownloadTitleBar.setLeftClickListener(v -> finish());
    }
}