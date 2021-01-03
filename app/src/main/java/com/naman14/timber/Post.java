package com.naman14.timber;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    private long listId;
    @SerializedName("playListName")
    @Expose
    private String playListName;


    private long userId;

    public long getListId() {
        return listId;
    }

    public String getPlayListName() {
        return playListName;
    }

    public long getUserId() {
        return userId;
    }
}
