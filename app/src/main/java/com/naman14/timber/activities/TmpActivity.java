package com.naman14.timber.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.naman14.timber.R;
import com.naman14.timber.Service.JsonApi;
import com.naman14.timber.adapters.PlaylistAdapter;
import com.naman14.timber.models.Playlist;
import com.naman14.timber.models.Song;
import com.naman14.timber.models.Tune;
import com.squareup.okhttp.ResponseBody;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TmpActivity extends AppCompatActivity {
    private static final String TAG = TmpActivity.class.getSimpleName();;
    PlaylistAdapter playlistAdapters;
    ArrayList<Playlist> OUTPUTPlaylist;
    ArrayList<Song> OUTPUTSong;
    ArrayList<Tune> OUTPUTTUNE;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_example_active);
        initViews();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.playlistsList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }
    private void loadJSON() {
        Map<String, String> build = new HashMap<>();
        int UserId = LoginActivity.getUser().getId();
        build.put("userId", String.valueOf(UserId));
        JSONObject RegisterJson = new JSONObject(build);
        JsonParser jsonParser = new JsonParser();
        JsonObject ToJson = (JsonObject) jsonParser.parse(RegisterJson.toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://140.136.151.130")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonApi Jsonapi = retrofit.create(JsonApi.class);

        for (int i = 0; i < OUTPUTTUNE.size(); i++) {
            final Tune sample = OUTPUTTUNE.get(i);
            Call<ResponseBody> call = Jsonapi.downloadTuneFile(sample.getTuneId());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "server contacted and has file");
                        boolean writtenToDisk = writeResponseBodyToDisk(response.body(),sample.getTuneName());
                        Log.d(TAG, "file download was a success? " + writtenToDisk);
                    } else {
                        Log.d(TAG, "server contact failed");
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e(TAG, "error");
                }
            });

        }
    }
    private boolean writeResponseBodyToDisk(ResponseBody body,String fileName) {
        try {
            File futureStudioIconFile = new File(getExternalFilesDir(null) + File.separator + fileName);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) { break; }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}