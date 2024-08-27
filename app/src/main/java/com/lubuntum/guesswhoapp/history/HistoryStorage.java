package com.lubuntum.guesswhoapp.history;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lubuntum.guesswhoapp.history.entity.History;
import com.lubuntum.guesswhoapp.history.entity.Round;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class HistoryStorage {
    public static final String HISTORY_STORAGE_NAME = "history";
    public static final String HISTORY_ROUNDS_NAME = "history_rounds";
    public static SharedPreferences preferences;
    public static Gson gson = new Gson();
    public static Type roundListType = new TypeToken<List<Round>>(){}.getType();

    public static void initHistoryStorage(Context context){
        preferences = context.getSharedPreferences(HISTORY_STORAGE_NAME, Context.MODE_PRIVATE );
    }
    public static boolean addRounds(List<Round> roundList){
        if (roundList.isEmpty()) return false;

        History history = getCurrentHistory();
        history.getRoundList().addAll(roundList);
        updateCurrentHistory(history);
        return true;
    }
    public static boolean addRound(Round round){
        if (round == null) return false;
        History history = getCurrentHistory();
        history.getRoundList().add(round);
        updateCurrentHistory(history);
        return true;
    }
    public static History getHistory(){
        History history = getCurrentHistory();
        if (history.getRoundList() == null)
            history.setRoundList(new LinkedList<>());
        return history;
    }

    private static History getCurrentHistory(){
        History history =  new History(gson.fromJson(preferences.getString(HISTORY_ROUNDS_NAME, ""), roundListType));
        if (history.getRoundList() == null) history.setRoundList(new LinkedList<>());
        return history;
    }
    private static void updateCurrentHistory(History history){
        preferences.edit()
                .putString(HISTORY_ROUNDS_NAME, gson.toJson(history.getRoundList()))
                .apply();
    }

}
