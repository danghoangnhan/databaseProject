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

public class Playlist {

    public final long listId;
    public final String playlistName;
    public final long userId;

    public Playlist() {
        this.listId = -1;
        this.playlistName = "";
        this.userId = -1;
    }

    public Playlist(long _listId, String _playlistName , long _userId) {
        this.listId = _listId;
        this.playlistName = _playlistName;
        this.userId = _userId;
    }
}
