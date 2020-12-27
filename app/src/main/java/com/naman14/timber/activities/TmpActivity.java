package com.naman14.timber.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.naman14.timber.R;
import com.naman14.timber.Service.JsonApi;
import com.naman14.timber.models.Playlist;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TmpActivity extends AppCompatActivity {

    List<Playlist> OUTPUT = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmp);

        GetPlatList();
    }

    public void GetPlatList()
    {
        JSONObject build = new JSONObject();
        try {
            build.put("userid", 4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonParser jsonParser = new JsonParser();
        JsonObject ToJsonID = (JsonObject) jsonParser.parse(build.toString());
        System.out.println(ToJsonID);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://140.136.151.130:80/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonApi Jsonapi = retrofit.create(JsonApi.class);

        Call<List<Playlist>> placeHolderApis = Jsonapi.getPlaylist( ToJsonID );
        placeHolderApis.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                if(response.isSuccessful())System.out.println("PlayList Post OK!");
                for(Playlist pl : response.body())
                    OUTPUT.add(pl);
                for(Playlist playlist : OUTPUT)
                    System.out.println("NOW List have : " + playlist.getPlaylistName());
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {
                System.out.println("PlayList Failed : " + t.toString());
            }
        });

    }
}