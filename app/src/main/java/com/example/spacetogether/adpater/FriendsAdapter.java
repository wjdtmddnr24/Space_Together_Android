package com.example.spacetogether.adpater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spacetogether.R;
import com.example.spacetogether.data.User;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    List<User> userList;

    public FriendsAdapter() {
        this.userList = new ArrayList<>();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User curUser = userList.get(position);
        holder.textView.setText(curUser.getUseName());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void addUser(User user) {
        userList.add(user);
        notifyDataSetChanged();
    }

    public User getUser(int position) {
        if (position > userList.size())
            return null;
        return userList.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_friend_name);
        }
    }
}
