package com.lubuntum.guesswhoapp.gamesession.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lubuntum.guesswhoapp.cards.Card;
import com.lubuntum.guesswhoapp.gamesession.entity.Session;
import com.lubuntum.guesswhoapp.gamesession.entity.User;
import com.lubuntum.guesswhoapp.utils.SessionKeyGenerator;

import java.util.LinkedList;
import java.util.List;

public class SessionViewModel extends AndroidViewModel {
    private static final String SESSIONS_PATH = "sessions";
    private static final String USERS_PATH = "users";
    private FirebaseDatabase firebase;
    public Session session;
    public MutableLiveData<Boolean> sessionInProgress = new MutableLiveData<>();
    public MutableLiveData<String> statusMessage = new MutableLiveData<>();
    public SessionViewModel(@NonNull Application application) {
        super(application);
        firebase = FirebaseDatabase.getInstance("https://guesswhoapp-2aa3d-default-rtdb.europe-west1.firebasedatabase.app/");
    }
    public void createSession(){
        List<User> users = new LinkedList<>();
        //Брать из локалки данные хоста (юзера)
        users.add(new User("TestHostName", new Card("TestCardName", "TestPresent", "123fse21d")));
        session = new Session(SessionKeyGenerator.generateStr(12), users);
    }
    public void saveSession(){
        if(session == null) return;
        firebase.getReference(SESSIONS_PATH).child(session.key).setValue(session)
                .addOnSuccessListener(unused -> {
                    sessionInProgress.postValue(true);
                })
                .addOnFailureListener(e -> Log.d("Firebase Error", e.getMessage()));
    }
    public void connectToSession(String key){
        firebase.getReference(SESSIONS_PATH).child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    statusMessage.postValue("Сессии не существует");
                    return;
                }
                session = snapshot.getValue(Session.class);
                //Нужно в вессию добавить самого юзера и обновить ее на сервере
                //сейчас сессия просто загружается для пользователя но его в ней нет
                //не для него, не для создателя
                //TODO
                // Сделать хранение данных локального пользователя
                // После доделать подключение к сессии
                sessionInProgress.postValue(true);
                //Обсервить изменения
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
