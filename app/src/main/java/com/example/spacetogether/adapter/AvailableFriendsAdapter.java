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
import com.example.spacetogether.activity.MainActivity;
import com.example.spacetogether.data.Lecture;
import com.example.spacetogether.data.Schedule;
import com.example.spacetogether.data.User;
import com.example.spacetogether.fragment.FriendsFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AvailableFriendsAdapter extends RecyclerView.Adapter<AvailableFriendsAdapter.ViewHolder> {
    List<AvailableUser> availableUserList;
    private final Date current;


    public AvailableFriendsAdapter(List<User> userList) {
        this.current = new Date();
        this.availableUserList = new ArrayList<>();
        List<AvailableUser> tmp = new ArrayList<>();
        if (userList != null) {
            for (User u : userList) {
                AvailableUser availableUser = new AvailableUser(u);
                if (isAvailable(availableUser)) {
                    availableUserList.add(availableUser);
                } else {
                    tmp.add(availableUser);
                }
            }
        }
        for (AvailableUser availableUser : tmp)
            availableUserList.add(availableUser);
    }

    public boolean isAvailable(AvailableUser user) {
        for (Lecture lecture : user.user.getTimetable()) {
            for (Schedule schedule : lecture.getSchedule()) {
                if (current.getDay() == schedule.getStartDate().getDay() && current.after(schedule.getStartDate()) && current.before(schedule.getEndDate())) {
                    user.interval = new Date();
                    int h = schedule.getEndDate().getHours() - current.getHours();
                    int m = schedule.getEndDate().getMinutes() - current.getMinutes();
                    if (m < 0) {
                        m += 60;
                        h--;
                    }
                    user.interval.setHours(h);
                    user.interval.setMinutes(m);
                    return false;
                }
            }
        }
        return true;
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
        if (curUser.interval == null) {
            holder.availableTimeTextView.setText("현재 식사 가능");
        } else {
            holder.availableTimeTextView.setText(String.format("%d시간 %d분 후 식사 가능", curUser.interval.getHours(), curUser.interval.getMinutes()));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.availableFriendCheckBox.toggle();
            }
        });
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
        Date interval = null;

        public AvailableUser(User user) {
            this.user = user;
        }
    }

    public ArrayList<User> getSelectedUser() {
        ArrayList<User> ret = new ArrayList<>();
        ret.add(MainActivity.app_user);
        for (AvailableUser availableUser : availableUserList) {
            if (availableUser.isChecked) {
                ret.add(availableUser.user);
            }
        }
        return ret;
    }
}
