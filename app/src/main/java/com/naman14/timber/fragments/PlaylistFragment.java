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

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.appthemeengine.ATE;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.naman14.timber.R;
import com.naman14.timber.Service.ApiService;
import com.naman14.timber.Service.JsonApi;
import com.naman14.timber.activities.LoginActivity;
import com.naman14.timber.activities.MainActivity;
import com.naman14.timber.adapters.PlaylistAdapter;
import com.naman14.timber.dataloaders.PlaylistLoader;
import com.naman14.timber.dialogs.CreatePlaylistDialog;
import com.naman14.timber.models.Playlist;
import com.naman14.timber.models.authentication;
import com.naman14.timber.subfragments.PlaylistPagerFragment;
import com.naman14.timber.utils.Constants;
import com.naman14.timber.utils.PreferencesUtility;
import com.naman14.timber.widgets.DividerItemDecoration;
import com.naman14.timber.widgets.MultiViewPager;


import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaylistFragment extends Fragment {

    private FragmentStatePagerAdapter adapter;
    private MultiViewPager pager;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private RecyclerView.ItemDecoration itemDecoration;
    private PreferencesUtility mPreferences;
    private  int playlistcount;
    ArrayList<Playlist> returnPlaylist;


    private PlaylistAdapter mAdapter;

    private List<Playlist> playlists ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPlayLists();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_playlist, container, false);
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        pager = rootView.findViewById(R.id.playlistpager);
        recyclerView = rootView.findViewById(R.id.recyclerview);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.playlists);
        getPlayLists();
        initRecyclerView();
        return rootView;
    }




    private void getPlayLists(){
        try{
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://140.136.151.130/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonApi Jsonapi = retrofit.create(JsonApi.class);

        Call<List<Playlist>> placeHolderApis = Jsonapi.getPlaylist(LoginActivity.getUser().getId());

        placeHolderApis.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(@NonNull Call<List<Playlist>> call, @NonNull Response<List<Playlist>> response) {
                if (response.isSuccessful()) {
                    returnPlaylist = new ArrayList<>(response.body().size());
                    returnPlaylist.addAll(response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Playlist>> call, @NonNull Throwable t) {
                Toast toast=Toast.makeText(getActivity(),"login failed",Toast.LENGTH_SHORT);
                toast.show();
                t.printStackTrace();
            }
        });

    }
                catch (Exception e){
                    Toast toast=Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_SHORT);
                    toast.show();
                }
    }


    private void initPager() {
        pager.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setAdapter(mAdapter);
        adapter = new FragmentStatePagerAdapter(getChildFragmentManager()) {

            @Override
            public int getCount() { return returnPlaylist.size(); }

            @Override
            public Fragment getItem(int position) { return PlaylistPagerFragment.newInstance(position); }

        };
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);
    }

    private void initRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
        pager.setVisibility(View.GONE);
        setLayoutManager();
        mAdapter = new PlaylistAdapter(getContext(),returnPlaylist);
        recyclerView.setAdapter(mAdapter);
        if (getActivity() != null) {
            setItemDecoration();
        }
    }


    private void setLayoutManager() {
            layoutManager = new GridLayoutManager(getActivity(), 1);
            recyclerView.setLayoutManager(layoutManager);
    }

    private void setItemDecoration() {
            itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);
   }

    private void updateLayoutManager(int column) {
        recyclerView.removeItemDecoration(itemDecoration);
        recyclerView.setAdapter(new PlaylistAdapter(getActivity(), returnPlaylist));
        layoutManager.setSpanCount(column);
        layoutManager.requestLayout();
        setItemDecoration();
    }


    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {


            outRect.left = space;
            outRect.top = space;
            outRect.right = space;
            outRect.bottom = space;

        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("dark_theme", false)) {
            ATE.apply(this, "dark_theme");
        } else {
            ATE.apply(this, "light_theme");
        }
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_playlist, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_view_auto_playlists).setTitle("Show auto playlists");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_playlist:
                CreatePlaylistDialog.newInstance().show(getChildFragmentManager(), "CREATE_PLAYLIST");
                return true;
            case R.id.menu_show_as_list:
                mPreferences.setPlaylistView(Constants.PLAYLIST_VIEW_LIST);
                initRecyclerView();
                updateLayoutManager(1);
                return true;
            case R.id.menu_show_as_grid:
                mPreferences.setPlaylistView(Constants.PLAYLIST_VIEW_GRID);
                initRecyclerView();
                updateLayoutManager(2);
                return true;
            case R.id.menu_show_as_default:
                mPreferences.setPlaylistView(Constants.PLAYLIST_VIEW_DEFAULT);
                initPager();
                return true;
            case R.id.action_view_auto_playlists:
                    mPreferences.setToggleShowAutoPlaylist(true);
                reloadPlaylists();
                getActivity().invalidateOptionsMenu();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void updatePlaylists(final long id) {
        getPlayLists();
        playlists = returnPlaylist;
        playlistcount = playlists.size();
        mAdapter.updateDataSet(playlists);
    }

    public void reloadPlaylists() {
        getPlayLists();
        playlists = returnPlaylist;
            initRecyclerView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.ACTION_DELETE_PLAYLIST) {
            if (resultCode == Activity.RESULT_OK) {
                reloadPlaylists();
            }

        }
    }
}

