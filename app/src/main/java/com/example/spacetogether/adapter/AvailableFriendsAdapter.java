package com.example.spacetogether.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.spacetogether.R;
import com.example.spacetogether.data.User;
import com.example.spacetogether.fragment.FriendsFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AvailableFriendsAdapter extends RecyclerView.Adapter<AvailableFriendsAdapter.ViewHolder> {
    List<AvailableUser> availableUserList;


    public AvailableFriendsAdapter(List<User> userList) {
        this.availableUserList = new ArrayList<>();
        if (userList != null) {
            for (User u : userList) {
                availableUserList.add(new AvailableUser(u));
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_available_friend, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AvailableUser curUser = availableUserList.get(position);

        holder.textView.setText(curUser.user.getUsername());
        holder.availableFriendCheckBox.setChecked(curUser.isChecked);
        holder.availableFriendCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                curUser.isChecked = isChecked;
            }
        });

    }

    @Override
    public int getItemCount() {
        return availableUserList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView availableTimeTextView;
        public CheckBox availableFriendCheckBox;
        public View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            textView = itemView.findViewById(R.id.item_available_friend_name);
            availableTimeTextView = itemView.findViewById(R.id.item_available_friend_time);
            availableFriendCheckBox = itemView.findViewById(R.id.item_available_friends_checkbox);
        }
    }

    public static class AvailableUser {
        User user;
        boolean isChecked = false;

        public AvailableUser(User user) {
            this.user = user;
        }
    }
}
