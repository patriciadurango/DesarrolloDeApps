package com.example.rickymorty.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rickymorty.R;
import com.example.rickymorty.models.Episode;
import com.example.rickymorty.network.ApiService;
import com.example.rickymorty.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EpisodeDetailFragment extends Fragment {

    private TextView nameTextView, airDateTextView, codeTextView;
    private int episodeId = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            episodeId = getArguments().getInt("episode_id", -1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_episode_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameTextView = view.findViewById(R.id.textViewEpisodeName);
        airDateTextView = view.findViewById(R.id.textViewAirDate);
        codeTextView = view.findViewById(R.id.textViewEpisodeCode);

        if (episodeId != -1) {
            fetchEpisodeDetails(episodeId);
        }
    }

    private void fetchEpisodeDetails(int id) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<Episode> call = apiService.getEpisodeDetails(id);

        call.enqueue(new Callback<Episode>() {
            @Override
            public void onResponse(Call<Episode> call, Response<Episode> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Episode episode = response.body();

                    nameTextView.setText(episode.getName());
                    airDateTextView.setText("Fecha de Emisión: " + episode.getAir_date());
                    codeTextView.setText("Código: " + episode.getEpisode());
                } else {
                    Toast.makeText(getContext(), "Error al cargar detalles", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Episode> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}