package com.naman14.timber.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import com.naman14.timber.R;
import com.naman14.timber.Service.JsonApi;
import com.naman14.timber.models.Playlist;
import com.naman14.timber.models.Tune;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DownloadActivity extends AppCompatActivity {
    int TuneId = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        addEvent();
    }
    private void addEvent() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://140.136.151.130/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonApi Jsonapi = retrofit.create(JsonApi.class);


        Call<List<Tune>> placeHolderApis = Jsonapi.DownloadTuneFile(TuneId);
        placeHolderApis.enqueue(new Callback<List<Tune>>() {
            @Override
            public void onResponse(Call<List<Tune>> call, Response<List<Tune>> response) {

                InputStream input = (InputStream) response.body();

                try {


                    String SDCard=Environment.getExternalStorageDirectory()+"";
                    String path="file";
                    String fileName="626967726772726772726967.mid";
                    String pathName=SDCard+"/"+path+"/"+fileName;
                    File file=new File(pathName);
                    OutputStream output=null;
                    FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory(), "test.midi"));
                    if(file.exists()){

                        System.out.println("exits");

                        return;

                    }else{

                        String dir=SDCard+"/"+path;

                        new File(dir).mkdir();//新建文件夾

                        file.createNewFile();//新建文件

                        output=new FileOutputStream(file);

//讀取大文件

                        byte[] buffer=new byte[4*1024];

                        while(input.read(buffer)!=-1){

                            output.write(buffer);

                        }

                        output.flush();

                    }


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<List<Tune>> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });


    }

}