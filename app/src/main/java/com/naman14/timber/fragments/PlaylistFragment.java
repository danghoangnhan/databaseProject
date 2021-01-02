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
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.appthemeengine.ATE;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.naman14.timber.R;
import com.naman14.timber.Service.JsonApi;
import com.naman14.timber.activities.LoginActivity;
import com.naman14.timber.adapters.PlaylistAdapter;
import com.naman14.timber.dataloaders.PlaylistLoader;
import com.naman14.timber.dialogs.CreatePlaylistDialog;
import com.naman14.timber.models.Playlist;
import com.naman14.timber.subfragments.PlaylistPagerFragment;
import com.naman14.timber.utils.Constants;
import com.naman14.timber.utils.PreferencesUtility;
import com.naman14.timber.widgets.DividerItemDecoration;
import com.naman14.timber.widgets.MultiViewPager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaylistFragment extends Fragment {
    PlaylistAdapter playlistAdapters;
    ArrayList<Playlist> OUTPUT;
    private RecyclerView recyclerView;
    private int playlistcount;
    private FragmentStatePagerAdapter adapter;//宣告adapter
    private MultiViewPager pager;//宣告pager
    private GridLayoutManager layoutManager;//九宮格式的呈現list(原本是使用gridview來呈現，但是近期recycle view 可以利用這個模組來達成 )
    private RecyclerView.ItemDecoration itemDecoration;//itemdecoration 用來裝飾介面
    private PreferencesUtility mPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.fragment_playlist, container, false);
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        pager = rootView.findViewById(R.id.playlistpager);
        recyclerView  =rootView.findViewById(R.id.recyclerview);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();

        ab.setTitle(" ");
        initViews();
        loadJSON();
        playlistcount = 10;
        return rootView;
    }

    private void initViews(){
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void loadJSON(){
        Map<String, String> build = new HashMap<>();
        build.put("userId", String.valueOf(LoginActivity.getUser().getId()));
        JSONObject RegisterJson = new JSONObject(build);
        JsonParser jsonParser = new JsonParser();
        JsonObject ToJson = (JsonObject) jsonParser.parse(RegisterJson.toString());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://140.136.151.130")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonApi request = retrofit.create(JsonApi.class);
        Call<List<Playlist>> call = request.getPlaylist(ToJson);

        call.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                List<Playlist> jsonResponse = response.body();
                OUTPUT = new ArrayList<>(jsonResponse.size());
                OUTPUT.addAll(jsonResponse);
                playlistAdapters = new PlaylistAdapter(OUTPUT);
                recyclerView.setAdapter(playlistAdapters);
            }
            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;
        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.top = space;
            outRect.right = space;
            outRect.bottom = space;

        }
    }
    public void updatePlaylists(final long id) {
        loadJSON();
        playlistcount = OUTPUT.size();
    }
    

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.ACTION_DELETE_PLAYLIST) {
            if (resultCode == Activity.RESULT_OK) {
                loadJSON();
            }

        }
    }
}

