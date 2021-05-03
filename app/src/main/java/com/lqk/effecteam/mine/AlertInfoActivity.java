package com.lqk.effecteam.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.lqk.effecteam.R;
import com.lqk.effecteam.account.AccountActivity;
import com.lqk.effecteam.common.ActivityContainer;
import com.lqk.effecteam.common.BaseActivity;
import com.lqk.effecteam.common.util.HttpUtil;
import com.lqk.effecteam.project.list.ProjectAdapter;
import com.lqk.effecteam.project.list.ProjectFragment;
import com.lqk.effecteam.team.home.TeamHomeProjectFragment;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.button.SmoothCheckBox;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class AlertInfoActivity extends BaseActivity {

/*
new MaterialDialog.Builder(activity)
    .content(R.string.projectCompleteAlertMenuTitle)
    .positiveText(R.string.Yes)
    .negativeText(R.string.No)
    .onPositive((dialog1, which) -> {

        if (fragment instanceof ProjectFragment){
            ProjectFragment projectFragment = (ProjectFragment) ProjectAdapter.this.fragment;
            projectFragment.completeProject(projectId);
        }
        else if (fragment instanceof TeamHomeProjectFragment){
            TeamHomeProjectFragment teamHomeProjectFragment = (TeamHomeProjectFragment) ProjectAdapter.this.fragment;
            teamHomeProjectFragment.completeProject(projectId);
        }
    }).show();


    // 把保存的数据清除
    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(HttpUtil.Shared_File_Name, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("userId");
            editor.remove("actualName");
            editor.remove("gender");
            editor.apply();
    // 跳转Activity
    Intent intent = new Intent(getActivity(), AccountActivity.class);
    startActivity(intent);
    // 摧毁之前的全部Activity
    ActivityContainer.destroyAllActivities();


*/
    private TitleBar mTitleBar;
    private MultiLineEditText mActualname;
    private SmoothCheckBox mCheckNan;
    private SmoothCheckBox mCheckNv;
    private EditText mInputOriginalPswd;
    private EditText mInputNewPswd;
    private EditText mInputNewPswdRe;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(AlertInfoActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    new MaterialDialog.Builder(AlertInfoActivity.this)
                            .content("密码修改成功，请重新登录")
                            .positiveText(R.string.Yes)
                            .onPositive((dialog1, which) -> {
                                // 把保存的数据清除
                                SharedPreferences sharedPreferences = getSharedPreferences(HttpUtil.Shared_File_Name, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove("userId");
                                editor.remove("actualName");
                                editor.remove("gender");
                                editor.remove("password");
                                editor.apply();
                                // 跳转Activity
                                Intent intent = new Intent(AlertInfoActivity.this, AccountActivity.class);
                                startActivity(intent);
                                // 摧毁之前的全部Activity
                                ActivityContainer.destroyAllActivities();
                            }).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altrt_info);

        initView();
    }

    private void initView() {
        mTitleBar = findViewById(R.id.alter_info_title_bar);
        mActualname = findViewById(R.id.alter_info_actualname);
        mCheckNan = findViewById(R.id.alter_info_check_nan);
        mCheckNv = findViewById(R.id.alter_info_check_nv);
        mInputOriginalPswd = findViewById(R.id.alter_info_input_original_pswd);
        mInputNewPswd = findViewById(R.id.alter_info_input_new_pswd);
        mInputNewPswdRe = findViewById(R.id.alter_info_input_new_pswd_re);

        addListener();
    }

    private void addListener() {


        SharedPreferences sp = getSharedPreferences(HttpUtil.Shared_File_Name, MODE_PRIVATE);
        String oldActualName = sp.getString("actualName", "");
        String oldPassword = sp.getString("password", "");
        String oldGender = sp.getString("gender", "");
        int userId = sp.getInt("userId", 0);

        if (oldGender.equals("男")) {
            mCheckNan.setChecked(true);
        }
        else if (oldGender.equals("女")) {
            mCheckNv.setChecked(true);
        }

        mActualname.setContentText(oldActualName);
        mTitleBar.setLeftClickListener(v -> finish());
        mCheckNan.setOnCheckedChangeListener((checkBox, isChecked) -> mCheckNv.setChecked(false));
        mCheckNv.setOnCheckedChangeListener((checkBox, isChecked) -> mCheckNan.setChecked(false));

        mTitleBar.addAction(new TitleBar.TextAction("确定") {
            @Override
            public void performAction(View view) {
                /* 输入信息不全的情况 */
                if ((!mCheckNan.isChecked() && !mCheckNv.isChecked())
                || mActualname.getContentText() == null
                || mActualname.getContentText().length() == 0
                || mInputOriginalPswd.getText().toString().length() == 0
                || mInputNewPswd.getText().toString().length() == 0) {
                    Toast.makeText(AlertInfoActivity.this, "缺少必要信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d("mima", "performAction: " + mInputNewPswd.getText().toString());
                Log.d("mima", "performAction: " + mInputNewPswdRe.getText().toString());
                if (!mInputNewPswd.getText().toString().equals(mInputNewPswdRe.getText().toString())){
                    Toast.makeText(AlertInfoActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    return;
                }

                /* 密码错误的情况 */
                if (!oldPassword.equals(mInputOriginalPswd.getText().toString())) {
                    Toast.makeText(AlertInfoActivity.this, "原密码错误", Toast.LENGTH_SHORT).show();
                    return;
                }

                String actualName = mActualname.getContentText();
                String newGender = null;
                if (mCheckNan.isChecked()){
                    newGender = "男";
                }
                else {
                    newGender = "女";
                }
                /* 开始修改密码的网络操作 */
                FormBody formBody = new FormBody.Builder().add("userId", String.valueOf(userId))
                        .add("gender", newGender)
                        .add("name", actualName)
                        .add("password", mInputNewPswd.getText().toString())
                        .build();
                String url = "alterInfo";
                HttpUtil.connectInternet(url, formBody, new Callback() {
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
                            message.what = 2;
                            handler.sendMessage(message);
                        }
                    }
                });
            }
        });



    }
}