package com.lqk.effecteam.mine.download;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.data.DocumentData;
import com.lqk.effecteam.common.util.HttpUtil;
import com.lqk.effecteam.common.util.MapTable;
import com.lqk.effecteam.doc.DocFragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Create By LiuQK on 2021/4/16
 * Describe: 文件列表 Adapter
 */
public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {

    private List<DocumentData> documentDataList;
    private Fragment fragment;

    public FileAdapter(List<DocumentData> documentDataList, Fragment fragment) {
        this.documentDataList = documentDataList;
        this.fragment = fragment;
    }

    public void setDocumentDataList(List<DocumentData> documentDataList) {
        this.documentDataList = documentDataList;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(fragment.getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    DocFragment fragment1 = (DocFragment) fragment;
                    fragment1.closeLoading();
                    Toast.makeText(fragment.getActivity(), "下载完成", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    DocFragment fragment2 = (DocFragment) fragment;
                    fragment2.closeLoading();
                    String path = (String) msg.obj;
                    MapTable.openFile(fragment.getActivity(), path);
                    break;
            }
        }
    };

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_file, parent, false);
        FileViewHolder fileViewHolder = new FileViewHolder(view);
        return fileViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        // 图标设置
        String fileName = documentDataList.get(position).getOriginalName();
        if (fileName.endsWith(".jpg")) {
            holder.mFileIcon.setImageResource(R.drawable.icon_file_jpg);
        } else if (fileName.endsWith(".mp4")) {
            holder.mFileIcon.setImageResource(R.drawable.icon_file_mp4);
        } else if (fileName.endsWith(".pdf")) {
            holder.mFileIcon.setImageResource(R.drawable.icon_file_pdf);
        } else if (fileName.endsWith(".png")) {
            holder.mFileIcon.setImageResource(R.drawable.icon_file_png);
        } else if (fileName.endsWith(".ppt") || fileName.endsWith(".pptx")) {
            holder.mFileIcon.setImageResource(R.drawable.icon_file_ppt);
        } else if (fileName.endsWith(".txt")) {
            holder.mFileIcon.setImageResource(R.drawable.icon_file_txt);
        } else if (fileName.endsWith(".doc") || fileName.endsWith(".docx")) {
            holder.mFileIcon.setImageResource(R.drawable.icon_file_word);
        } else if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
            holder.mFileIcon.setImageResource(R.drawable.icon_file_xlsx);
        } else {
            holder.mFileIcon.setImageResource(R.drawable.icon_file_unknown);
        }
        // 文件名称设置
        holder.mFileName.setText(fileName);
        // 文件ID设置
        holder.docId = documentDataList.get(position).getId();
    }
    @Override
    public int getItemCount() {
        return documentDataList.size();
    }
    class FileViewHolder extends RecyclerView.ViewHolder {
        private ImageView mFileIcon; // 文件图标的 Image
        private TextView mFileName;  // 文件名称的 Text
        private int docId;           // 文件的ID

        public FileViewHolder(@NonNull View itemView) {
            super(itemView);
            mFileIcon = itemView.findViewById(R.id.viewholder_file_icon);
            mFileName = itemView.findViewById(R.id.viewholder_file_name);
            mFileName.setOnClickListener(v -> {
                DocFragment docFragment = (DocFragment) fragment;
                docFragment.showLoading();
                String url = "downloadDoc?docId=" + docId;
                String fileName = mFileName.getText().toString();
                SharedPreferences sp = fragment.getActivity().getSharedPreferences(HttpUtil.Shared_File_Name, Context.MODE_PRIVATE);
                String email = sp.getString("email", "");
                String dirPath = HttpUtil.FileDir + File.separator + email;
                String path = dirPath + File.separator + fileName;


                HttpUtil.connectInternet(url, null, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Message message = Message.obtain();
                        message.what = 1;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        File dirFile = new File(dirPath);
                        if (!dirFile.exists()){
                            dirFile.mkdirs();
                        }
                        File file = new File(path);

                        if (!file.exists()){
                            // 获得response的输入流
                            InputStream inputStream = response.body().byteStream();
                            file.createNewFile();
                            // 得到文件输出流
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            byte[] bytes = new byte[100];
                            int len = 0;
                            // 写入文件
                            while ((len = inputStream.read(bytes)) != -1){
                                fileOutputStream.write(bytes, 0, len);
                            }
                            inputStream.close();
                            fileOutputStream.close();
                            Message message = Message.obtain();
                            message.what = 2;
                            handler.sendMessage(message);
                        }
                        else {// 使用其他附件打开
                            Message message = Message.obtain();
                            message.obj = path;
                            message.what = 3;
                            handler.sendMessage(message);
                        }

                    }
                });
            });
        }
    }

}
