package com.lubuntum.guesswhoapp.history.entity;

import androidx.annotation.NonNull;

import com.lubuntum.guesswhoapp.cards.Card;

import java.io.Serializable;

public class Round implements Serializable {
    Card card;
    String date;
    boolean isWin = false;
    public Round(Card card, String date, boolean isWin){
        this.card = card;
        this.date = date;
        this.isWin = isWin;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }
}
