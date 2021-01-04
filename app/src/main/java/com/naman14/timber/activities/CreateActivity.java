package com.naman14.timber.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.audio_mixer.AudioMixer;
import com.example.audio_mixer.input.AudioInput;
import com.example.audio_mixer.input.BlankAudioInput;
import com.example.audio_mixer.input.GeneralAudioInput;
import com.naman14.timber.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class CreateActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 0;
    private static final int AUDIO_CHOOSE_REQUEST_CODE = 1;
    private Activity activity;
    private String outputPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +"audio_mixer_output.mp3";
    private EditText Filename;
    private List<com.naman14.timber.activities.Input> inputs = new ArrayList<>();
    private AudioMixer audioMixer = null;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        activity = this;
        //Filename = findViewById(R.id.newSongName);
        findViewById(R.id.add_audio_btn).setOnClickListener(v -> openChooser());
        findViewById(R.id.mix_btn).setOnClickListener(v -> {
            if(inputs.size() < 1){
                Toast.makeText(activity, "Add at least one audio.", Toast.LENGTH_SHORT).show();
            }else{
                //outputPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +Filename.getText().toString()+".mp3";
                startMixing();
            }
        });
        findViewById(R.id.remove_audio_btn).setOnClickListener(v -> {
            if(inputs.size() > 0){
                inputs.remove(inputs.size()-1);
                Toast.makeText(activity, "Last audio removed. Number of inputs: "+inputs.size(), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(activity, "No audio added.", Toast.LENGTH_SHORT).show();
            }
        });
        checkPermission();
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void startMixing(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mixing audio...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setProgress(0);

        try{
            audioMixer = new AudioMixer(outputPath);

            for(Input input: inputs){
                AudioInput audioInput;
                if(input.uri != null){
                    GeneralAudioInput ai = new GeneralAudioInput(activity, input.uri, null);
                    ai.setStartOffsetUs(input.startOffsetUs);
                    ai.setStartTimeUs(input.startTimeUs);
                    ai.setEndTimeUs(input.endTimeUs);
                    audioInput = ai;
                }else{
                    audioInput = new BlankAudioInput(5000000);
                }
                audioMixer.addDataSource(audioInput);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        audioMixer.setMixingType(AudioMixer.MixingType.PARALLEL);
        audioMixer.setProcessingListener(new AudioMixer.ProcessingListener() {
            @Override
            public void onProgress(double progress) {
                runOnUiThread(() -> progressDialog.setProgress((int) (progress * 100)));
            }

            @Override
            public void onEnd() {
                runOnUiThread(() -> {
                    progressDialog.setProgress(1000);
                    progressDialog.dismiss();
                    Toast.makeText(activity, "Success!!! Ouput path: "+outputPath, Toast.LENGTH_LONG).show();
                });
            }
        });

        progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "End", (dialog, which) -> {
            audioMixer.stop();
            audioMixer.release();
        });

        try {
            audioMixer.start();
            audioMixer.processAsync();
            progressDialog.show();
        }catch (IOException e){
            audioMixer.release();
        }
    }

    public void openChooser(){
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, AUDIO_CHOOSE_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AUDIO_CHOOSE_REQUEST_CODE && resultCode == RESULT_OK){
            try{
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(activity, data.getData());
                String dur = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                long duration = Integer.parseInt(dur) * 1000; // milli to micro second
                retriever.release();

                Input input = new Input();
                input.uri = data.getData();
                input.durationUs = duration;
                inputs.add(input);
                Toast.makeText(activity, inputs.size()+" input(s) added.", Toast.LENGTH_SHORT).show();

                com.naman14.timber.activities.AudioInputSettingsDialog dialog = new com.naman14.timber.activities.AudioInputSettingsDialog(activity, input);
                dialog.setCancelable(false);
                dialog.show();

            }catch (Exception o){
                Toast.makeText(activity, "Input not added.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (    grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "Permission has been granted.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "[WARN] permission is not granted.", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}