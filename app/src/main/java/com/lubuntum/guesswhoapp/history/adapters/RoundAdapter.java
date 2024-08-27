package com.lubuntum.guesswhoapp.history.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.lubuntum.guesswhoapp.R;
import com.lubuntum.guesswhoapp.history.entity.Round;

import java.text.DateFormat;
import java.util.List;

public class RoundAdapter extends RecyclerView.Adapter<RoundAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Round> roundList;
    private final int layout = R.layout.round_item;

    public RoundAdapter(Context context, List<Round> roundList){
        this.inflater = LayoutInflater.from(context);
        this.roundList = roundList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(layout, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Round round = roundList.get(position);
        holder.characterInfo.setText(round.getCard().toString());
        holder.date.setText(round.getDate());
        if(round.isWin()) {
            holder.characterInfo.setTextColor(inflater.getContext().getColor(com.beardedhen.androidbootstrap.R.color.bootstrap_brand_success));
        }
        else holder.characterInfo.setTextColor(inflater.getContext().getColor(com.beardedhen.androidbootstrap.R.color.bootstrap_brand_danger));
    }

    @Override
    public int getItemCount() {
        return roundList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView characterInfo;
        final TextView date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.characterInfo = itemView.findViewById(R.id.character_info);
            this.date = itemView.findViewById(R.id.date);
        }
    }
}
