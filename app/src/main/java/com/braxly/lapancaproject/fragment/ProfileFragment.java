package com.braxly.lapancaproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.braxly.lapancaproject.R;
import com.braxly.lapancaproject.activity.RegistrarClienteActivity;
import com.braxly.lapancaproject.activity.RegistrarUsuarioActivity;

public class ProfileFragment extends Fragment {

    TextView nombreCompletoTxt;
    Button correoClienteTxt, direccionClienteTxt, btnEditarCliente;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        iniciarProfileFragment(rootView);
        editarCliente();
        cargarPreferencias();
        comprobarUsuario();
        return rootView;
    }

    private void iniciarProfileFragment(View rootView){
        nombreCompletoTxt = rootView.findViewById(R.id.txtNombreCompleto);
        correoClienteTxt = rootView.findViewById(R.id.profile_info);
        direccionClienteTxt = rootView.findViewById(R.id.order_history_btn);
        btnEditarCliente = rootView.findViewById(R.id.editarClienteBtn);

    }
    private void comprobarUsuario(){
        SharedPreferences preferences = requireContext().getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        String clieIdRecibido = preferences.getString("clieId", "No existe la información");


        if (clieIdRecibido.equals("No existe la información")) {
            // Si el dato está ausente o es nulo, ocultas el botón
            btnEditarCliente.setVisibility(View.GONE);
        } else {
            // Si el dato está presente, muestras el botón
            btnEditarCliente.setVisibility(View.VISIBLE);
        }
    }
    private void editarCliente(){
        btnEditarCliente.setOnClickListener(view -> {
            Intent intentModificar = new Intent(requireContext(), RegistrarClienteActivity.class);
            intentModificar.putExtra("MODO", "MODIFICACION");

            startActivity(intentModificar);
        });
    }

    private void cargarPreferencias(){
        SharedPreferences preferences = requireContext().getSharedPreferences("Credenciales", Context.MODE_PRIVATE);

        String clieDireccionRecibido = preferences.getString("clieDireccion", "No existe la información");
        String clieCorreoRecibido = preferences.getString("clieCorreo", "No existe la información");
        String clieNombresRecibido = preferences.getString("clieNombres", "No existe la información");
        String clieApellidosRecbido = preferences.getString("clieApellidos", "No existe la información");

        if(!(clieNombresRecibido.equals("No existe la información") && clieApellidosRecbido.equals("No existe la información") && clieCorreoRecibido.equals("No existe la información") && clieDireccionRecibido.equals("No existe la información"))){
            nombreCompletoTxt.setText(clieNombresRecibido + " " + clieApellidosRecbido);
            correoClienteTxt.setText(clieCorreoRecibido);
            direccionClienteTxt.setText(clieDireccionRecibido);
        }else{
            nombreCompletoTxt.setText("Debe iniciar sesión");
            correoClienteTxt.setText("");
            direccionClienteTxt.setText("");
        }

    }
}