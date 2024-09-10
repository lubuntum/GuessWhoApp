package com.lubuntum.guesswhoapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lubuntum.guesswhoapp.cards.Card;
import com.lubuntum.guesswhoapp.cards.CardLoader;
import com.lubuntum.guesswhoapp.databinding.FragmentCharacterCardBinding;
import com.lubuntum.guesswhoapp.gamesession.SessionFragment;
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

        expandBtn();
        historyBtn();
        showInstructionBtn();
        sessionBtn();
    }

    private void nextWinBtn(){
        binding.nextWinBtn.setOnClickListener(v1 -> {
            if (CardLoader.currentCard != null) {
                HistoryStorage.addRound(new Round(CardLoader.currentCard, DateUtil.getCurrentDate(), true));
            }
            binding.guessBlank.setText("");
            if(binding.appIcon.getVisibility() == View.VISIBLE)
                binding.appIcon.setVisibility(View.GONE);
            getNextCard();
        });
    }

    private void nextLoseBtn(){
        binding.nextLoseBtn.setOnClickListener(v -> {
            if (CardLoader.currentCard != null) {
                HistoryStorage.addRound(new Round(CardLoader.currentCard, DateUtil.getCurrentDate(), false));
            }
            if(binding.appIcon.getVisibility() == View.VISIBLE)
                binding.appIcon.setVisibility(View.GONE);
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

    private void sessionBtn(){
        binding.sessionGame.setOnClickListener(view -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.main_content, new SessionFragment(), "session_fragment")
                    .addToBackStack(null)
                    .commit();
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
            builder.setIcon(R.drawable.app_ic_2);
            builder.setView(roundsRecyclerView);
            builder.setCancelable(true);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }

    private void showInstructionBtn(){
        binding.instructionBtn.setOnClickListener(v -> {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.incstuction_card));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, // Width
                    LinearLayout.LayoutParams.WRAP_CONTENT  // Height
            );
            imageView.setLayoutParams(params);
            imageView.setAdjustViewBounds(true); // Maintain aspect ratio
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0,
                    (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()),
                    0,
                    0);

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Инструкция");
            builder.setView(imageView);
            builder.setCancelable(true);
            builder.setIcon(R.drawable.app_ic_2);

            AlertDialog dialog = builder.create();

            dialog.show();
        });
    }

    private void expandBtn(){
        binding.expandBtn.setOnClickListener(v -> {
            if(binding.instructionBtn.getVisibility() == View.GONE){
                binding.expandBtn.setImageDrawable(
                        AppCompatResources.getDrawable(
                                getContext(),
                                R.drawable.baseline_keyboard_arrow_right_24));
                binding.instructionBtn.setVisibility(View.VISIBLE);
                binding.showHistoryBtn.setVisibility(View.VISIBLE);
                binding.sessionGame.setVisibility(View.VISIBLE);
                return;
            }
            binding.expandBtn.setImageDrawable(
                    AppCompatResources.getDrawable(
                            getContext(),
                            R.drawable.baseline_keyboard_arrow_left_24));
            binding.instructionBtn.setVisibility(View.GONE);
            binding.showHistoryBtn.setVisibility(View.GONE);
            binding.sessionGame.setVisibility(View.GONE);
        });
    }

    private void getNextCard(){
        //Если карточки уже загружены, но они пустые (дошли до конца игры) то обновить список
        if(CardLoader.cardsList != null && CardLoader.cardsList.isEmpty() && CardLoader.originalList != null)
            for(Card card: CardLoader.originalList) CardLoader.cardsList.add(card.copy());
        if(CardLoader.cardsList == null || CardLoader.cardsList.size() == 0) {
            Toast.makeText(getContext(), "Возможно карточки еще загружаются...", Toast.LENGTH_SHORT).show();
            return;
        }

        Random random = new Random();

        Card nextCard = CardLoader.cardsList.get(random.nextInt(CardLoader.cardsList.size()));
        CardLoader.cardsList.remove(nextCard);
        CardLoader.currentCard = nextCard;

        binding.characterName.setText(CardLoader.currentCard.getCharacterName());
        binding.present.setText(CardLoader.currentCard.getPresent());
        CardLoader.loadCardImageById(getContext(), CardLoader.currentCard, binding.cardImg, binding.loadProgress);

    }

}