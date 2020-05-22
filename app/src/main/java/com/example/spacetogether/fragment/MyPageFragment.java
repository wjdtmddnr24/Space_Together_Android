package com.example.spacetogether.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.spacetogether.R;
import com.example.spacetogether.activity.MainActivity;

public class MyPageFragment extends Fragment {

    private TextView username;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mypage, container, false);
        username = root.findViewById(R.id.username);
        username.setText(MainActivity.app_user.getUsername());

        return root;
    }
}
