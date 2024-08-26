package com.lubuntum.guesswhoapp.history.entity;

import java.util.List;

public class History {
    List<Round> roundList;

    public History(List<Round> roundList) {
        this.roundList = roundList;
    }

    public List<Round> getRoundList() {
        return roundList;
    }

    public void setRoundList(List<Round> roundList) {
        this.roundList = roundList;
    }
}
