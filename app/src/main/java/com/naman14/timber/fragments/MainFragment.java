/*
 * Copyright (C) 2015 Naman Dwivedi
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */

package com.naman14.timber.fragments;

import android.os.Bundle;
import android.preference.PreferenceManager;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;//import viewpager(控制介面左右滑動)
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeengine.Config;
import com.naman14.timber.R;
import com.naman14.timber.utils.ATEUtils;
import com.naman14.timber.utils.Helpers;
import com.naman14.timber.utils.PreferencesUtility;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private PreferencesUtility mPreferences;//宣告 PreferencesUtility類別的物件(util.PreferencesUtility)
    private ViewPager viewPager;//宣告ViewPager類別的物件(androidx.viewpager.widget.ViewPager)

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = PreferencesUtility.getInstance(getActivity());//設定PreferencesUtility.java的sInstance
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {//建立該fragment對應的ui
        View rootView = inflater.inflate(
                R.layout.fragment_main, container, false);//加載fragament_main.xml

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);//呼叫fragament_main.xml的toolbar;
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);//(fragment版本)設置為actionbar

        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();//宣告actionbar 的物件 ab 儲存設置好的action bar
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);//用來自定義標題欄左邊按鈕的樣式
        ab.setDisplayHomeAsUpEnabled(true);//在標題欄的最左邊設置一個返回按鈕


        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);//把fragement_main.xml 中的 viewpager讀進來
        if (viewPager != null) {//當viewpage不是空的時候
            setupViewPager(viewPager);//創一個viewpager
            viewPager.setOffscreenPageLimit(2);//除了目前的頁面以外加載2個頁面
        }

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);//把fragment_main.xml的tab加載進來
        tabLayout.setupWithViewPager(viewPager);//从VIewPager中获取TabLayout的Title、ViewPager滑动时设置TabLayout的Title和indicator

        return rootView;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {//onViewCreated是在onCreateView后被觸發的事件
        super.onViewCreated(view, savedInstanceState);
        if (PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("dark_theme", false)) {//從存取的狀態確定目前的主題是否為黑色主題
            ATE.apply(this, "dark_theme");//把現在的fragment換成黑色主題
        } else {
            ATE.apply(this, "light_theme");//把現在的fragment換成亮色主題
        }
        viewPager.setCurrentItem(mPreferences.getStartPageIndex());//控制滑動速度
    }

    private void setupViewPager(ViewPager viewPager) {//
        Adapter adapter = new Adapter(getChildFragmentManager());//宣告一個adapter(第117行) 的物件裡面有getChildFragmentManager返回一个FragmentManager为了设置和管理当前Fragment内部的Fragment
        adapter.addFragment(new SongsFragment(), this.getString(R.string.songs));//新增歌曲介面
        adapter.addFragment(new AlbumFragment(), this.getString(R.string.albums));//新增專輯介面
        viewPager.setAdapter(adapter);//建立adapter給viewpager
    }

    @Override
    public void onPause() {
        super.onPause();
<<<<<<< HEAD
        if (mPreferences.lastOpenedIsStartPagePreference()) {//如果最後打開的是起始頁首選項
            mPreferences.setStartPageIndex(viewPager.getCurrentItem());//根據目前的頁面設置起始頁索引
=======
        if (mPreferences.lastOpenedIsStartPagePreference()) {
            mPreferences.setStartPageIndex(viewPager.getCurrentItem());
            
>>>>>>> 954c14c33605d2003a3cfc978ea4ddbcf82baaa6
        }
    }

    @Override
    public void onResume() {//呼叫一些重新整理UI的函式
        super.onResume();
        String ateKey = Helpers.getATEKey(getActivity());
        ATEUtils.setStatusBarColor(getActivity(), ateKey, Config.primaryColor(getActivity(), ateKey));

    }

    @Override
    public void onStart() {
        super.onStart();
    }//,讓fragment變可見

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();//宣告fragment 的list
        private final List<String> mFragmentTitles = new ArrayList<>();//宣告string的list;

        public Adapter(FragmentManager fm) {
            super(fm);
        }//constructor

        public void addFragment(Fragment fragment, String title) {//增加fragment到list
            mFragments.add(fragment);//增加fragment到list
            mFragmentTitles.add(title);//增加title到list
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }//get該位置的fragament

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
