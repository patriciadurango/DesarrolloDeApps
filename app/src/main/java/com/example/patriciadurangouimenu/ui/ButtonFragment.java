package com.example.patriciadurangouimenu.ui;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.patriciadurangouimenu.R;

public class ButtonFragment extends Fragment {

    public ButtonFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout del fragment
        return inflater.inflate(R.layout.fragment_button, container, false);
    }
}
