package com.example.patriciadurangolistados;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements VideoGameAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private VideoGameAdapter adapter;
    private List<VideoGame> videoGamesList;
    private FloatingActionButton fabAdd;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        videoGamesList = new ArrayList<>();

        // Datos de ejemplo
        agregarDatosEjemplo();

        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fabAdd);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VideoGameAdapter(videoGamesList, this);
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> mostrarDialogoAgregar());
    }

    private void agregarDatosEjemplo() {
        try {
            videoGamesList.add(new VideoGame("The Legend of Zelda: Breath of the Wild",
                    "Aventura", 2017, 9.8f, dateFormat.parse("03/03/2017")));
            videoGamesList.add(new VideoGame("Red Dead Redemption 2",
                    "Acción", 2018, 9.7f, dateFormat.parse("26/10/2018")));
            videoGamesList.add(new VideoGame("God of War",
                    "Acción-Aventura", 2018, 9.5f, dateFormat.parse("20/04/2018")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void mostrarDialogoAgregar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_videogame, null);

        EditText etTitulo = view.findViewById(R.id.etTitulo);
        EditText etGenero = view.findViewById(R.id.etGenero);
        EditText etAno = view.findViewById(R.id.etAno);
        EditText etCalificacion = view.findViewById(R.id.etCalificacion);
        EditText etFecha = view.findViewById(R.id.etFecha);

        etFecha.setFocusable(false);
        etFecha.setClickable(true);
        etFecha.setOnClickListener(v -> mostrarDatePicker(etFecha));

        builder.setView(view)
                .setTitle("Agregar Videojuego")
                .setPositiveButton("Agregar", (dialog, which) -> {
                    try {
                        String titulo = etTitulo.getText().toString().trim();
                        String genero = etGenero.getText().toString().trim();
                        String anoStr = etAno.getText().toString().trim();
                        String calificacionStr = etCalificacion.getText().toString().trim();
                        String fechaStr = etFecha.getText().toString().trim();

                        if (validarCampos(titulo, genero, anoStr, calificacionStr, fechaStr)) {
                            int ano = Integer.parseInt(anoStr);
                            float calificacion = Float.parseFloat(calificacionStr);
                            Date fecha = dateFormat.parse(fechaStr);

                            VideoGame nuevoJuego = new VideoGame(titulo, genero, ano, calificacion, fecha);
                            videoGamesList.add(nuevoJuego);
                            adapter.notifyItemInserted(videoGamesList.size() - 1);
                            Toast.makeText(this, "Videojuego agregado", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(this, "Error en los datos", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create()
                .show();
    }

    private void mostrarDialogoEditar(VideoGame game, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_videogame, null);

        EditText etTitulo = view.findViewById(R.id.etTitulo);
        EditText etGenero = view.findViewById(R.id.etGenero);
        EditText etAno = view.findViewById(R.id.etAno);
        EditText etCalificacion = view.findViewById(R.id.etCalificacion);
        EditText etFecha = view.findViewById(R.id.etFecha);

        etTitulo.setText(game.getTitulo());
        etGenero.setText(game.getGenero());
        etAno.setText(String.valueOf(game.getAnoLanzamiento()));
        etCalificacion.setText(String.valueOf(game.getCalificacion()));
        etFecha.setText(game.getFechaEstrenoFormateada());

        etFecha.setFocusable(false);
        etFecha.setClickable(true);
        etFecha.setOnClickListener(v -> mostrarDatePicker(etFecha));

        builder.setView(view)
                .setTitle("Editar Videojuego")
                .setPositiveButton("Guardar", (dialog, which) -> {
                    try {
                        String titulo = etTitulo.getText().toString().trim();
                        String genero = etGenero.getText().toString().trim();
                        String anoStr = etAno.getText().toString().trim();
                        String calificacionStr = etCalificacion.getText().toString().trim();
                        String fechaStr = etFecha.getText().toString().trim();

                        if (validarCampos(titulo, genero, anoStr, calificacionStr, fechaStr)) {
                            int ano = Integer.parseInt(anoStr);
                            float calificacion = Float.parseFloat(calificacionStr);
                            Date fecha = dateFormat.parse(fechaStr);

                            game.setTitulo(titulo);
                            game.setGenero(genero);
                            game.setAnoLanzamiento(ano);
                            game.setCalificacion(calificacion);
                            game.setFechaEstreno(fecha);

                            adapter.notifyItemChanged(position);
                            Toast.makeText(this, "Videojuego actualizado", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(this, "Error en los datos", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create()
                .show();
    }

    private void mostrarDatePicker(EditText etFecha) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String fecha = String.format(Locale.getDefault(), "%02d/%02d/%d",
                            selectedDay, selectedMonth + 1, selectedYear);
                    etFecha.setText(fecha);
                }, year, month, day);
        datePickerDialog.show();
    }

    private boolean validarCampos(String titulo, String genero, String ano, String calificacion, String fecha) {
        if (titulo.isEmpty() || genero.isEmpty() || ano.isEmpty() || calificacion.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            int anoInt = Integer.parseInt(ano);
            if (anoInt < 1950 || anoInt > 2025) {
                Toast.makeText(this, "Año inválido (1950-2025)", Toast.LENGTH_SHORT).show();
                return false;
            }

            float calif = Float.parseFloat(calificacion);
            if (calif < 0 || calif > 10) {
                Toast.makeText(this, "Calificación debe estar entre 0 y 10", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Formato de número inválido", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onEditClick(VideoGame videoGame, int position) {
        mostrarDialogoEditar(videoGame, position);
    }

    @Override
    public void onDeleteClick(VideoGame videoGame, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Videojuego")
                .setMessage("¿Estás seguro de eliminar '" + videoGame.getTitulo() + "'?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    videoGamesList.remove(position);
                    adapter.notifyItemRemoved(position);
                    Toast.makeText(this, "Videojuego eliminado", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}