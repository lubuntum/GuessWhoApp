package com.lubuntum.guesswhoapp.gamesession.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lubuntum.guesswhoapp.R;
import com.lubuntum.guesswhoapp.cards.CardLoader;
import com.lubuntum.guesswhoapp.gamesession.entity.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
    private List<User> users;
    private LayoutInflater inflater;
    private int resource = R.layout.user_card_item;
    public UserAdapter(Context context, List<User> users){
        this.context = context;
        this.users = users;
        this.inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(resource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.characterName.setText(user.card.getCharacterName());
        holder.present.setText(user.card.getPresent());
        holder.username.setText(user.getName());
        if (holder.cardImg.getDrawable() == null) {
            CardLoader.loadCardImageById(
                    context,
                    user.getCard(),
                    holder.cardImg,
                    holder.progressBar);
            holder.appIcon.setVisibility(View.GONE);
        }

    }



    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView characterName;
        TextView present;

        ImageView cardImg;
        ImageView appIcon;
        TextView username;
        ProgressBar progressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.characterName = itemView.findViewById(R.id.character_name);
            this.present = itemView.findViewById(R.id.present);
            this.appIcon = itemView.findViewById(R.id.app_icon);
            this.cardImg = itemView.findViewById(R.id.card_img);
            this.username = itemView.findViewById(R.id.username);
            this.progressBar = itemView.findViewById(R.id.load_progress);
        }
    }
}
