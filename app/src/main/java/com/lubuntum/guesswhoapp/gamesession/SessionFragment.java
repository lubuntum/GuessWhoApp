package com.lubuntum.guesswhoapp.gamesession;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lubuntum.guesswhoapp.R;
import com.lubuntum.guesswhoapp.databinding.SessionFragmentBinding;
import com.lubuntum.guesswhoapp.gamesession.adapters.UserAdapter;
import com.lubuntum.guesswhoapp.gamesession.entity.User;
import com.lubuntum.guesswhoapp.gamesession.viewmodels.SessionViewModel;
import com.lubuntum.guesswhoapp.utils.CopyBuffer;

import java.util.List;

public class SessionFragment extends Fragment {
    SessionFragmentBinding binding;
    SessionViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SessionFragmentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(SessionViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Preview window btns
        createSessionBtnInit();
        connectToSessionBtnInit();
        //Session header btns
        copyDataToBufferBtnInit();
        exitFromSessionBtnInit();
        //observers
        sessionInProgressObserverInit();
        showStatusMessageObserver();
    }
    private void userListInit(List<User> users){
        //Взять из локальнрго хран. только владельца и запихать как игрока.
        //Затем те кто подсоединяется добавяться автоматически обсервером и realTimeDB
        //List<User> users = User.generateTestUsers(2);
        UserAdapter adapter = new UserAdapter(getContext(), users);
        binding.users.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.users.setAdapter(adapter);
    }
    private void createSessionBtnInit(){
        binding.createSession.setOnClickListener((v)->{
            viewModel.createSession();
            //Поскольку хост, кнопка старт активна
            binding.startGameBtn.setFocusable(true);
            ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getContext(), com.beardedhen.androidbootstrap.R.color.bootstrap_brand_success));
            binding.startGameBtn.setImageTintList(colorStateList);
            viewModel.saveSession();
        });
    }
    private void connectToSessionBtnInit(){
        binding.connectToSession.setOnClickListener(view -> {
            String sessionKey = binding.sessionKeyEdit.getText().toString();
            if (sessionKey.isEmpty()){
                viewModel.statusMessage.setValue("Введите ключ");
                return;
            }
            viewModel.connectToSession(sessionKey);
        });
    }
    private void copyDataToBufferBtnInit(){
        binding.copyKeyBtn.setOnClickListener(view -> {
            if (viewModel.session.key.isEmpty()) {
                Toast.makeText(getContext(), "Ошибка ключ не найден", Toast.LENGTH_SHORT).show();
                return;
            }
            CopyBuffer.addTextToBuffer(getContext(), viewModel.session.key);
        });
    }
    private void exitFromSessionBtnInit(){
        binding.exitSession.setOnClickListener(view -> {
            viewModel.sessionInProgress.setValue(false);
        });
    }
    //Observers
    private void sessionInProgressObserverInit(){
        viewModel.sessionInProgress.observe(getViewLifecycleOwner(), isInProgress -> {
            if (!isInProgress){
                Toast.makeText(getContext(), "Игра окончена", Toast.LENGTH_SHORT).show();
                binding.sessionDialog.setVisibility(View.VISIBLE);
                binding.sessionInfoContainer.setVisibility(View.GONE);
                binding.users.setVisibility(View.GONE);
                viewModel.session = null;
                binding.startGameBtn.setFocusable(false);
                ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(getContext(), com.beardedhen.androidbootstrap.R.color.bootstrap_gray_light));
                binding.startGameBtn.setImageTintList(colorStateList);
                return;
            }
            binding.sessionDialog.setVisibility(View.GONE);
            binding.sessionInfoContainer.setVisibility(View.VISIBLE);
            binding.users.setVisibility(View.VISIBLE);
            userListInit(viewModel.session.sessionUsers);//Сюда передать users из viewmodel
            binding.usersCount.setText(String.valueOf(viewModel.session.sessionUsers.size()));
            binding.sessionKeyText.setText(String.format(getString(R.string.session_key_res), viewModel.session.key));
        });
    }
    //status observer
    private void showStatusMessageObserver(){
        viewModel.statusMessage.observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });
    }
}
