package com.example.spacetogether.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        Intent intent = getIntent();
        users = (List<User>) intent.getSerializableExtra("users");
        LinearLayout linearLayout = findViewById(R.id.restaurant_friend_list);
        for (User user : users) {
            View v = getLayoutInflater().inflate(R.layout.item_user_icon, null);
            ((TextView) v.findViewById(R.id.item_user_name)).setText(user.getUsername());
            linearLayout.addView(v);
        }
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
        adapter = new RestaurantAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        dialog = ProgressDialog.show(this, "추천 음식점을 가져오는 중입니다.", "잠시만 기다려주세요...");

    }

    public void selectRestaurant(Restaurant restaurant) {
        new AlertDialog.Builder(this).setMessage(restaurant.getName() + "에서 식사를 하시겠습니까?").setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RetrofitClient.getOdysseyService().selectRestaurant(restaurant.get_id(), PreferenceManager.getString(RestaurantActivity.this, "token"), users).enqueue(new Callback<Result<String>>() {
                    @Override
                    public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                        if (response.body().result.equals("success")) {
                            Toast.makeText(RestaurantActivity.this, "맛있는 식사하세요!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result<String>> call, Throwable t) {

                    }
                });
            }
        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }
}
