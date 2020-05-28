package com.example.spacetogether.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.spacetogether.R;
import com.example.spacetogether.activity.MainActivity;
import com.example.spacetogether.activity.RestaurantActivity;
import com.example.spacetogether.adapter.AvailableFriendsAdapter;
import com.example.spacetogether.data.Restaurant;
import com.example.spacetogether.data.User;
import com.example.spacetogether.util.OdysseyService;
import com.example.spacetogether.util.PreferenceManager;
import com.example.spacetogether.util.RetrofitClient;

import java.nio.file.Path;

public class HomeFragment extends Fragment {

    private OdysseyService service;
    private RecyclerView availableFriendRecyclerView;
    private AvailableFriendsAdapter availableFriendsAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        availableFriendRecyclerView = root.findViewById(R.id.available_friends_recyclerview);
        availableFriendsAdapter = new AvailableFriendsAdapter(MainActivity.app_user.getFriendsUser());
        availableFriendRecyclerView.setAdapter(availableFriendsAdapter);
        availableFriendRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        root.findViewById(R.id.createMeetingBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RestaurantActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}
