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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.patsapp.database.GestorBD;

import java.util.ArrayList;

/**
 * PASO 6: DashboardFragment - Gestión de Clientes (Catálogo 2)
 * CRUD completo: Crear, Leer, Actualizar, Eliminar
 */
public class DashboardFragment extends Fragment {

    private EditText etNombreCliente, etTelefono, etDireccion;
    private Button btnGuardarCliente, btnNuevoCliente, btnModificarCliente, btnEliminarCliente;
    private ListView listViewClientes;

    private GestorBD gestorBD;
    private ArrayList<String> listaClientes;
    private ArrayAdapter<String> adapter;

    private int idClienteSeleccionado = -1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Inicializar vistas
        etNombreCliente = root.findViewById(R.id.etNombreCliente);
        etTelefono = root.findViewById(R.id.etTelefono);
        etDireccion = root.findViewById(R.id.etDireccion);
        btnGuardarCliente = root.findViewById(R.id.btnGuardarCliente);
        btnNuevoCliente = root.findViewById(R.id.btnNuevoCliente);
        btnModificarCliente = root.findViewById(R.id.btnModificarCliente);
        btnEliminarCliente = root.findViewById(R.id.btnEliminarCliente);
        listViewClientes = root.findViewById(R.id.listViewClientes);

        // Inicializar base de datos
        gestorBD = new GestorBD(getContext());
        listaClientes = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listaClientes);
        listViewClientes.setAdapter(adapter);

        // Botones
        btnGuardarCliente.setOnClickListener(v -> guardarCliente());
        btnNuevoCliente.setOnClickListener(v -> limpiarCampos());
        btnModificarCliente.setOnClickListener(v -> modificarCliente());
        btnEliminarCliente.setOnClickListener(v -> eliminarCliente());

        // Seleccionar cliente de la lista
        listViewClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemSeleccionado = listaClientes.get(position);
                cargarDatosCliente(itemSeleccionado);
            }
        });

        cargarClientes();
        return root;
    }

    private void guardarCliente() {
        String nombre = etNombreCliente.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String direccion = etDireccion.getText().toString().trim();

        if (nombre.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(getContext(), "⚠️ Completa nombre y teléfono", Toast.LENGTH_SHORT).show();
            return;
        }

        gestorBD.abrir();
        boolean insertado = gestorBD.insertarCliente(nombre, telefono, direccion);
        gestorBD.cerrar();

        if (insertado) {
            Toast.makeText(getContext(), "✅ Cliente guardado", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            cargarClientes();
        } else {
            Toast.makeText(getContext(), "❌ Error al guardar", Toast.LENGTH_SHORT).show();
        }
    }

    private void modificarCliente() {
        if (idClienteSeleccionado == -1) {
            Toast.makeText(getContext(), "⚠️ Selecciona un cliente", Toast.LENGTH_SHORT).show();
            return;
        }

        String nombre = etNombreCliente.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String direccion = etDireccion.getText().toString().trim();

        if (nombre.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(getContext(), "⚠️ Completa nombre y teléfono", Toast.LENGTH_SHORT).show();
            return;
        }

        gestorBD.abrir();
        boolean actualizado = gestorBD.actualizarCliente(idClienteSeleccionado, nombre, telefono, direccion);
        gestorBD.cerrar();

        if (actualizado) {
            Toast.makeText(getContext(), "✅ Cliente modificado", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            cargarClientes();
        } else {
            Toast.makeText(getContext(), "❌ Error al modificar", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarCliente() {
        if (idClienteSeleccionado == -1) {
            Toast.makeText(getContext(), "⚠️ Selecciona un cliente", Toast.LENGTH_SHORT).show();
            return;
        }

        gestorBD.abrir();
        boolean eliminado = gestorBD.eliminarCliente(idClienteSeleccionado);
        gestorBD.cerrar();

        if (eliminado) {
            Toast.makeText(getContext(), "✅ Cliente eliminado", Toast.LENGTH_SHORT).show();
            limpiarCampos();
            cargarClientes();
        } else {
            Toast.makeText(getContext(), "❌ Error al eliminar", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarClientes() {
        listaClientes.clear();
        gestorBD.abrir();
        Cursor cursor = gestorBD.consultarClientes();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String telefono = cursor.getString(2);
                String direccion = cursor.getString(3);

                String item = String.format("[%d] %s\n📞 %s\n📍 %s",
                        id, nombre, telefono,
                        direccion != null && !direccion.isEmpty() ? direccion : "Sin dirección");
                listaClientes.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }
        gestorBD.cerrar();
        adapter.notifyDataSetChanged();
    }

    private void cargarDatosCliente(String item) {
        // Extraer ID del formato: [ID] Nombre...
        int inicio = item.indexOf("[") + 1;
        int fin = item.indexOf("]");
        idClienteSeleccionado = Integer.parseInt(item.substring(inicio, fin));

        // Extraer datos desde la base de datos
        gestorBD.abrir();
        Cursor cursor = gestorBD.consultarClientes();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                if (id == idClienteSeleccionado) {
                    String nombre = cursor.getString(1);
                    String telefono = cursor.getString(2);
                    String direccion = cursor.getString(3);

                    etNombreCliente.setText(nombre);
                    etTelefono.setText(telefono);
                    etDireccion.setText(direccion != null ? direccion : "");
                    break;
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        gestorBD.cerrar();
    }

    private void limpiarCampos() {
        etNombreCliente.setText("");
        etTelefono.setText("");
        etDireccion.setText("");
        idClienteSeleccionado = -1;
    }
}