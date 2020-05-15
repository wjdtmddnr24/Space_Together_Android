package com.example.spacetogether.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.spacetogether.R;
import com.example.spacetogether.util.PreferenceManager;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    Intent intent;
                    String token = PreferenceManager.getString(SplashActivity.this, "token");
                    if (token != null && !token.isEmpty())
                        intent = new Intent(SplashActivity.this, MainActivity.class);
                    else
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
//                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
