package com.lubuntum.guesswhoapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lubuntum.guesswhoapp.cards.CardLoader;
import com.lubuntum.guesswhoapp.databinding.FragmentCharacterCardBinding;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CharacterCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharacterCardFragment extends Fragment {
    FragmentCharacterCardBinding binding;
    private static int score = 0;
    public CharacterCardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCharacterCardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.nextImageBtn.setOnClickListener(v -> {
            getNextCard();
        });
        binding.addScoreBtn.setOnClickListener(v -> {
            score++;
            binding.addScoreBtn.setText(String.format(getString(R.string.score), score));
        });
        binding.addScoreBtn.setText(String.format(getString(R.string.score), score));
    }

    private void getNextCard(){
        if(CardLoader.cardsList == null || CardLoader.cardsList.size() == 0) {
            Toast.makeText(getContext(), "Возможно карточки еще загружаются...", Toast.LENGTH_SHORT).show();
            return;
        }

        Random random = new Random();
        CardLoader.currentCard = CardLoader.cardsList.get(random.nextInt(CardLoader.cardsList.size()));

        binding.characterName.setText(CardLoader.currentCard.getCharacterName());
        binding.present.setText(CardLoader.currentCard.getPresent());
        CardLoader.loadCardImageById(getContext(), CardLoader.currentCard, binding.cardImg, binding.loadProgress);

    }

}