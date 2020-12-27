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

import com.naman14.timber.R;
import com.naman14.timber.Service.JsonApi;
import com.naman14.timber.models.authentication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {
    private static authentication userBio;
    private String TAG = LoginActivity.class.getSimpleName();
    private EditText ename;
    private EditText epassword;
    private Button elogin, eregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControl();
        addEvent();
    }

    private void addControl() {
        ename = findViewById(R.id.editTextTextPersonName);
        epassword = findViewById(R.id.editTextTextPersonName2);
        elogin = findViewById(R.id.button);
        eregister = findViewById(R.id.button3);

    }
    private void addEvent() {

        eregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // direct to register activity
                // Intent: jump screen type // (LoginActivity.this, (jump to) RegisterActivity.class)
                // direct to tmp activity
                System.out.println("111");
                Intent toRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                System.out.println("222");
                //Intent toRegister = new Intent(LoginActivity.this, TmpActivity.class);
                startActivity(toRegister);
                finish();
            }
        });


        elogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (epassword == null && ename == null)
                        return;

                    elogin.setEnabled(true);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://140.136.151.130/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    JsonApi Jsonapi = retrofit.create(JsonApi.class);
                    Call<authentication> placeHolderApis = Jsonapi.login(ename.getText().toString(), epassword.getText().toString());
                    placeHolderApis.enqueue(new Callback<authentication>() {
                        @Override
                        public void onResponse(@NonNull Call<authentication> call, @NonNull Response<authentication> response) {
                            if (response.isSuccessful()) {//issuccessful() = status(200)

                                Toast toast=Toast.makeText(LoginActivity.this,"welcome "+ename.getText().toString(),Toast.LENGTH_SHORT);
                                toast.show();
                                userBio = response.body();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }
                        @Override
                        public void onFailure(@NonNull Call<authentication> call, @NonNull Throwable t) {
                            Toast toast=Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_SHORT);
                            toast.show();
                            t.printStackTrace();
                        }
                    });

                }
                catch (Exception e){
                    Toast toast=Toast.makeText(LoginActivity.this,e.toString(),Toast.LENGTH_SHORT);
                    toast.show();
                }
                setResult(Activity.RESULT_OK);
            }
        });}
    public static authentication getUser(){
        return  userBio;
    }

}