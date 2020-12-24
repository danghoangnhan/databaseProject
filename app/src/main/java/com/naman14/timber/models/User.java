package com.naman14.timber.models;

public class User {
    public final long userId;
    public final String userName;
    public final long passWord;

    public User() {
        this.userId= -1;
        this.userName = "";
        this.passWord= -1;
    }

    public User(long _userId, String _userName, long _passWord) {
        this.userId= _userId;
        this.userName = _userName;
        this.passWord = _passWord;
    }

}
