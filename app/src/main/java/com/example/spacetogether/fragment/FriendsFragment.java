package com.example.spacetogether.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.spacetogether.R;
import com.example.spacetogether.activity.MainActivity;
import com.example.spacetogether.adpater.FriendsAdapter;
import com.example.spacetogether.data.Result;
import com.example.spacetogether.data.User;
import com.example.spacetogether.util.PreferenceManager;
import com.example.spacetogether.util.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {

    private FriendsAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_friends, container, false);
        if (MainActivity.app_user.getFriendsUser() == null) {
            RetrofitClient.getOdysseyService().getUsers(PreferenceManager.getString(getContext(), "token"), MainActivity.app_user.getFriends()).enqueue(new Callback<Result<List<User>>>() {
                @Override
                public void onResponse(Call<Result<List<User>>> call, Response<Result<List<User>>> response) {
                    MainActivity.app_user.setFriendsUser(response.body().data);
                    // TODO 다른것만 차이 -> 갱신
                    for (User user : MainActivity.app_user.getFriendsUser()) {
                        adapter.addUser(user);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<Result<List<User>>> call, Throwable t) {

                }
            });
        }


        adapter = new FriendsAdapter();
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }
}
