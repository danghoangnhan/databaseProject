package com.naman14.timber.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("userId")
    @Expose
    public final long userId;
    @SerializedName("userName")
    @Expose
    public final String userName;
    @SerializedName("passWord")
    @Expose
    public final long passWord;

    public User() {
        this.userId= -1;
        this.userName = "";
        this.passWord= -1;
    }

    public long getuserId() {
        return userId;
    }
    public String getuserName() {
        return userName;
    }
    public long getpassWord() {
        return passWord;
    }

    public User(long _userId, String _userName, long _passWord) {
        this.userId= _userId;
        this.userName = _userName;
        this.passWord = _passWord;
    }

}
