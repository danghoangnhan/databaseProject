package com.naman14.timber.models;

public class Tune {
    public final long tuneId;
    public final String tuneName;
    public final long path;

    public Tune() {
        this.tuneId= -1;
        this.tuneName = "";
        this.path= -1;
    }

    public Tune(long _tuneId, String _tuneName, long _path) {
        this.tuneId= _tuneId;
        this.tuneName = _tuneName;
        this.path = _path;
    }
}
