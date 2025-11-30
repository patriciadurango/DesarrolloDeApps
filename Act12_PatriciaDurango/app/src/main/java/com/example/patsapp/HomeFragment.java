package com.example.patsapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.patsapp.database.GestorBD;

import java.util.ArrayList;

/**
 * PASO 4: HomeFragment - Gestión de Platos (Catálogo 1)
 * CRUD completo: Crear, Leer, Actualizar, Eliminar
 */
public class HomeFragment extends Fragment {

    private EditText etNombrePlato, etPrecio;
    private Spinner spinnerCategoria;
    private Button btnGuardar, btnNuevo, btnModificar, btnEliminar;
    private ListView listViewPlatos;

    private GestorBD gestorBD;
    private ArrayList<String> listaPlatos;
    private ArrayAdapter<String> adapter;

    private int idPlatoSeleccionado = -1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Inicializar vistas
        etNombrePlato = root.findViewById(R.id.etNombrePlato);
        etPrecio = root.findViewById(R.id.etPrecio);
        spinnerCategoria = root.findViewById(R.id.spinnerCategoria);
        btnGuardar = root.findViewById(R.id.btnGuardar);
        btnNuevo = root.findViewById(R.id.btnNuevo);
        btnModificar = root.findViewById(R.id.btnModificar);
        btnEliminar = root.findViewById(R.id.btnEliminar);
        listViewPlatos = root.findViewById(R.id.listViewPlatos);

        // Inicializar base de datos
        gestorBD = new GestorBD(getContext());
        listaPlatos = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listaPlatos);
        listViewPlatos.setAdapter(adapter);

        // Configurar Spinner de categorías
        String[] categorias = {"Arepas", "Platos Principales", "Tequeños", "Postres", "Bebidas"};
        ArrayAdapter<String> adapterCategorias = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_item, categorias);
        adapterCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterCategorias);

        // Botones
        btnGuardar.setOnClickListener(v -> guardarPlato());
        btnNuevo.setOnClickListener(v -> limpiarCampos());
        btnModificar.setOnClickListener(v -> modificarPlato());
        btnEliminar.setOnClickListener(v -> eliminarPlato());

        // Seleccionar plato de la lista
        listViewPlatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemSeleccionado = listaPlatos.get(position);
                cargarDatosPlato(itemSeleccionado);
            }
        });

        cargarPlatos();
        return root;
    }

    private void guardarPlato() {
        String nombre = etNombrePlato.getText().toString().trim();
        String precioStr = etPrecio.getText().toString().trim();
        String categoria = spinnerCategoria.getSelectedItem().toString();

        if (nombre.isEmpty() || precioStr.isEmpty()) {
            Toast.makeText(getContext(), "⚠️ Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double precio = Double.parseDouble(precioStr);

        gestorBD.abrir();
        boolean insertado = gestorBD.insertarPlato(nombre, precio, categoria);
        gestorBD.cerrar();

        if (insertado) {
            Toast.makeText(getContext(), "✅ Plato guardado", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            cargarPlatos();
        } else {
            Toast.makeText(getContext(), "❌ Error al guardar", Toast.LENGTH_SHORT).show();
        }
    }

    private void modificarPlato() {
        if (idPlatoSeleccionado == -1) {
            Toast.makeText(getContext(), "⚠️ Selecciona un plato", Toast.LENGTH_SHORT).show();
            return;
        }

        String nombre = etNombrePlato.getText().toString().trim();
        String precioStr = etPrecio.getText().toString().trim();
        String categoria = spinnerCategoria.getSelectedItem().toString();

        if (nombre.isEmpty() || precioStr.isEmpty()) {
            Toast.makeText(getContext(), "⚠️ Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double precio = Double.parseDouble(precioStr);

        gestorBD.abrir();
        boolean actualizado = gestorBD.actualizarPlato(idPlatoSeleccionado, nombre, precio, categoria);
        gestorBD.cerrar();

        if (actualizado) {
            Toast.makeText(getContext(), "✅ Plato modificado", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            cargarPlatos();
        } else {
            Toast.makeText(getContext(), "❌ Error al modificar", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarPlato() {
        if (idPlatoSeleccionado == -1) {
            Toast.makeText(getContext(), "⚠️ Selecciona un plato", Toast.LENGTH_SHORT).show();
            return;
        }

        gestorBD.abrir();
        boolean eliminado = gestorBD.eliminarPlato(idPlatoSeleccionado);
        gestorBD.cerrar();

        if (eliminado) {
            Toast.makeText(getContext(), "✅ Plato eliminado", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            cargarPlatos();
        } else {
            Toast.makeText(getContext(), "❌ Error al eliminar", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarPlatos() {
        listaPlatos.clear();
        gestorBD.abrir();
        Cursor cursor = gestorBD.consultarPlatos();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String nombre = cursor.getString(1);
                double precio = cursor.getDouble(2);
                String categoria = cursor.getString(3);

                String item = String.format("[%d] %s - $%.2f (%s)", id, nombre, precio, categoria);
                listaPlatos.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }
        gestorBD.cerrar();
        adapter.notifyDataSetChanged();
    }

    private void cargarDatosPlato(String item) {
        // Extraer ID del formato: [ID] Nombre - $Precio (Categoría)
        int inicio = item.indexOf("[") + 1;
        int fin = item.indexOf("]");
        idPlatoSeleccionado = Integer.parseInt(item.substring(inicio, fin));

        // Extraer nombre
        int finNombre = item.indexOf(" - $");
        String nombre = item.substring(fin + 2, finNombre);

        // Extraer precio
        int inicioPrecio = finNombre + 4;
        int finPrecio = item.indexOf(" (");
        String precioStr = item.substring(inicioPrecio, finPrecio);

        // Extraer categoría
        int inicioCategoria = finPrecio + 2;
        int finCategoria = item.indexOf(")");
        String categoria = item.substring(inicioCategoria, finCategoria);

        etNombrePlato.setText(nombre);
        etPrecio.setText(precioStr);

        // Seleccionar categoría en el spinner
        ArrayAdapter adapter = (ArrayAdapter) spinnerCategoria.getAdapter();
        int posicion = adapter.getPosition(categoria);
        spinnerCategoria.setSelection(posicion);
    }

    private void limpiarCampos() {
        etNombrePlato.setText("");
        etPrecio.setText("");
        spinnerCategoria.setSelection(0);
        idPlatoSeleccionado = -1;
    }
}