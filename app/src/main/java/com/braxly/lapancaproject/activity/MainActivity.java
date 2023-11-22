package com.braxly.lapancaproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.braxly.lapancaproject.MailJob;
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
        verificarLogin();
        verificarMostrarCerrarSesion();
        if (!verificarConexionInternet()) {
            // Si no hay conexión a Internet, muestra un mensaje o acción adecuada y cierra la actividad
            Toast.makeText(this, "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

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
        cargarPreferenciasModificarHeader();

    }
    @Override
    protected void onResume() {
        super.onResume();
        // Realizar las actualizaciones necesarias aquí
        cargarPreferenciasModificarHeader();
    }
    private boolean verificarConexionInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
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
            /*case R.id.nav_compra:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ComprasFragment()).commit();
                break;*/
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
                ConexionApi conexionApi = new ConexionApi();
                conexionApi.eliminarCredenciales(getApplicationContext());
                cargarPreferenciasModificarHeader();
                Intent pasarMensaje = new Intent(getApplicationContext(), MainActivity.class);
                //activamos el  Intent
                startActivity(pasarMensaje);
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
    /*private void eliminarCredenciales(){
        SharedPreferences.Editor editor = getSharedPreferences("Credenciales", MODE_PRIVATE).edit();

        // Remueve los datos que quieres eliminar (correo, id_cliente)
        editor.remove("clieId");
        editor.remove("usuaId");
        editor.remove("clieDireccion");
        editor.remove("clieCorreo");
        editor.remove("usuaUsername");
        editor.remove("clieNombres");
        editor.remove("clieApellidos");

        // Aplica los cambios
        editor.apply();
    }*/
    public void cargarPreferenciasModificarHeader() {

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        TextView txtNombre = headerView.findViewById(R.id.txtNombreHeader);
        TextView txtCorreo = headerView.findViewById(R.id.txtCorreoClienteHeader);

        SharedPreferences preferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);

        String clieCorreoRecibido = preferences.getString("clieCorreo", "No existe la información");
        String usuaUsernameRecibido = preferences.getString("usuaUsername", "No existe la información");


        if (clieCorreoRecibido.equals("No existe la información") && usuaUsernameRecibido.equals("No existe la información")) {
            txtNombre.setText("Usuario no registrado");
            txtCorreo.setText("");
        } else {
            txtNombre.setText(usuaUsernameRecibido);
            txtCorreo.setText(clieCorreoRecibido);
        }
    }

    public void verificarLogin() {

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        MenuItem loginItem = navigationView.getMenu().findItem(R.id.nav_login);

        SharedPreferences preferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);

        String clieCorreoRecibido = preferences.getString("clieCorreo", "No existe la información");
        String usuaUsernameRecibido = preferences.getString("usuaUsername", "No existe la información");


        if (clieCorreoRecibido.equals("No existe la información") && usuaUsernameRecibido.equals("No existe la información")) {
            loginItem.setVisible(true);
        } else {
            loginItem.setVisible(false);
        }
    }
    public void verificarMostrarCerrarSesion() {

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        MenuItem cerrarSesionItem = navigationView.getMenu().findItem(R.id.nav_logout);

        SharedPreferences preferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);

        String clieCorreoRecibido = preferences.getString("clieCorreo", "No existe la información");
        String usuaUsernameRecibido = preferences.getString("usuaUsername", "No existe la información");


        if (clieCorreoRecibido.equals("No existe la información") && usuaUsernameRecibido.equals("No existe la información")) {
            cerrarSesionItem.setVisible(false);
        } else {
            cerrarSesionItem.setVisible(true);
        }
    }

}