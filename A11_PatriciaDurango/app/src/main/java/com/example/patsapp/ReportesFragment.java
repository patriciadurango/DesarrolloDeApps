package com.example.patsapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.patsapp.database.GestorBD;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * PASO 8: ReportesFragment - Datos Cruzados con JOIN
 * 3 Tipos de reportes usando INNER JOIN
 */
public class ReportesFragment extends Fragment {

    private Button btnReporteTodos, btnReportePorCliente, btnReporteMasVendidos;
    private Spinner spinnerClientesReporte;
    private ListView listViewReportes;

    private GestorBD gestorBD;
    private ArrayList<String> listaReportes;
    private ArrayAdapter<String> adapter;

    private HashMap<String, Integer> mapaClientes;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reportes, container, false);

        // Inicializar vistas
        btnReporteTodos = root.findViewById(R.id.btnReporteTodos);
        btnReportePorCliente = root.findViewById(R.id.btnReportePorCliente);
        btnReporteMasVendidos = root.findViewById(R.id.btnReporteMasVendidos);
        spinnerClientesReporte = root.findViewById(R.id.spinnerClientesReporte);
        listViewReportes = root.findViewById(R.id.listViewReportes);

        // Inicializar base de datos
        gestorBD = new GestorBD(getContext());
        listaReportes = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listaReportes);
        listViewReportes.setAdapter(adapter);

        mapaClientes = new HashMap<>();

        // Botones
        btnReporteTodos.setOnClickListener(v -> generarReporteTodosPedidos());
        btnReportePorCliente.setOnClickListener(v -> generarReportePorCliente());
        btnReporteMasVendidos.setOnClickListener(v -> generarReportePlatosMasVendidos());

        cargarClientesReporte();

        return root;
    }

    private void cargarClientesReporte() {
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
        spinnerClientesReporte.setAdapter(adapterClientes);
    }

    // REPORTE 1: Todos los pedidos con detalles completos (JOIN 3 tablas)
    private void generarReporteTodosPedidos() {
        listaReportes.clear();
        listaReportes.add("📊 REPORTE: TODOS LOS PEDIDOS (DATOS CRUZADOS CON JOIN)\n");

        gestorBD.abrir();
        Cursor cursor = gestorBD.consultarPedidosDetallados();

        if (cursor != null && cursor.moveToFirst()) {
            int contador = 1;
            double totalGeneral = 0;

            do {
                int idPedido = cursor.getInt(0);
                String cliente = cursor.getString(1);
                String plato = cursor.getString(2);
                String categoria = cursor.getString(3);
                int cantidad = cursor.getInt(4);
                double precioUnitario = cursor.getDouble(5);
                double total = cursor.getDouble(6);
                String fecha = cursor.getString(7);

                totalGeneral += total;

                String item = String.format(
                        "━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                                "Pedido #%d [ID: %d]\n" +
                                "━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                                "👤 Cliente: %s\n" +
                                "🍽️ Plato: %s\n" +
                                "🏷️ Categoría: %s\n" +
                                "🔢 Cantidad: %d\n" +
                                "💵 Precio Unit: $%.2f\n" +
                                "💰 Total: $%.2f\n" +
                                "📅 Fecha: %s\n",
                        contador, idPedido, cliente, plato, categoria,
                        cantidad, precioUnitario, total, fecha
                );

                listaReportes.add(item);
                contador++;

            } while (cursor.moveToNext());

            listaReportes.add(String.format("\n💰 TOTAL GENERAL: $%.2f\n", totalGeneral));
            cursor.close();

            Toast.makeText(getContext(), "✅ Reporte generado: " + (contador-1) + " pedidos",
                    Toast.LENGTH_SHORT).show();
        } else {
            listaReportes.add("\n⚠️ No hay pedidos registrados\n");
            Toast.makeText(getContext(), "⚠️ No hay pedidos", Toast.LENGTH_SHORT).show();
        }

        gestorBD.cerrar();
        adapter.notifyDataSetChanged();
    }

    // REPORTE 2: Pedidos por cliente específico (JOIN 2 tablas)
    private void generarReportePorCliente() {
        if (spinnerClientesReporte.getSelectedItem() == null) {
            Toast.makeText(getContext(), "⚠️ No hay clientes registrados", Toast.LENGTH_SHORT).show();
            return;
        }

        String clienteSeleccionado = spinnerClientesReporte.getSelectedItem().toString();
        int idCliente = mapaClientes.get(clienteSeleccionado);

        listaReportes.clear();
        listaReportes.add("📊 REPORTE: PEDIDOS DE " + clienteSeleccionado.toUpperCase() + "\n");

        gestorBD.abrir();
        Cursor cursor = gestorBD.consultarPedidosPorCliente(idCliente);

        if (cursor != null && cursor.moveToFirst()) {
            int contador = 1;
            double totalCliente = 0;

            do {
                int idPedido = cursor.getInt(0);
                String plato = cursor.getString(1);
                String categoria = cursor.getString(2);
                int cantidad = cursor.getInt(3);
                double total = cursor.getDouble(4);
                String fecha = cursor.getString(5);

                totalCliente += total;

                String item = String.format(
                        "━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                                "Pedido #%d [ID: %d]\n" +
                                "━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                                "🍽️ Plato: %s\n" +
                                "🏷️ Categoría: %s\n" +
                                "🔢 Cantidad: %d\n" +
                                "💰 Total: $%.2f\n" +
                                "📅 Fecha: %s\n",
                        contador, idPedido, plato, categoria, cantidad, total, fecha
                );

                listaReportes.add(item);
                contador++;

            } while (cursor.moveToNext());

            listaReportes.add(String.format("\n💰 TOTAL DEL CLIENTE: $%.2f\n", totalCliente));
            cursor.close();

            Toast.makeText(getContext(), "✅ " + (contador-1) + " pedidos de " + clienteSeleccionado,
                    Toast.LENGTH_SHORT).show();
        } else {
            listaReportes.add("\n⚠️ Este cliente no tiene pedidos registrados\n");
            Toast.makeText(getContext(), "⚠️ Sin pedidos", Toast.LENGTH_SHORT).show();
        }

        gestorBD.cerrar();
        adapter.notifyDataSetChanged();
    }

    // REPORTE 3: Platos más vendidos (JOIN con GROUP BY)
    private void generarReportePlatosMasVendidos() {
        listaReportes.clear();
        listaReportes.add("📊 REPORTE: PLATOS MÁS VENDIDOS (AGRUPADO CON JOIN)\n");

        gestorBD.abrir();
        Cursor cursor = gestorBD.consultarPlatosMasVendidos();

        if (cursor != null && cursor.moveToFirst()) {
            int posicion = 1;
            double ventasTotales = 0;

            do {
                String plato = cursor.getString(0);
                String categoria = cursor.getString(1);
                int totalVendido = cursor.getInt(2);
                int numPedidos = cursor.getInt(3);
                double ventas = cursor.getDouble(4);

                ventasTotales += ventas;

                String medalla = "";
                if (posicion == 1) medalla = "🥇";
                else if (posicion == 2) medalla = "🥈";
                else if (posicion == 3) medalla = "🥉";
                else medalla = "#" + posicion;

                String item = String.format(
                        "━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                                "%s %s\n" +
                                "━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                                "🏷️ Categoría: %s\n" +
                                "🔢 Unidades vendidas: %d\n" +
                                "📦 Número de pedidos: %d\n" +
                                "💰 Ventas generadas: $%.2f\n",
                        medalla, plato, categoria, totalVendido, numPedidos, ventas
                );

                listaReportes.add(item);
                posicion++;

            } while (cursor.moveToNext());

            listaReportes.add(String.format("\n💰 VENTAS TOTALES: $%.2f\n", ventasTotales));
            cursor.close();

            Toast.makeText(getContext(), "✅ Ranking de " + (posicion-1) + " platos generado",
                    Toast.LENGTH_SHORT).show();
        } else {
            listaReportes.add("\n⚠️ No hay datos de ventas\n");
            Toast.makeText(getContext(), "⚠️ Sin datos", Toast.LENGTH_SHORT).show();
        }

        gestorBD.cerrar();
        adapter.notifyDataSetChanged();
    }
}