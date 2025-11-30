package com.example.patsapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configurar DrawerLayout y NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Configurar el toggle del drawer (menú hamburguesa)
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Establecer el listener de navegación
        navigationView.setNavigationItemSelectedListener(this);

        // Cargar el fragment inicial (Gestión de Platos)
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_home);
            setTitle("🍴 Gestión de Platos");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // No se usa menú de opciones en este proyecto
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment selectedFragment = null;

        if (id == R.id.nav_home) {
            // Gestión de Platos (Catálogo 1)
            selectedFragment = new HomeFragment();
            setTitle("🍴 Gestión de Platos");
        } else if (id == R.id.nav_dashboard) {
            // Gestión de Clientes (Catálogo 2)
            selectedFragment = new DashboardFragment();
            setTitle("👥 Gestión de Clientes");
        } else if (id == R.id.nav_pedidos) {
            // Registro de Pedidos (Tabla de Relación)
            selectedFragment = new PedidosFragment();
            setTitle("🛒 Registro de Pedidos");
        } else if (id == R.id.nav_reportes) {
            // Reportes con JOIN (Datos Cruzados)
            selectedFragment = new ReportesFragment();
            setTitle("📈 Reportes con JOIN");
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}