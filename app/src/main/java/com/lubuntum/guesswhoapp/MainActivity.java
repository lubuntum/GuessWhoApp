package com.lubuntum.guesswhoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lubuntum.guesswhoapp.cards.CardLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCardData();
    }

    private void initCardData(){
        Runnable loadCardRnb = () -> {
            CardLoader.loadCardsData(getApplicationContext());
        };
        new Thread(loadCardRnb).start();
    }
}