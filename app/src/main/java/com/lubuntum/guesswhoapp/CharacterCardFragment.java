package com.lubuntum.guesswhoapp;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.lubuntum.guesswhoapp.cards.CardLoader;
import com.lubuntum.guesswhoapp.databinding.FragmentCharacterCardBinding;
import com.lubuntum.guesswhoapp.history.HistoryStorage;
import com.lubuntum.guesswhoapp.history.adapters.RoundAdapter;
import com.lubuntum.guesswhoapp.history.entity.History;
import com.lubuntum.guesswhoapp.history.entity.Round;
import com.lubuntum.guesswhoapp.utils.DateUtil;

import java.util.Random;
public class CharacterCardFragment extends Fragment {
    FragmentCharacterCardBinding binding;
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
        nextWinBtn();
        nextLoseBtn();

        showNotesBtn();
        historyBtn();
    }

    private void nextWinBtn(){
        binding.nextWinBtn.setOnClickListener(v1 -> {
            if (CardLoader.currentCard != null) {
                HistoryStorage.addRound(new Round(CardLoader.currentCard, DateUtil.getCurrentDate(), true));
            }
            binding.guessBlank.setText("");
            getNextCard();
        });
    }

    private void nextLoseBtn(){
        binding.nextLoseBtn.setOnClickListener(v -> {
            if (CardLoader.currentCard != null) {
                HistoryStorage.addRound(new Round(CardLoader.currentCard, DateUtil.getCurrentDate(), false));
            }
            binding.guessBlank.setText("");
            getNextCard();
        });
    }

    private void showNotesBtn(){
        binding.showListBtn.setOnClickListener(v -> {
            if(binding.card.getVisibility() == View.VISIBLE){
                binding.card.setVisibility(View.GONE);
                binding.emptyCard.setVisibility(View.VISIBLE);
                binding.showListBtn.setImageDrawable(
                        AppCompatResources.getDrawable(
                                getContext(),
                                R.drawable.baseline_visibility_24));
            } else {
                binding.card.setVisibility(View.VISIBLE);
                binding.emptyCard.setVisibility(View.GONE);
                binding.showListBtn.setImageDrawable(
                        AppCompatResources.getDrawable(
                                getContext(),
                                R.drawable.baseline_visibility_off_24));
            }
        });
    }

    private void historyBtn(){
        binding.showHistoryBtn.setOnClickListener(v -> {
            History history = HistoryStorage.getHistory();
            if (history.getRoundList() == null || history.getRoundList().isEmpty()) {
                Toast.makeText(getContext(), "Ошибка при загрузки истории", Toast.LENGTH_SHORT).show();
                return;
            }
            RoundAdapter adapter = new RoundAdapter(getContext(), history.getRoundList());

            RecyclerView roundsRecyclerView = new RecyclerView(getContext());
            roundsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            roundsRecyclerView.setAdapter(adapter);

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Ваша история");
            builder.setView(roundsRecyclerView);
            builder.setCancelable(true);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();;
        });

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