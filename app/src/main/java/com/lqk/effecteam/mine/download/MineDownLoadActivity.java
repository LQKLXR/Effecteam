package com.lqk.effecteam.mine.download;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.doc.DocFragment;
import com.xuexiang.xui.widget.actionbar.TitleBar;

public class MineDownLoadActivity extends BaseActivity {

    private TitleBar mMineDownloadTitleBar;
    private DocFragment docFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_download);
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        mMineDownloadTitleBar = findViewById(R.id.mine_activity_download_title);
        mMineDownloadTitleBar.setLeftClickListener(v -> finish());
        docFragment = new DocFragment();
        docFragment.setType(DocFragment.LOCAL);
        getSupportFragmentManager().beginTransaction().add(R.id.mine_activity_download_frame, docFragment).commit();
    }

}