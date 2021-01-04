package com.naman14.timber.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.naman14.timber.R;
import com.naman14.timber.Service.JsonApi;
import com.naman14.timber.models.authentication;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private String TAG = LoginActivity.class.getSimpleName();
    private EditText    rname;
    private EditText    rpassword;
    private EditText    checkpassword;
    private Button      rregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addControl();
        onclick();
    }
    private void addControl() {
        rname = findViewById(R.id.editTextTextPersonName3);
        rpassword = findViewById(R.id.editTextTextPersonName4);
        rregister = findViewById(R.id.button2);
        checkpassword=findViewById(R.id.editTextTextPersonName5);
    }
    private void onclick(){
        rregister.setOnClickListener(v -> {
            try {
                if(validate()==false){
                    Toast toast=Toast.makeText(RegisterActivity.this,"the password is doesn't match",Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                Map<String, String> build = new HashMap<>();
                build.put("account", rname.getText().toString());
                build.put("password", rpassword.getText().toString());
                JSONObject RegisterJson = new JSONObject(build);
                JsonParser jsonParser = new JsonParser();
                JsonObject ToJson = (JsonObject) jsonParser.parse(RegisterJson.toString());

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://140.136.151.130/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                JsonApi Jsonapi = retrofit.create(JsonApi.class);
                Call<authentication> placeHolderApis = Jsonapi.register(ToJson);// JsonApi.java register_body
                placeHolderApis.enqueue(new Callback<authentication>() {
                    @Override
                    public void onResponse(@NonNull Call<authentication> call, @NonNull Response<authentication> response) {
                        Toast.makeText(RegisterActivity.this, response.message(),Toast.LENGTH_SHORT).show();
                        if (response.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, response.message(),Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(i);
                            finish();
                        }
                        onFailure(call,new Exception("user already assigned"));
                    }
                    @Override
                    public void onFailure(@NonNull Call<authentication> call, @NonNull Throwable t) {
                        Toast.makeText(RegisterActivity.this,"login failed",Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
            }
            catch (Exception e){
                Toast toast=Toast.makeText(RegisterActivity.this,e.toString(),Toast.LENGTH_SHORT);
                toast.show();
            }
            setResult(Activity.RESULT_OK);
        });}
    public boolean validate(){
        return rpassword.getText().toString().equals(checkpassword.getText().toString());
    }

}