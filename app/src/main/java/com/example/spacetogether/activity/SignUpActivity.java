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

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText username_edittext, id_edittext, pw_edittext, pw2_edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        username_edittext = findViewById(R.id.signup_username);
        id_edittext = findViewById(R.id.signup_id);
        pw_edittext = findViewById(R.id.signup_pw);
        pw2_edittext = findViewById(R.id.signup_pw2);

        findViewById(R.id.signup_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = id_edittext.getText().toString();
                final String username = username_edittext.getText().toString();
                final String pw = pw_edittext.getText().toString();
                final String pw2 = pw2_edittext.getText().toString();
                if (!pw.equals(pw2)) {
                    Toast.makeText(SignUpActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                final OdysseyService service = RetrofitInstance.getInstance().create(OdysseyService.class);
                service.signup(id, pw, username).enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        Result result = response.body();
                        if (result != null && result.data != null && !result.data.isEmpty()) {
                            PreferenceManager.setString(SignUpActivity.this, "token", result.data);
                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, "회원가입에 실패하였습니다: " + result.data, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Toast.makeText(SignUpActivity.this, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
