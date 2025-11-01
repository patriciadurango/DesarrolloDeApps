package com.example.patriciadurangouimenu;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.google.android.material.navigation.NavigationView;
import android.view.MenuItem;

import com.example.patriciadurangouimenu.ui.ButtonFragment;
import com.example.patriciadurangouimenu.ui.EditTextFragment;
import com.example.patriciadurangouimenu.ui.RadioButtonFragment;
import com.example.patriciadurangouimenu.ui.CheckBoxFragment;
import com.example.patriciadurangouimenu.ui.SwitchFragment;
import com.example.patriciadurangouimenu.ui.SpinnerFragment;
import com.example.patriciadurangouimenu.ui.SeekBarFragment;
import com.example.patriciadurangouimenu.ui.TextViewFragment;
import com.example.patriciadurangouimenu.ui.ImageViewFragment;
import com.example.patriciadurangouimenu.ui.ProgressBarFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Fragment por defecto
        if (savedInstanceState == null) {
            loadFragment(new ButtonFragment());
            navigationView.setCheckedItem(R.id.nav_button);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int id = item.getItemId();

                if (id == R.id.nav_button) {
                    selectedFragment = new ButtonFragment();
                } else if (id == R.id.nav_edittext) {
                    selectedFragment = new EditTextFragment();
                } else if (id == R.id.nav_radiobutton) {
                    selectedFragment = new RadioButtonFragment();
                } else if (id == R.id.nav_checkbox) {
                    selectedFragment = new CheckBoxFragment();
                } else if (id == R.id.nav_switch) {
                    selectedFragment = new SwitchFragment();
                } else if (id == R.id.nav_spinner) {
                    selectedFragment = new SpinnerFragment();
                } else if (id == R.id.nav_seekbar) {
                    selectedFragment = new SeekBarFragment();
                } else if (id == R.id.nav_textview) {
                    selectedFragment = new TextViewFragment();
                } else if (id == R.id.nav_imageview) {
                    selectedFragment = new ImageViewFragment();
                } else if (id == R.id.nav_progressbar) {
                    selectedFragment = new ProgressBarFragment();
                }

                if (selectedFragment != null) {
                    loadFragment(selectedFragment);
                    drawerLayout.closeDrawers();
                }
                return true;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
