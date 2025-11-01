package com.example.a04_patriciadurango;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    private Button btnFoto, btnHorario, btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enlazar botones
        btnFoto = findViewById(R.id.btn_foto);
        btnHorario = findViewById(R.id.btn_horario);
        btnMenu = findViewById(R.id.btn_menu);

        // Cargar un fragment por defecto al iniciar la app
        loadFragment(new fragment_actividades());

        // Configurar botones para cambiar de fragment
        btnFoto.setOnClickListener(v -> loadFragment(new fragment_foto()));
        btnHorario.setOnClickListener(v -> loadFragment(new fragment_horario()));
        btnMenu.setOnClickListener(v -> loadFragment(new fragment_actividades()));
    }

    // Método para reemplazar fragment en el FrameLayout
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
