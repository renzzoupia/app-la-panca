package com.braxly.lapancaproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;

import com.braxly.lapancaproject.conexionApi.ConexionApi;
import com.braxly.lapancaproject.fragment.AboutFragment;
import com.braxly.lapancaproject.fragment.ComprasFragment;
import com.braxly.lapancaproject.fragment.HomeFragment;
import com.braxly.lapancaproject.fragment.ProfileFragment;
import com.braxly.lapancaproject.R;
import com.braxly.lapancaproject.fragment.ReserveFragment;
import com.google.android.material.navigation.NavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        modificarHeader();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_reserve:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ReserveFragment()).commit();
                break;
            case R.id.nav_compra:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ComprasFragment()).commit();
                break;
            case R.id.nav_mi_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;
            case R.id.nav_login:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).commit();
                break;
            case R.id.nav_logout:
                ConexionApi.clieUsuario ="";
                ConexionApi.clieCorreo = "";
                modificarHeader();
                Toast.makeText(this, "Usted cerro sesión", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
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

    public void modificarHeader(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        TextView txtNombre = headerView.findViewById(R.id.txtNombreHeader);
        TextView txtCorreo = headerView.findViewById(R.id.txtCorreoClienteHeader);
        String usuario = ConexionApi.clieUsuario;
        String correo = ConexionApi.clieCorreo;
        if (usuario.isEmpty() && correo.isEmpty()) {
            txtNombre.setText("Usuario no registrado");
            txtCorreo.setText("");
        } else {
            txtNombre.setText(usuario);
            txtCorreo.setText(correo);
        }

    }

}