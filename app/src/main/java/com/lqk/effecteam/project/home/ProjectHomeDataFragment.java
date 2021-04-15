package com.lqk.effecteam.project.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lqk.effecteam.R;

/**
 * 项目共享资料Fragment
 */
public class ProjectHomeDataFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project_home_data, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
    }
}