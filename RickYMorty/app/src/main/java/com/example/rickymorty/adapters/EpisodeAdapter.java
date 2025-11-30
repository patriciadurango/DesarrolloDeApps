package com.example.rickymorty.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rickymorty.EpisodeClickListener;
import com.example.rickymorty.R;
import com.example.rickymorty.models.Episode;

import java.util.ArrayList;
import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder> {

    private List<Episode> episodeList;
    private final EpisodeClickListener clickListener;

    public EpisodeAdapter(EpisodeClickListener clickListener) {
        this.episodeList = new ArrayList<>();
        this.clickListener = clickListener;
    }

    public void setEpisodeList(List<Episode> episodes) {
        this.episodeList = episodes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_episode, parent, false);
        return new EpisodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        Episode episode = episodeList.get(position);

        holder.nameTextView.setText(episode.getName());
        holder.codeTextView.setText(episode.getEpisode());

        holder.itemView.setOnClickListener(v -> {
            clickListener.onEpisodeClicked(episode.getId());
        });
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    static class EpisodeViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView codeTextView;

        EpisodeViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewEpisodeName);
            codeTextView = itemView.findViewById(R.id.textViewEpisodeCode);
        }
    }
}