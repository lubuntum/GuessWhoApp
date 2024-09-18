package com.lubuntum.guesswhoapp.gamesession.entity;

import com.google.firebase.database.Exclude;
import com.lubuntum.guesswhoapp.cards.Card;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Session implements Serializable {
    @Exclude
    public String id;
    public String key;

    public List<User> getSessionUsers() {
        return sessionUsers;
    }

    public void setSessionUsers(List<User> sessionUsers) {
        this.sessionUsers = sessionUsers;
    }

    public List<User> sessionUsers;

    public Session(String id, String key, List<User> sessionUsers) {
        this.id = id;
        this.key = key;
        this.sessionUsers = sessionUsers;
    }

    public Session(String key, List<User> sessionUsers) {
        this.key = key;
        this.sessionUsers = sessionUsers;
    }
    public Session(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
