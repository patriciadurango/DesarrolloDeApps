package com.example.patsapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * PASO 3: Gestor de Base de Datos
 * Operaciones CRUD + Consultas JOIN
 */
public class GestorBD {

    private AdminBD admin;
    private SQLiteDatabase bd;

    public GestorBD(Context context) {
        admin = new AdminBD(context);
    }

    public void abrir() {
        bd = admin.getWritableDatabase();
    }

    public void cerrar() {
        if (bd != null && bd.isOpen()) {
            bd.close();
        }
    }

    // ========== CRUD PLATOS (Catálogo 1) ==========

    public boolean insertarPlato(String nombre, double precio, String categoria) {
        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("precio", precio);
        valores.put("categoria", categoria);
        long resultado = bd.insert("platos", null, valores);
        return resultado != -1;
    }

    public Cursor consultarPlatos() {
        return bd.rawQuery("SELECT * FROM platos ORDER BY categoria, nombre", null);
    }

    public boolean actualizarPlato(int id, String nombre, double precio, String categoria) {
        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("precio", precio);
        valores.put("categoria", categoria);
        int resultado = bd.update("platos", valores, "id = ?",
                new String[]{String.valueOf(id)});
        return resultado > 0;
    }

    public boolean eliminarPlato(int id) {
        int resultado = bd.delete("platos", "id = ?",
                new String[]{String.valueOf(id)});
        return resultado > 0;
    }

    // ========== CRUD CLIENTES (Catálogo 2) ==========

    public boolean insertarCliente(String nombre, String telefono, String direccion) {
        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("telefono", telefono);
        valores.put("direccion", direccion);
        long resultado = bd.insert("clientes", null, valores);
        return resultado != -1;
    }

    public Cursor consultarClientes() {
        return bd.rawQuery("SELECT * FROM clientes ORDER BY nombre", null);
    }

    public boolean actualizarCliente(int id, String nombre, String telefono, String direccion) {
        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("telefono", telefono);
        valores.put("direccion", direccion);
        int resultado = bd.update("clientes", valores, "id = ?",
                new String[]{String.valueOf(id)});
        return resultado > 0;
    }

    public boolean eliminarCliente(int id) {
        int resultado = bd.delete("clientes", "id = ?",
                new String[]{String.valueOf(id)});
        return resultado > 0;
    }

    // ========== CRUD PEDIDOS (Tabla de Relación) ==========

    public boolean insertarPedido(int idCliente, int idPlato, int cantidad, double total) {
        ContentValues valores = new ContentValues();
        valores.put("id_cliente", idCliente);
        valores.put("id_plato", idPlato);
        valores.put("cantidad", cantidad);
        valores.put("total", total);

        // Fecha actual
        String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault()).format(new Date());
        valores.put("fecha", fecha);

        long resultado = bd.insert("pedidos", null, valores);
        return resultado != -1;
    }

    public Cursor consultarPedidos() {
        return bd.rawQuery("SELECT * FROM pedidos ORDER BY fecha DESC", null);
    }

    public boolean eliminarPedido(int id) {
        int resultado = bd.delete("pedidos", "id = ?",
                new String[]{String.valueOf(id)});
        return resultado > 0;
    }

    // ========== CONSULTAS CON JOIN (Reportes) ==========

    // Reporte 1: Todos los pedidos con detalles completos
    public Cursor consultarPedidosDetallados() {
        String query = "SELECT " +
                "p.id AS id_pedido, " +
                "c.nombre AS cliente, " +
                "pl.nombre AS plato, " +
                "pl.categoria, " +
                "p.cantidad, " +
                "pl.precio AS precio_unitario, " +
                "p.total, " +
                "p.fecha " +
                "FROM pedidos p " +
                "INNER JOIN clientes c ON p.id_cliente = c.id " +
                "INNER JOIN platos pl ON p.id_plato = pl.id " +
                "ORDER BY p.fecha DESC";
        return bd.rawQuery(query, null);
    }

    // Reporte 2: Pedidos por cliente
    public Cursor consultarPedidosPorCliente(int idCliente) {
        String query = "SELECT " +
                "p.id AS id_pedido, " +
                "pl.nombre AS plato, " +
                "pl.categoria, " +
                "p.cantidad, " +
                "p.total, " +
                "p.fecha " +
                "FROM pedidos p " +
                "INNER JOIN platos pl ON p.id_plato = pl.id " +
                "WHERE p.id_cliente = ? " +
                "ORDER BY p.fecha DESC";
        return bd.rawQuery(query, new String[]{String.valueOf(idCliente)});
    }

    // Reporte 3: Platos más vendidos
    public Cursor consultarPlatosMasVendidos() {
        String query = "SELECT " +
                "pl.nombre AS plato, " +
                "pl.categoria, " +
                "SUM(p.cantidad) AS total_vendido, " +
                "COUNT(p.id) AS num_pedidos, " +
                "SUM(p.total) AS ventas_totales " +
                "FROM pedidos p " +
                "INNER JOIN platos pl ON p.id_plato = pl.id " +
                "GROUP BY pl.id, pl.nombre, pl.categoria " +
                "ORDER BY total_vendido DESC";
        return bd.rawQuery(query, null);
    }
}