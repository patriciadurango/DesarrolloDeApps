package com.example.rickymorty.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.rickymorty.R;
import com.example.rickymorty.models.Character;
import com.example.rickymorty.network.ApiService;
import com.example.rickymorty.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterDetailFragment extends Fragment {

    private TextView nameTextView, statusTextView, speciesTextView, genderTextView;
    private ImageView imageView;
    private int characterId = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            characterId = getArguments().getInt("character_id", -1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_character_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.imageViewCharacter);
        nameTextView = view.findViewById(R.id.textViewName);
        statusTextView = view.findViewById(R.id.textViewStatus);
        speciesTextView = view.findViewById(R.id.textViewSpecies);
        genderTextView = view.findViewById(R.id.textViewGender);

        if (characterId != -1) {
            fetchCharacterDetails(characterId);
        }
    }

    private void fetchCharacterDetails(int id) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<Character> call = apiService.getCharacterDetails(id);

        call.enqueue(new Callback<Character>() {
            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Character character = response.body();

                    nameTextView.setText(character.getName());
                    statusTextView.setText("Estado: " + character.getStatus());
                    speciesTextView.setText("Especie: " + character.getSpecies());
                    genderTextView.setText("Género: " + character.getGender());

                    Glide.with(requireContext())
                            .load(character.getImage())
                            .into(imageView);
                } else {
                    Toast.makeText(getContext(), "Error al cargar detalles", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}