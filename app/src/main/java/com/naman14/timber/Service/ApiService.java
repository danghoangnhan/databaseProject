package com.naman14.timber.Service;

import android.telecom.Call;

public class ApiService {
    public static String Base_url="http://140.136.151.130/";
    public static JsonApi getService(){
        return ApiClient.getClient(Base_url).create(JsonApi.class);
    }


}
