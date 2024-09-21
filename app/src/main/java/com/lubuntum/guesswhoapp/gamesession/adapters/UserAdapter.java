package com.lubuntum.guesswhoapp.gamesession.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
    private Drawable defaultCardImg;
    public UserAdapter(Context context, List<User> users){
        this.context = context;
        this.users = users;
        this.inflater = LayoutInflater.from(context);
        this.defaultCardImg = context.getDrawable(R.drawable.app_ic_2);
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
        holder.username.setText(user.getName());

        if (user.card != null && user.card.characterName != null
                && user.card.present != null && user.card.imageId != null) { //if (holder.cardImg.getDrawable() == null)
            holder.characterName.setText(user.card.getCharacterName());
            holder.present.setText(user.card.getPresent());
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
