package com.naman14.timber.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.naman14.timber.R;
import com.naman14.timber.Service.JsonApi;
import com.naman14.timber.adapters.PlaylistAdapter;
import com.naman14.timber.models.Playlist;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TmpActivity extends AppCompatActivity {
    PlaylistAdapter playlistAdapters;
    ArrayList<Playlist> OUTPUT;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_example_active);
        initViews();
    }
    private void initViews(){
        recyclerView = findViewById(R.id.playlistsList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }
    private void loadJSON(){

        Map<String, String> build = new HashMap<>();
        int UserId = LoginActivity.getUser().getId();
        build.put("userId",String.valueOf(UserId));
        JSONObject RegisterJson = new JSONObject(build);
        JsonParser jsonParser = new JsonParser();
        JsonObject ToJson = (JsonObject) jsonParser.parse(RegisterJson.toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://140.136.151.130")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonApi request = retrofit.create(JsonApi.class);
        Call<List<Playlist>> call = request.getPlaylist(ToJson);

        call.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                if(response.isSuccessful()) {
                    List<Playlist> jsonResponse = response.body();
                    OUTPUT = new ArrayList<>(jsonResponse.size());
                    OUTPUT.addAll(jsonResponse);
                    playlistAdapters = new PlaylistAdapter(OUTPUT);
                    recyclerView.setAdapter(playlistAdapters);
                }
            }
            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
}