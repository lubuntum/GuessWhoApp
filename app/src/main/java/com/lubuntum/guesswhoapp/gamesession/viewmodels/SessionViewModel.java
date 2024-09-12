package com.lubuntum.guesswhoapp.gamesession.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
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
    public SessionViewModel(@NonNull Application application) {
        super(application);
        firebase = FirebaseDatabase.getInstance("https://guesswhoapp-2aa3d-default-rtdb.europe-west1.firebasedatabase.app/");
    }
    public void createSession(){
        List<User> users = new LinkedList<>();
        //Брать из локалки данные хоста (юзера)
        users.add(new User("TestHostName", new Card("TestCardName", "TestPresent", "123fse21d")));
        session = new Session(SessionKeyGenerator.generateStr(7), users);
    }
    public void saveSession(){
        if(session == null) return;
        firebase.getReference(SESSIONS_PATH).child(session.key).setValue(session)
                .addOnFailureListener(e -> Log.d("Firebase Error", e.getMessage()))
                .addOnCompleteListener(task -> Log.d("Firebase Complete", task.toString()));
    }
}
