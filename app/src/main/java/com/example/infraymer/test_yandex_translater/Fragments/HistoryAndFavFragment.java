package com.example.infraymer.test_yandex_translater.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infraymer.test_yandex_translater.Adapters.ViewPagerAdapter;
import com.example.infraymer.test_yandex_translater.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by infraymer on 15.04.17.
 */

public class HistoryAndFavFragment extends Fragment {

    @BindView(R.id.history_fav_tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.history_fav_viewPager)
    ViewPager mViewPager;

    private ViewPagerAdapter mViewPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hystory_and_favourites, container, false);
        ButterKnife.bind(this, view);

        initViewPager();

        mTabLayout.setupWithViewPager(mViewPager);

        return view;
    }

    private void initViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new HistoryFragment(), getString(R.string.history));
        adapter.addFragment(new FavouriteFragment(), getString(R.string.favourite));
        mViewPager.setAdapter(adapter);
    }
}
