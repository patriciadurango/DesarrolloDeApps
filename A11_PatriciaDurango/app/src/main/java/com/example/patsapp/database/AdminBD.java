package com.example.patsapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * PASO 2: Administrador de Base de Datos
 * Crea 3 tablas: platos, clientes, pedidos
 */
public class AdminBD extends SQLiteOpenHelper {

    private static final String NOMBRE_BD = "restaurante_venezolano.db";
    private static final int VERSION_BD = 1;

    // Tabla 1: PLATOS (Catálogo 1)
    private static final String TABLA_PLATOS = "platos";
    private static final String CREAR_TABLA_PLATOS =
            "CREATE TABLE " + TABLA_PLATOS + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT NOT NULL, " +
                    "precio REAL NOT NULL, " +
                    "categoria TEXT NOT NULL)";

    // Tabla 2: CLIENTES (Catálogo 2)
    private static final String TABLA_CLIENTES = "clientes";
    private static final String CREAR_TABLA_CLIENTES =
            "CREATE TABLE " + TABLA_CLIENTES + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT NOT NULL, " +
                    "telefono TEXT NOT NULL, " +
                    "direccion TEXT)";

    // Tabla 3: PEDIDOS (Tabla de Relación)
    private static final String TABLA_PEDIDOS = "pedidos";
    private static final String CREAR_TABLA_PEDIDOS =
            "CREATE TABLE " + TABLA_PEDIDOS + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "id_cliente INTEGER NOT NULL, " +
                    "id_plato INTEGER NOT NULL, " +
                    "cantidad INTEGER NOT NULL, " +
                    "fecha TEXT NOT NULL, " +
                    "total REAL NOT NULL, " +
                    "FOREIGN KEY(id_cliente) REFERENCES " + TABLA_CLIENTES + "(id), " +
                    "FOREIGN KEY(id_plato) REFERENCES " + TABLA_PLATOS + "(id))";

    public AdminBD(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear las 3 tablas
        db.execSQL(CREAR_TABLA_PLATOS);
        db.execSQL(CREAR_TABLA_CLIENTES);
        db.execSQL(CREAR_TABLA_PEDIDOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Si actualizamos la versión, eliminamos y recreamos las tablas
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_PEDIDOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_CLIENTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_PLATOS);
        onCreate(db);
    }
}