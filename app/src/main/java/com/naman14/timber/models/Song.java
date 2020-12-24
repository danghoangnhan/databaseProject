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

public class Song {

    public final long songId;
    public final long listId;
    public final String songName;
    public final int tuneSet;
    public final int duration;
    public final int playCount;
    public final int playTime;
    public final int createTime;
    public final long path;

    public Song() {
        this.songId = -1;
        this.listId = -1;
        this.songName = "";
        this.tuneSet = -1;
        this.duration = -1;
        this.playCount = -1;
        this.playTime = -1;
        this.createTime = -1;
        this.path = -1;
    }

    public Song(long _songId, long _listId, String _songName, int _tuneSet, int _duration, int _playCount, int _playTime, int _createTime, long _path) {
        this.songId = _songId;
        this.listId = _listId;
        this.songName = _songName;
        this.tuneSet = _tuneSet;
        this.duration = _duration;
        this.playCount = _playCount;
        this.playTime = _playTime;
        this.createTime= _createTime;
        this.path = _path;
    }
}
