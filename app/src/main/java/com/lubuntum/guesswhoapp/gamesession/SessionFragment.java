package com.lubuntum.guesswhoapp.gamesession;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lubuntum.guesswhoapp.databinding.SessionFragmentBinding;
import com.lubuntum.guesswhoapp.gamesession.adapters.UserAdapter;
import com.lubuntum.guesswhoapp.gamesession.entity.User;

import java.util.LinkedList;
import java.util.List;

public class SessionFragment extends Fragment {
    SessionFragmentBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SessionFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //userListInit();
    }
    private void userListInit(){
        List<User> users = User.generateTestUsers(2);
        UserAdapter adapter = new UserAdapter(getContext(), users);
        binding.users.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.users.setAdapter(adapter);
    }
}
