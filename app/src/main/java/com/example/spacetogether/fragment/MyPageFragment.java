package com.example.spacetogether.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.spacetogether.R;
import com.example.spacetogether.util.PreferenceManager;

public class MyPageFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mypage, container, false);

        TextView textView = root.findViewById(R.id.username);
        textView.setText(PreferenceManager.getString(getContext(), "token"));
        return root;
    }
}
