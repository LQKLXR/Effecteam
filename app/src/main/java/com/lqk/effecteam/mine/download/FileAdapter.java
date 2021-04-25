package com.lqk.effecteam.mine.download;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;
import com.lqk.effecteam.common.data.DocumentData;

import java.io.File;
import java.util.List;

/**
 * Create By LiuQK on 2021/4/16
 * Describe: 文件列表 Adapter
 */
public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {

    private List<DocumentData> documentDataList;

    public FileAdapter(List<DocumentData> documentDataList) {
        this.documentDataList = documentDataList;
    }

    public void setDocumentDataList(List<DocumentData> documentDataList) {
        this.documentDataList = documentDataList;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_file, parent, false);
        FileViewHolder fileViewHolder = new FileViewHolder(view);
        return fileViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {

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

        holder.mFileName.setText(fileName);
    }

    @Override
    public int getItemCount() {
        return documentDataList.size();
    }

    class FileViewHolder extends RecyclerView.ViewHolder {

        private ImageView mFileIcon;
        private TextView mFileName;

        public FileViewHolder(@NonNull View itemView) {
            super(itemView);
            mFileIcon = itemView.findViewById(R.id.viewholder_file_icon);
            mFileName = itemView.findViewById(R.id.viewholder_file_name);
        }
    }
}
