package com.example.patsmovieapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PeliculaAdapter extends RecyclerView.Adapter<PeliculaAdapter.PeliculaViewHolder> {

    private List<Pelicula> listaPeliculas;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public PeliculaAdapter(List<Pelicula> listaPeliculas) {
        this.listaPeliculas = listaPeliculas;
    }

    @NonNull
    @Override
    public PeliculaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pelicula, parent, false);
        return new PeliculaViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PeliculaViewHolder holder, int position) {
        Pelicula peliculaActual = listaPeliculas.get(position);
        holder.nombrePelicula.setText(peliculaActual.getNombre());
        holder.status.setText(peliculaActual.isVista() ? "Status: Vista" : "Status: Por ver");
    }

    @Override
    public int getItemCount() {
        return listaPeliculas.size();
    }

    public static class PeliculaViewHolder extends RecyclerView.ViewHolder {
        public TextView nombrePelicula;
        public TextView status;
        public Button buttonEditar;

        public PeliculaViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            nombrePelicula = itemView.findViewById(R.id.textViewNombrePelicula);
            status = itemView.findViewById(R.id.textViewStatus);
            buttonEditar = itemView.findViewById(R.id.buttonEditar);

            buttonEditar.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onEditClick(position);
                    }
                }
            });
        }
    }
}