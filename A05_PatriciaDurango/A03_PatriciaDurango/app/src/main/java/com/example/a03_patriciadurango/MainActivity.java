package com.example.a03_patriciadurango;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button botonClick, botonLargo;
    private TextView etiquetaDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonClick = findViewById(R.id.btnOnClick);
        botonLargo = findViewById(R.id.btnOnLongClick);
        etiquetaDetalle = findViewById(R.id.txtDetalle);

        configurarEventos();
    }

    private void configurarEventos() {
        botonClick.setOnClickListener(v -> {
            etiquetaDetalle.setText("Presionaste el botón de click normal ✅");
            mostrarToast("Click rápido detectado ⚡", Gravity.TOP);
        });
        botonLargo.setOnLongClickListener(v -> {
            etiquetaDetalle.setText("Dejaste presionado el botón ⏳");
            mostrarToast("Click prolongado registrado 💡", Gravity.CENTER);
            return true;
        });
    }
    private void mostrarToast(String mensaje, int posicion) {
        Toast aviso = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        aviso.setGravity(posicion, 0, 200);
        aviso.show();
    }
}
