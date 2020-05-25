package com.example.spacetogether.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spacetogether.R;
import com.example.spacetogether.data.Result;
import com.example.spacetogether.data.User;
import com.example.spacetogether.fragment.FriendsFragment;
import com.example.spacetogether.util.PreferenceManager;
import com.example.spacetogether.util.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    List<User> userList;
    boolean isRequestFriend;
    FriendsFragment friendsFragment;

    public FriendsAdapter(List<User> userList, boolean isRequestFriend, FriendsFragment friendsFragment) {
        if (userList == null) {
            this.userList = new ArrayList<>();
        } else {
            this.userList = userList;
        }
        this.friendsFragment = friendsFragment;
        this.isRequestFriend = isRequestFriend;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User curUser = userList.get(position);
        if (isRequestFriend) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(friendsFragment.getContext())
                            .setMessage("친구 요청을 수락하시겠습니까?")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    friendsFragment.acceptFriend(curUser);
                                }
                            })
                            .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).create().show();
                }
            });
        }
        holder.textView.setText(curUser.getUsername());
        holder.idTextView.setText(curUser.getUserId());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void addUser(User user) {
        userList.add(user);
        notifyDataSetChanged();
    }

    public void setUsers(List<User> userList) {
        this.userList.clear();
        this.userList.addAll(userList);
    }

    public User getUser(int position) {
        if (position > userList.size())
            return null;
        return userList.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView idTextView;
        public View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            textView = itemView.findViewById(R.id.item_friend_name);
            idTextView = itemView.findViewById(R.id.item_friend_id);
        }
    }
}
