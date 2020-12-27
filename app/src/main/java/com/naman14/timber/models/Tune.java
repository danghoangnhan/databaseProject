package com.naman14.timber.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tune {
    @SerializedName("tuneId")
    @Expose
    public final long tuneId;
    @SerializedName("tuneName")
    @Expose
    public final String tuneName;
    @SerializedName("path")
    @Expose
    public final long path;

    public Tune() {
        this.tuneId= -1;
        this.tuneName = "";
        this.path= -1;
    }

    public long gettuneId() {
        return tuneId;
    }
    public String tuneName(){
        return tuneName;
    }
    public long getpath() {
        return path;
    }

    public Tune(long _tuneId, String _tuneName, long _path) {
        this.tuneId= _tuneId;
        this.tuneName = _tuneName;
        this.path = _path;
    }
}