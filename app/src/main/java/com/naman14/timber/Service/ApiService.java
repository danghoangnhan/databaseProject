package com.naman14.timber.Service;

public class ApiService {
    public static String Base_url="http://140.136.151.130/";
    public static JsonApi getService(){
        return ApiClient.getClient(Base_url).create(JsonApi.class);// getclient()direct to server create() jsonapi.class declare req
    }
}
