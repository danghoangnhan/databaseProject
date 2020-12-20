package com.naman14.timber.models;

public class Tune {

    public final long tuneId;
    public final String tuneName;
    public final String path;

    public Tune() {
        this.tuneId = -1;
        this.tuneName = "";
        this.path = "";
    }
    public Tune( long _tuneId, String _tuneName, String _path) {
        this.tuneId = _tuneId;
        this.tuneName =_tuneName;
        this.path = _path;
    }
}
