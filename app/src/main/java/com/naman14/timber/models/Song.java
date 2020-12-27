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

package com.naman14.timber.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.StringTokenizer;

public class Song {
    @SerializedName("songId")
    @Expose
    public final long songId;
    @SerializedName("songName")
    @Expose
    public final String songName;
    @SerializedName("duration")
    @Expose
    public final int duration;
    @SerializedName("path")
    @Expose
    public final String path;
    @SerializedName("playCount")
    @Expose
    public final int playCount;
    @SerializedName("listId")
    @Expose
    public final long listId;
    @SerializedName("playTime")
    @Expose
    public final Date playTime;
    @SerializedName("createTime")
    @Expose
    public final Date createTime;


    public Song() {
        this.songId = -1;
        this.listId = -1;
        this.songName = "";
        this.duration = -1;
        this.playCount = -1;
        this.playTime = new Date();
        this.createTime = new Date();
        this.path = "";
    }

    public long getSongId() {
        return songId;
    }
    public long getListId() {
        return listId;
    }
    public String getSongName() {
        return songName;
    }
    public int getDuration() {
        return duration;
    }
    public int getPlayCount() {
        return playCount;
    }
    public Date getPlayTime() {
        return playTime;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public String getPath() {
        return path;
    }

    public Song(long _songId, long _listId, String _songName, int _duration, int _playCount, Date _playTime, Date _createTime, String _path) {
        this.songId = _songId;
        this.listId = _listId;
        this.songName = _songName;
        this.duration = _duration;
        this.playCount = _playCount;
        this.playTime = _playTime;
        this.createTime= _createTime;
        this.path = _path;
    }
}
