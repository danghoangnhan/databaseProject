package com.naman14.timber.activities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class authentication {
    @SerializedName("account")
    @Expose
    private String account;
    @SerializedName("password")
    @Expose
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


