package com.example.rickymorty.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rickymorty.CharacterClickListener;
import com.example.rickymorty.R;
import com.example.rickymorty.models.Character;

import java.util.ArrayList;
import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {

    private List<Character> characterList;
    private final CharacterClickListener clickListener;

    public CharacterAdapter(CharacterClickListener clickListener) {
        this.characterList = new ArrayList<>();
        this.clickListener = clickListener;
    }

    public void setCharacterList(List<Character> characters) {
        this.characterList = characters;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_character, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        Character character = characterList.get(position);

        holder.nameTextView.setText(character.getName());
        holder.speciesTextView.setText(character.getSpecies());

        // Cargar imagen con Glide
        Glide.with(holder.itemView.getContext())
                .load(character.getImage())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            clickListener.onCharacterClicked(character.getId());
        });
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    static class CharacterViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView speciesTextView;

        CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewCharacter);
            nameTextView = itemView.findViewById(R.id.textViewName);
            speciesTextView = itemView.findViewById(R.id.textViewSpecies);
        }
    }
}