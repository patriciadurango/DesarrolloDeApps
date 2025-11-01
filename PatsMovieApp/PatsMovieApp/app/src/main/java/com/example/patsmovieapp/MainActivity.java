package com.example.patsmovieapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Pelicula> listaDePeliculas = new ArrayList<>();
    private PeliculaAdapter adapter;
    private ActivityResultLauncher<Intent> addEditMovieLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewPeliculas);
        FloatingActionButton fabAgregar = findViewById(R.id.fabAgregar);

        configurarRecyclerView(recyclerView);
        cargarDatosDeEjemplo();

        addEditMovieLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        Pelicula pelicula = (Pelicula) data.getSerializableExtra("pelicula");
                        int position = data.getIntExtra("position", -1);

                        if (position == -1) {
                            listaDePeliculas.add(pelicula);
                            Toast.makeText(this, "Película guardada", Toast.LENGTH_SHORT).show();
                        } else {
                            listaDePeliculas.set(position, pelicula);
                            Toast.makeText(this, "Película actualizada", Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

        fabAgregar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditMovieActivity.class);
            addEditMovieLauncher.launch(intent);
        });
    }

    private void configurarRecyclerView(RecyclerView recyclerView) {
        adapter = new PeliculaAdapter(listaDePeliculas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(MainActivity.this, AddEditMovieActivity.class);
            intent.putExtra("pelicula", listaDePeliculas.get(position));
            intent.putExtra("position", position);
            addEditMovieLauncher.launch(intent);
        });
    }

    private void cargarDatosDeEjemplo() {
        String jsonPeliculas = "[{\"nombre\":\"Duna: Parte Dos\",\"vista\":false},{\"nombre\":\"Oppenheimer\",\"vista\":true},{\"nombre\":\"Interestelar\",\"vista\":false}]";

        Gson gson = new Gson();
        Type tipoListaPeliculas = new TypeToken<ArrayList<Pelicula>>(){}.getType();
        List<Pelicula> peliculasIniciales = gson.fromJson(jsonPeliculas, tipoListaPeliculas);

        listaDePeliculas.addAll(peliculasIniciales);
        adapter.notifyDataSetChanged();
    }
}