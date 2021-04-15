package com.lqk.effecteam.task.create;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lqk.effecteam.R;
import com.xuexiang.xui.widget.button.SmoothCheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By LiuQK on 2021/4/14
 * Describe:
 */
public class PickAdapter extends RecyclerView.Adapter<PickAdapter.PickViewHolder> {


    private List<PickItem> pickItemList;
    private ArrayList<Integer> resultList;

    public PickAdapter(List<PickItem> pickItemList, ArrayList<Integer> resultList) {
        this.pickItemList = pickItemList;
        this.resultList = resultList;
    }

    public void setPickItemList(List<PickItem> pickItemList) {
        this.pickItemList = pickItemList;
    }

    @NonNull
    @Override
    public PickViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_pick, parent, false);
        PickViewHolder pickViewHolder = new PickViewHolder(view);
        return pickViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PickViewHolder holder, int position) {
        holder.mId = pickItemList.get(position).getId();
        holder.mTextView.setText(pickItemList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return pickItemList.size();
    }

    class PickViewHolder extends RecyclerView.ViewHolder {

        private int mId;
        private SmoothCheckBox mSmoothCheckBox;
        private TextView mTextView;

        public PickViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            mSmoothCheckBox = itemView.findViewById(R.id.viewholder_pick_checkbox);
            mTextView = itemView.findViewById(R.id.viewholder_pick_name);

            mSmoothCheckBox.setOnCheckedChangeListener((checkBox, isChecked) -> {
                if (isChecked) {
                    resultList.add(new Integer(mId));
                }
                else {
                    resultList.remove(new Integer(mId));
                }
            });
        }
    }
}
