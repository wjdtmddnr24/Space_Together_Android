package com.example.spacetogether.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacetogether.R;
import com.example.spacetogether.adpater.FriendsAdapter;
import com.example.spacetogether.data.User;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_friends, container, false);
        FriendsAdapter adapter = new FriendsAdapter();
        adapter.addUser(new User("김태현", ""));
        adapter.addUser(new User("이규원", ""));
        adapter.addUser(new User("조혜은", ""));

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }
}
