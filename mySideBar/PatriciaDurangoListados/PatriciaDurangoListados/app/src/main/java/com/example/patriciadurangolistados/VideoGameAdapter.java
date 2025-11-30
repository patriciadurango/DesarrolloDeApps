package com.example.patriciadurangolistados;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class VideoGameAdapter extends RecyclerView.Adapter<VideoGameAdapter.VideoGameViewHolder> {

    private List<VideoGame> videoGames;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(VideoGame videoGame, int position);
        void onDeleteClick(VideoGame videoGame, int position);
    }

    public VideoGameAdapter(List<VideoGame> videoGames, OnItemClickListener listener) {
        this.videoGames = videoGames;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VideoGameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_videogame, parent, false);
        return new VideoGameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoGameViewHolder holder, int position) {
        VideoGame game = videoGames.get(position);
        holder.bind(game, listener);
    }

    @Override
    public int getItemCount() {
        return videoGames.size();
    }

    public void updateList(List<VideoGame> newList) {
        this.videoGames = newList;
        notifyDataSetChanged();
    }

    static class VideoGameViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvGenero, tvAno, tvCalificacion, tvFecha;
        ImageButton btnEdit, btnDelete;

        public VideoGameViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvGenero = itemView.findViewById(R.id.tvGenero);
            tvAno = itemView.findViewById(R.id.tvAno);
            tvCalificacion = itemView.findViewById(R.id.tvCalificacion);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        public void bind(VideoGame game, OnItemClickListener listener) {
            tvTitulo.setText(game.getTitulo());
            tvGenero.setText("Género: " + game.getGenero());
            tvAno.setText("Año: " + game.getAnoLanzamiento());
            tvCalificacion.setText("★ " + String.format("%.1f", game.getCalificacion()));
            tvFecha.setText("Estreno: " + game.getFechaEstrenoFormateada());

            btnEdit.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onEditClick(game, position);
                }
            });

            btnDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(game, position);
                }
            });
        }
    }
}