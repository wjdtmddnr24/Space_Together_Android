package com.example.spacetogether.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.spacetogether.R;
import com.example.spacetogether.data.Result;
import com.example.spacetogether.data.User;
import com.example.spacetogether.util.OdysseyService;
import com.example.spacetogether.util.PreferenceManager;
import com.example.spacetogether.util.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static User app_user;

    public static void fetch_user(Runnable runnable, String token) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<Result<User>> user = RetrofitClient.getOdysseyService().getUser(token, token);
                user.enqueue(new Callback<Result<User>>() {
                    @Override
                    public void onResponse(Call<Result<User>> call, Response<Result<User>> response) {
                        MainActivity.app_user = response.body().data;
                        runnable.run();
                    }

                    @Override
                    public void onFailure(Call<Result<User>> call, Throwable t) {
                        runnable.run();
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        Toast.makeText(this, "환영합니다 " + PreferenceManager.getString(this, "token"), Toast.LENGTH_SHORT).show();

    }

}
