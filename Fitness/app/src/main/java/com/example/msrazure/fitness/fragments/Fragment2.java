package com.example.msrazure.fitness.fragments;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.msrazure.fitness.R;

/**
 * Created by MSRAzure on 01.12.2015.
 */
public class Fragment2 extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment2_layout, container, false);
        return v;
    }
}
