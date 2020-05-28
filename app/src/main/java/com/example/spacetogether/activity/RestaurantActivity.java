package com.example.spacetogether.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.spacetogether.R;
import com.example.spacetogether.adapter.RestaurantAdapter;
import com.example.spacetogether.data.Restaurant;
import com.example.spacetogether.data.Result;
import com.example.spacetogether.data.User;
import com.example.spacetogether.util.PreferenceManager;
import com.example.spacetogether.util.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        Intent intent = getIntent();
        List<User> users = (List<User>) intent.getSerializableExtra("users");
        if (users == null) {
            users = new ArrayList<>();
        }

        RetrofitClient.getOdysseyService().recommendRestaurant(PreferenceManager.getString(this, "token"), users).enqueue(new Callback<Result<List<Restaurant>>>() {
            @Override
            public void onResponse(Call<Result<List<Restaurant>>> call, Response<Result<List<Restaurant>>> response) {
                Result<List<Restaurant>> result = response.body();
                if (result.data != null) {
                    for (Restaurant restaurant : result.data) {
                        adapter.addRestaurant(restaurant);
                    }
                    adapter.notifyDataSetChanged();
                }
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Result<List<Restaurant>>> call, Throwable t) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        recyclerView = findViewById(R.id.restaurant_recyclerview);
        adapter = new RestaurantAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        dialog = ProgressDialog.show(this, "추천 음식점을 가져오는 중입니다.", "잠시만 기다려주세요...");

    }
}
