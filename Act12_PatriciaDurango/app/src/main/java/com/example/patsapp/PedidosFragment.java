package com.example.patsapp;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.HashMap;

/**
 * PASO 7: PedidosFragment - Tabla de Relación
 * Relaciona Clientes con Platos (Pedidos)
 */
public class PedidosFragment extends Fragment {

    private Spinner spinnerClientes, spinnerPlatos;
    private EditText etCantidad;
    private Button btnRegistrarPedido, btnEliminarPedido;
    private ListView listViewPedidos;

    private GestorBD gestorBD;
    private ArrayList<String> listaPedidos;
    private ArrayAdapter<String> adapter;

    private HashMap<String, Integer> mapaClientes;
    private HashMap<String, Integer> mapaPlatos;
    private HashMap<String, Double> mapaPreciosPlatos;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pedidos, container, false);

        // Inicializar vistas
        spinnerClientes = root.findViewById(R.id.spinnerClientes);
        spinnerPlatos = root.findViewById(R.id.spinnerPlatos);
        etCantidad = root.findViewById(R.id.etCantidad);
        btnRegistrarPedido = root.findViewById(R.id.btnRegistrarPedido);
        btnEliminarPedido = root.findViewById(R.id.btnEliminarPedido);
        listViewPedidos = root.findViewById(R.id.listViewPedidos);

        // Inicializar base de datos
        gestorBD = new GestorBD(getContext());
        listaPedidos = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listaPedidos);
        listViewPedidos.setAdapter(adapter);

        mapaClientes = new HashMap<>();
        mapaPlatos = new HashMap<>();
        mapaPreciosPlatos = new HashMap<>();

        // Botones
        btnRegistrarPedido.setOnClickListener(v -> registrarPedido());
        btnEliminarPedido.setOnClickListener(v -> mostrarDialogoEliminar());

        cargarClientes();
        cargarPlatos();
        cargarPedidos();

        return root;
    }

    private void cargarClientes() {
        ArrayList<String> listaClientes = new ArrayList<>();
        mapaClientes.clear();

        gestorBD.abrir();
        Cursor cursor = gestorBD.consultarClientes();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String nombre = cursor.getString(1);
                listaClientes.add(nombre);
                mapaClientes.put(nombre, id);
            } while (cursor.moveToNext());
            cursor.close();
        }
        gestorBD.cerrar();

        ArrayAdapter<String> adapterClientes = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_item, listaClientes);
        adapterClientes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClientes.setAdapter(adapterClientes);
    }

    private void cargarPlatos() {
        ArrayList<String> listaPlatos = new ArrayList<>();
        mapaPlatos.clear();
        mapaPreciosPlatos.clear();

        gestorBD.abrir();
        Cursor cursor = gestorBD.consultarPlatos();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String nombre = cursor.getString(1);
                double precio = cursor.getDouble(2);
                String categoria = cursor.getString(3);

                String itemPlato = nombre + " - $" + String.format("%.2f", precio);
                listaPlatos.add(itemPlato);
                mapaPlatos.put(itemPlato, id);
                mapaPreciosPlatos.put(itemPlato, precio);
            } while (cursor.moveToNext());
            cursor.close();
        }
        gestorBD.cerrar();

        ArrayAdapter<String> adapterPlatos = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_item, listaPlatos);
        adapterPlatos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlatos.setAdapter(adapterPlatos);
    }

    private void registrarPedido() {
        if (spinnerClientes.getSelectedItem() == null || spinnerPlatos.getSelectedItem() == null) {
            Toast.makeText(getContext(), "⚠️ Debes tener clientes y platos registrados", Toast.LENGTH_SHORT).show();
            return;
        }

        String cantidadStr = etCantidad.getText().toString().trim();
        if (cantidadStr.isEmpty()) {
            Toast.makeText(getContext(), "⚠️ Ingresa la cantidad", Toast.LENGTH_SHORT).show();
            return;
        }

        int cantidad = Integer.parseInt(cantidadStr);
        if (cantidad <= 0) {
            Toast.makeText(getContext(), "⚠️ La cantidad debe ser mayor a 0", Toast.LENGTH_SHORT).show();
            return;
        }

        String clienteSeleccionado = spinnerClientes.getSelectedItem().toString();
        String platoSeleccionado = spinnerPlatos.getSelectedItem().toString();

        int idCliente = mapaClientes.get(clienteSeleccionado);
        int idPlato = mapaPlatos.get(platoSeleccionado);
        double precioUnitario = mapaPreciosPlatos.get(platoSeleccionado);
        double total = precioUnitario * cantidad;

        gestorBD.abrir();
        boolean insertado = gestorBD.insertarPedido(idCliente, idPlato, cantidad, total);
        gestorBD.cerrar();

        if (insertado) {
            Toast.makeText(getContext(), "✅ Pedido registrado - Total: $" + String.format("%.2f", total),
                    Toast.LENGTH_SHORT).show();
            etCantidad.setText("");
            cargarPedidos();
        } else {
            Toast.makeText(getContext(), "❌ Error al registrar pedido", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarPedidos() {
        listaPedidos.clear();
        gestorBD.abrir();
        Cursor cursor = gestorBD.consultarPedidos();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int idCliente = cursor.getInt(1);
                int idPlato = cursor.getInt(2);
                int cantidad = cursor.getInt(3);
                String fecha = cursor.getString(4);
                double total = cursor.getDouble(5);

                String item = String.format("[ID: %d]\nCliente ID: %d | Plato ID: %d\nCantidad: %d | Total: $%.2f\nFecha: %s",
                        id, idCliente, idPlato, cantidad, total, fecha);
                listaPedidos.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }
        gestorBD.cerrar();
        adapter.notifyDataSetChanged();
    }

    private void mostrarDialogoEliminar() {
        if (listaPedidos.isEmpty()) {
            Toast.makeText(getContext(), "⚠️ No hay pedidos para eliminar", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("🗑️ Eliminar Pedido");
        builder.setMessage("Ingresa el ID del pedido a eliminar:");

        final EditText input = new EditText(getContext());
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("Eliminar", (dialog, which) -> {
            String idStr = input.getText().toString().trim();
            if (!idStr.isEmpty()) {
                int id = Integer.parseInt(idStr);
                eliminarPedido(id);
            }
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void eliminarPedido(int id) {
        gestorBD.abrir();
        boolean eliminado = gestorBD.eliminarPedido(id);
        gestorBD.cerrar();

        if (eliminado) {
            Toast.makeText(getContext(), "✅ Pedido eliminado", Toast.LENGTH_SHORT).show();
            cargarPedidos();
        } else {
            Toast.makeText(getContext(), "❌ No se encontró el pedido", Toast.LENGTH_SHORT).show();
        }
    }
}