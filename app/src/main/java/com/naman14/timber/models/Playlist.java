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


    public int songCount;

    public Playlist() {
        this. listId= -1;
        this.platListName = "";
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



    public Playlist(long _id, String _name, String  playlistName) {
        this.playlistId = _id;
        this.userId = _name;
        this.playlistName = playlistName;

    }
}