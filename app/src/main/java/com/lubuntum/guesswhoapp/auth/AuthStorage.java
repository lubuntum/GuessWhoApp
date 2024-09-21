package com.lubuntum.guesswhoapp.auth;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.lubuntum.guesswhoapp.gamesession.entity.User;

public class AuthStorage {
    private static final String AUTH_STORAGE_NAME = "auth_storage";
    private static final String USER_LOCAL_NAME = "user_local";
    private static SharedPreferences preferences;
    public static void initSharedPreferences(Context context){
        if (preferences == null)
            preferences = context.getSharedPreferences(AUTH_STORAGE_NAME, Context.MODE_PRIVATE);
    }

    public static User getCurrentUser(Context context) {
        initSharedPreferences(context);
        Gson gson = new Gson();
        return gson.fromJson(preferences.getString(USER_LOCAL_NAME, null), User.class);
    }

    public static void saveCurrentUser(Context context, User currentUser) {
        initSharedPreferences(context);
        Gson gson = new Gson();
        preferences.edit().putString(USER_LOCAL_NAME, gson.toJson(currentUser)).apply();
    }
    public static boolean isUserExists(Context context){
        initSharedPreferences(context);
        return preferences.contains(USER_LOCAL_NAME);
    }
}
