package com.lubuntum.guesswhoapp.auth;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lubuntum.guesswhoapp.databinding.AuthDialogBinding;
import com.lubuntum.guesswhoapp.gamesession.entity.User;
import com.lubuntum.guesswhoapp.gamesession.viewmodels.SessionViewModel;
import com.lubuntum.guesswhoapp.utils.DialogAdapt;

public class AuthDialog extends DialogFragment {
    AuthDialogBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AuthDialogBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DialogAdapt.adaptByScreenSize(getDialog().getWindow(), getContext(), 0.9);
        authBtnInit();
    }
    private void authBtnInit(){
        binding.authUserBtn.setOnClickListener(view -> {
            binding.loadProgress.setVisibility(View.VISIBLE);
            saveAuthUser();
        });
    }
    private void saveAuthUser(){
        FirebaseDatabase firebase = FirebaseDatabase.getInstance(SessionViewModel.FIREBASE_PATH);
        DatabaseReference userRef = firebase.getReference(SessionViewModel.USERS_PATH);
        User user;
        if (AuthStorage.isUserExists(getContext())){
            user = AuthStorage.getCurrentUser(getContext());
        }
        else{
            user = new User();
            user.id = userRef.push().getKey();
        }
        user.name = binding.username.getText().toString();
        userRef.child(user.id).setValue(user)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(
                            getContext(),
                            "Регистрация успешна",
                            Toast.LENGTH_SHORT).show();
                    binding.loadProgress.setVisibility(View.GONE);
                    AuthStorage.saveCurrentUser(getContext(), user);
                    dismiss();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(),
                            "Неизвестная ошибка, попробуйте позже",
                            Toast.LENGTH_SHORT).show();
                });
    }
}
