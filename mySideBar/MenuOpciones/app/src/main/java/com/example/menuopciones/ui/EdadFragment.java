package com.example.menuopciones.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.menuopciones.R;

import java.util.Calendar;

public class EdadFragment extends Fragment {

    private DatePicker datePicker;
    private Button btnCalcular;
    private TextView tvResultado;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edad, container, false);

        datePicker = view.findViewById(R.id.date_picker);
        btnCalcular = view.findViewById(R.id.btn_calcular);
        tvResultado = view.findViewById(R.id.tv_resultado);

        btnCalcular.setOnClickListener(v -> {
            int year = datePicker.getYear();
            int month = datePicker.getMonth();
            int day = datePicker.getDayOfMonth();

            Calendar nacimiento = Calendar.getInstance();
            nacimiento.set(year, month, day);

            Calendar hoy = Calendar.getInstance();

            int edad = hoy.get(Calendar.YEAR) - nacimiento.get(Calendar.YEAR);

            if (hoy.get(Calendar.DAY_OF_YEAR) < nacimiento.get(Calendar.DAY_OF_YEAR)) {
                edad--;
            }

            tvResultado.setText("Tu edad es: " + edad + " años 🎂");
        });

        return view;
    }
}
