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
    public final String path;

    public Tune() {
        this.tuneId= -1;
        this.tuneName = "";
        this.path= "";
    }

    public long getTuneId() {
        return tuneId;
    }
    public String getTuneName(){
        return tuneName;
    }
    public String getPath() {
        return path;
    }

    public Tune(long _tuneId, String _tuneName, String _path) {
        this.tuneId= _tuneId;
        this.tuneName = _tuneName;
        this.path = _path;
    }
}