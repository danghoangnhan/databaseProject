package com.naman14.timber.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.naman14.timber.R;
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
    }
    private void addControl() {
        rname = findViewById(R.id.editTextTextPersonName3);
        rpassword = findViewById(R.id.editTextTextPersonName4);
        rregister = findViewById(R.id.button2);
        checkpassword=findViewById(R.id.editTextTextPersonName5);
    }
    private void onclick(){
        rregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if(validate()==false){
                        Toast toast=Toast.makeText(RegisterActivity.this,"the password is doesn't match",Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://140.136.151.130/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    JsonApi Jsonapi = retrofit.create(JsonApi.class);
                    Call<authentication> placeHolderApis = Jsonapi.register(rname.getText().toString(),rpassword.getText().toString());
                    placeHolderApis.enqueue(new Callback<authentication>() {
                        @Override
                        public void onResponse(@NonNull Call<authentication> call, @NonNull Response<authentication> response) {
                            Toast toast=Toast.makeText(RegisterActivity.this,response.message().toString(),Toast.LENGTH_SHORT);
                            toast.show();
                            if (response.isSuccessful()) {

                                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }
                        @Override
                        public void onFailure(@NonNull Call<authentication> call, @NonNull Throwable t) {
                            Toast toast=Toast.makeText(RegisterActivity.this,"login failed",Toast.LENGTH_SHORT);
                            toast.show();
                            t.printStackTrace();
                        }
                    });
                }
                catch (Exception e){
                    Toast toast=Toast.makeText(RegisterActivity.this,e.toString(),Toast.LENGTH_SHORT);
                    toast.show();
                }
                setResult(Activity.RESULT_OK);
            }
        });}
        public boolean validate(){
        return rpassword.equals(checkpassword);
    }

}