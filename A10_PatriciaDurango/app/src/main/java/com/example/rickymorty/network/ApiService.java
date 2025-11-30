package com.example.rickymorty.network;

import com.example.rickymorty.models.Character;
import com.example.rickymorty.models.CharacterResponse;
import com.example.rickymorty.models.Episode;
import com.example.rickymorty.models.EpisodeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    // --- PERSONAJES ---
    @GET("character")
    Call<CharacterResponse> getCharacters();

    @GET("character/{id}")
    Call<Character> getCharacterDetails(@Path("id") int characterId);

    // --- EPISODIOS ---
    @GET("episode")
    Call<EpisodeResponse> getEpisodes();

    @GET("episode/{id}")
    Call<Episode> getEpisodeDetails(@Path("id") int episodeId);
}