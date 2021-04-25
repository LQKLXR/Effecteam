package com.lqk.effecteam.project.dynamic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lqk.effecteam.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 成员动态
 */
public class DynamicsFragment extends Fragment {

    private RecyclerView mProjectDynamicRecyclerview;
    private DynamicAdapter mDynamicAdapter;
    private List<Dynamic> mDynamicList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_project_dynamics, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mProjectDynamicRecyclerview = view.findViewById(R.id.project_dynamic_recyclerview);
        mDynamicAdapter = new DynamicAdapter(new ArrayList<>(), getActivity());
        mProjectDynamicRecyclerview.setAdapter(mDynamicAdapter);
        mProjectDynamicRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        addListener(view);
    }

    private void addListener(View view) {

    }

}