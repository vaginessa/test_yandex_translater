package com.example.infraymer.test_yandex_translater.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infraymer.test_yandex_translater.R;

import butterknife.ButterKnife;

/**
 * Created by infraymer on 15.04.17.
 */

public class SettingsFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
