package com.naman14.timber.models;

import com.google.gson.annotations.SerializedName;

public class History {
    @SerializedName("songId")
    private Integer songId;
    @SerializedName("songName")
    private String songName;
    @SerializedName("duration")
    private Integer duration;
    @SerializedName("path")
    private String path;
    @SerializedName("tuneSet")
    private String tuneSet;
    @SerializedName("playCount")
    private Integer playCount;
    @SerializedName("listId")
    private Integer listId;
    @SerializedName("playTime")
    private String playTime;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("PlayListName")
    private String PlayListName;
    @SerializedName("userId")
    private Integer userId;

    public History(Integer songId, String songName, Integer duration, String path, String tuneSet, Integer playCount, Integer listId, String playTime, String createTime, String playListName, Integer userId) {
        this.songId = songId;
        this.songName = songName;
        this.duration = duration;
        this.path = path;
        this.tuneSet = tuneSet;
        this.playCount = playCount;
        this.listId = listId;
        this.playTime = playTime;
        this.createTime = createTime;
        PlayListName = playListName;
        this.userId = userId;
    }

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTuneSet() {
        return tuneSet;
    }

    public void setTuneSet(String tuneSet) {
        this.tuneSet = tuneSet;
    }

    public Integer getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Integer playCount) {
        this.playCount = playCount;
    }

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPlayListName() {
        return PlayListName;
    }

    public void setPlayListName(String playListName) {
        PlayListName = playListName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
