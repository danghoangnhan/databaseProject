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

public class Playlist {
    @SerializedName("listId")
    @Expose
    public final int listId;

    @SerializedName("playlistName")
    @Expose
    public final String playlistName;

    @SerializedName(" userId")
    @Expose
    public final long userId;

<<<<<<< Updated upstream
    public int songCount;

    public Playlist() {
        this.listId = -1;
        this.playlistName = "";
        this.userId = -1;
    }

    public int getListId() {
        return listId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public long getUserId() {
        return userId;
    }

    public Playlist(int _listId, String _playlistName , long _userId) {
        this.listId = _listId;
        this.playlistName = _playlistName;
        this.userId = _userId;
=======
    public final long playlistId;
    public final String userId;
    public final String  playlistName;

    public Playlist() {
        this.playlistId = -1;
        this.userId = "";
        this.playlistName = "";
    }

    public Playlist(long _id, String _name, String  playlistName) {
        this.playlistId = _id;
        this.userId = _name;
        this.playlistName = playlistName;
>>>>>>> Stashed changes
    }
}
