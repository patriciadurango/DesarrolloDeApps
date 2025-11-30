package com.example.menuopciones.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.menuopciones.R;

public class LoginFragment extends Fragment {

    private EditText etUsuario, etContrasena;
    private Button btnLogin;
    private TextView tvError;

    private final String USUARIO_VALIDO = "admin";
    private final String CONTRASENA_VALIDA = "1234";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etUsuario = view.findViewById(R.id.et_usuario);
        etContrasena = view.findViewById(R.id.et_contrasena);
        btnLogin = view.findViewById(R.id.btn_login);
        tvError = view.findViewById(R.id.tv_error);

        btnLogin.setOnClickListener(v -> {
            String user = etUsuario.getText().toString().trim();
            String pass = etContrasena.getText().toString().trim();

            if (user.equals(USUARIO_VALIDO) && pass.equals(CONTRASENA_VALIDA)) {
                tvError.setVisibility(View.GONE);
                Toast.makeText(getContext(), "¡Inicio de sesión exitoso! 🎉", Toast.LENGTH_SHORT).show();
            } else {
                tvError.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }
}

