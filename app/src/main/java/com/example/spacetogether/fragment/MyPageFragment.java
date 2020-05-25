package com.example.spacetogether.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.spacetogether.R;
import com.example.spacetogether.activity.LoginActivity;
import com.example.spacetogether.activity.MainActivity;
import com.example.spacetogether.util.PreferenceManager;

public class MyPageFragment extends Fragment {

    private TextView username;
    private TextView userId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mypage, container, false);
        setHasOptionsMenu(true);
        username = root.findViewById(R.id.username);
        username.setText(MainActivity.app_user.getUsername());
        userId = root.findViewById(R.id.user_id);
        userId.setText("ID: " + MainActivity.app_user.getUserId());

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mypage_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout: {
                PreferenceManager.setString(getContext(), "token", "");
                MainActivity.app_user = null;
                getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
