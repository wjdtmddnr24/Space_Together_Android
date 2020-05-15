package com.example.spacetogether.activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.spacetogether.R;
import com.example.spacetogether.data.Result;
import com.example.spacetogether.util.OdysseyService;
import com.example.spacetogether.util.PreferenceManager;
import com.example.spacetogether.util.RetrofitInstance;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText id_edittext, pw_edittext;

    @Override
    protected void onResume() {
        super.onResume();
        if (!PreferenceManager.getString(this, "token").isEmpty()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        id_edittext = findViewById(R.id.login_id);
        pw_edittext = findViewById(R.id.login_pw);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = id_edittext.getText().toString();
                final String pw = pw_edittext.getText().toString();

                final OdysseyService service = RetrofitInstance.getInstance().create(OdysseyService.class);
                service.login(id, pw).enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        Result result = response.body();
                        if (result != null && result.data != null && !result.data.isEmpty()) {
                            PreferenceManager.setString(LoginActivity.this, "token", result.data);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "로그인에 실패하였습니다: " + result.data, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        findViewById(R.id.btn_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
