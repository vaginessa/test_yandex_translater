package com.example.infraymer.test_yandex_translater;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.example.infraymer.test_yandex_translater.Fragments.HistoryAndFavFragment;
import com.example.infraymer.test_yandex_translater.Fragments.SettingsFragment;
import com.example.infraymer.test_yandex_translater.Fragments.TranslatorFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    private FragmentManager mFragmentManager;

    private Fragment mFragment;

    TranslatorFragment translatorFragment;
    HistoryAndFavFragment historyAndFavFragment;
    SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        initFragmentManager();

        initBottomNavigation();
    }

    // Инициализация фрагментов
    private void initFragments() {
        translatorFragment = new TranslatorFragment();
        historyAndFavFragment = new HistoryAndFavFragment();
        settingsFragment = new SettingsFragment();
    }

    // Инициализация менеджера фрагментов
    private void initFragmentManager() {
        mFragment = new TranslatorFragment();

        mFragmentManager = getSupportFragmentManager();
        // Показать фрагмент перевода
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, mFragment)
                .commit();

    }

    // Инициализация нижней навигации
    private void initBottomNavigation() {
        AHBottomNavigationAdapter navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.navigation);

        // Set background color
        // bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.colorAccent));

        // Change colors
        bottomNavigation.setAccentColor(getResources().getColor(R.color.colorAccent));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));

        bottomNavigation.setBehaviorTranslationEnabled(false);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0:
                        mFragment = new TranslatorFragment();
                        break;
                    case 1:
                        mFragment = new HistoryAndFavFragment();
                        break;
                    case 2:
                        mFragment = new SettingsFragment();
                        break;
                }
                mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
                return true;
            }
        });

        navigationAdapter.setupWithBottomNavigation(bottomNavigation);
    }
}
