package com.lubuntum.guesswhoapp.gamesession.entity;

import com.google.firebase.database.Exclude;
import com.lubuntum.guesswhoapp.cards.Card;

import org.jetbrains.annotations.TestOnly;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class User implements Serializable {
    @Exclude
    public String id;
    public String name;
    public Card card;

    public User(String name, Card card){
        this.card = card;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
    @TestOnly
    public static List<User> generateTestUsers(int count){
        List<User> testUsers = new LinkedList<>();
        for(int i = 0; i < count; i++) {
            Card card = new Card("Character" + i, "Present in" + i, "10PAAUAZgsq_dvIOD2aIasqAAC8JjQRie");
            testUsers.add(new User("User" + i, card));
        }
        return testUsers;
    }
}
