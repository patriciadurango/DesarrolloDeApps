package com.example.patsmovieapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditMovieActivity extends AppCompatActivity {

    private EditText editTextNombrePelicula;
    private CheckBox checkBoxVista;
    private Button buttonGuardar;
    private int posicionPelicula = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_movie);

        editTextNombrePelicula = findViewById(R.id.editTextNombrePelicula);
        checkBoxVista = findViewById(R.id.checkBoxVista);
        buttonGuardar = findViewById(R.id.buttonGuardar);

        if (getIntent().hasExtra("pelicula")) {
            Pelicula pelicula = (Pelicula) getIntent().getSerializableExtra("pelicula");
            posicionPelicula = getIntent().getIntExtra("position", -1);

            editTextNombrePelicula.setText(pelicula.getNombre());
            checkBoxVista.setChecked(pelicula.isVista());
            buttonGuardar.setText("Actualizar");
            setTitle("Editar Película");
        } else {
            setTitle("Agregar Película");
        }

        buttonGuardar.setOnClickListener(v -> guardarPelicula());
    }

    private void guardarPelicula() {
        String nombre = editTextNombrePelicula.getText().toString().trim();
        boolean vista = checkBoxVista.isChecked();

        if (nombre.isEmpty()) {
            editTextNombrePelicula.setError("El nombre no puede estar vacío");
            return;
        }

        Pelicula peliculaGuardada = new Pelicula(nombre, vista);
        Intent intentDeResultado = new Intent();
        intentDeResultado.putExtra("pelicula", peliculaGuardada);
        intentDeResultado.putExtra("position", posicionPelicula);

        setResult(Activity.RESULT_OK, intentDeResultado);
        finish();
    }
}