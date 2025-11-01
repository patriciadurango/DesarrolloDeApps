package com.example.patriciadurangouimenu.ui;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.patriciadurangouimenu.R;

public class SpinnerFragment extends Fragment {

    public SpinnerFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spinner, container, false);

        Spinner spinner = view.findViewById(R.id.spinner);
        String[] items = {"Opción 1", "Opción 2", "Opción 3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        return view;
    }
}