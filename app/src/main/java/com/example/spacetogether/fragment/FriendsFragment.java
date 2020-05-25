package com.example.spacetogether.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.spacetogether.R;
import com.example.spacetogether.activity.MainActivity;
import com.example.spacetogether.adapter.FriendsAdapter;
import com.example.spacetogether.data.Result;
import com.example.spacetogether.data.User;
import com.example.spacetogether.util.PreferenceManager;
import com.example.spacetogether.util.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class FriendsFragment extends Fragment {

    private FriendsAdapter friendsAdapter;
    private RecyclerView friendsRecyclerView;
    private RecyclerView requestFriendsRecyclerView;
    private FriendsAdapter requestFriendsAdapter;

    public void acceptFriend(User friend) {
        RetrofitClient.getOdysseyService().acceptFriend(friend.get_id(), PreferenceManager.getString(getContext(), "token")).enqueue(new Callback<Result<String>>() {
            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                if (response.body().result.equals("success")) {
                    fetchRequestFriendsUser();
                    fetchFriendsUser();
                }
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
                int a=0;
            }
        });
    }

    private void fetchFriendsUser() {
        RetrofitClient.getOdysseyService().getFriendsUsers(PreferenceManager.getString(getContext(), "token")).enqueue(new Callback<Result<List<User>>>() {
            @Override
            public void onResponse(Call<Result<List<User>>> call, Response<Result<List<User>>> response) {
                MainActivity.app_user.setFriendsUser(response.body().data);
                friendsAdapter.setUsers(MainActivity.app_user.getFriendsUser());
                friendsAdapter.notifyDataSetChanged();
                getFragmentManager().beginTransaction().detach(FriendsFragment.this).attach(FriendsFragment.this).commit();
            }

            @Override
            public void onFailure(Call<Result<List<User>>> call, Throwable t) {

            }
        });
    }

    private void fetchRequestFriendsUser() {
        RetrofitClient.getOdysseyService().getRequestFriendsUsers(PreferenceManager.getString(getContext(), "token")).enqueue(new Callback<Result<List<User>>>() {
            @Override
            public void onResponse(Call<Result<List<User>>> call, Response<Result<List<User>>> response) {
                MainActivity.app_user.setFriendsRequestUser(response.body().data);
                requestFriendsAdapter.setUsers(MainActivity.app_user.getFriendsRequestUser());
                requestFriendsAdapter.notifyDataSetChanged();
                getFragmentManager().beginTransaction().detach(FriendsFragment.this).attach(FriendsFragment.this).commit();
            }

            @Override
            public void onFailure(Call<Result<List<User>>> call, Throwable t) {

            }
        });

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_friends, container, false);

        friendsAdapter = new FriendsAdapter(MainActivity.app_user.getFriendsUser(), false, this);
        friendsRecyclerView = root.findViewById(R.id.friend_recyclerview);
        friendsRecyclerView.setAdapter(friendsAdapter);
        friendsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        requestFriendsAdapter = new FriendsAdapter(MainActivity.app_user.getFriendsRequestUser(), true, this);
        requestFriendsRecyclerView = root.findViewById(R.id.request_friend_recyclerview);
        requestFriendsRecyclerView.setAdapter(requestFriendsAdapter);
        requestFriendsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        root.findViewById(R.id.request_friend_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_request_friend);
                TextInputEditText editText = dialog.findViewById(R.id.dialog_request_friend_id_edittext);
                dialog.findViewById(R.id.dialog_request_friend_request).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userId = editText.getText().toString();
                        Call<Result<String>> request = RetrofitClient.getOdysseyService().requestFriend(userId, PreferenceManager.getString(getContext(), "token"));
                        request.enqueue(new Callback<Result<String>>() {
                            @Override
                            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                                if (response.body().result.equals("success")) {
                                    Toast.makeText(getContext(), "친구 요청을 하였습니다.", Toast.LENGTH_SHORT).show();
                                    fetchRequestFriendsUser();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(getContext(), response.body().data, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Result<String>> call, Throwable t) {

                            }
                        });
                    }
                });
                dialog.findViewById(R.id.dialog_request_friend_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing())
                            dialog.dismiss();
                    }
                });
                dialog.show();
            }

        });


        return root;
    }
}
