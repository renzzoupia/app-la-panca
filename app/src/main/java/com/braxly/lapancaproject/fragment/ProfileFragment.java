package com.braxly.lapancaproject.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.braxly.lapancaproject.R;
import com.braxly.lapancaproject.activity.EditarClienteActivity;
import com.braxly.lapancaproject.activity.LoginActivity;
import com.braxly.lapancaproject.activity.MainActivity;
import com.braxly.lapancaproject.activity.RegistrarClienteActivity;
import com.braxly.lapancaproject.activity.RegistrarUsuarioActivity;
import com.braxly.lapancaproject.conexionApi.ConexionApi;
import com.braxly.lapancaproject.models.Reserva;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private TextView nombreCompletoTxt;
    private Button correoClienteTxt, direccionClienteTxt, btnEditarCliente, btnEliminarCliente;
    private RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        iniciarProfileFragment(rootView);
        editarCliente();
        cargarPreferencias();
        comprobarUsuario();
        eliminarCliente();
        return rootView;
    }

    private void iniciarProfileFragment(View rootView){
        nombreCompletoTxt = rootView.findViewById(R.id.txtNombreCompleto);
        correoClienteTxt = rootView.findViewById(R.id.profile_info);
        direccionClienteTxt = rootView.findViewById(R.id.order_history_btn);
        btnEditarCliente = rootView.findViewById(R.id.editarClienteBtn);
        btnEliminarCliente = rootView.findViewById(R.id.eliminarClienteBtn);
        requestQueue = Volley.newRequestQueue(requireContext());

    }
    private void comprobarUsuario(){
        SharedPreferences preferences = requireContext().getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        String clieIdRecibido = preferences.getString("clieId", "No existe la información");


        if (clieIdRecibido.equals("No existe la información")) {
            // Si el dato está ausente o es nulo, ocultas el botón
            correoClienteTxt.setVisibility(View.GONE);
            direccionClienteTxt.setVisibility(View.GONE);
            btnEditarCliente.setVisibility(View.GONE);
            btnEliminarCliente.setVisibility(View.GONE);
        } else {
            // Si el dato está presente, muestras el botón
            btnEditarCliente.setVisibility(View.VISIBLE);
            btnEliminarCliente.setVisibility(View.VISIBLE);
            correoClienteTxt.setVisibility(View.VISIBLE);
            direccionClienteTxt.setVisibility(View.VISIBLE);
        }
    }
    private void eliminarCliente(){
        btnEliminarCliente.setOnClickListener(view -> {
            // Crear un AlertDialog.Builder
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            // Configurar el mensaje y los botones
            builder.setMessage("¿Estás seguro?")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Acciones cuando se presiona OK

                            peticionEliminarCliente();

                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Acciones cuando se presiona Cancelar
                            dialog.cancel();
                        }
                    });

            // Crear el AlertDialog
            AlertDialog alert = builder.create();

            // Mostrar el AlertDialog
            alert.show();
        });
    }
    private void editarCliente(){

        btnEditarCliente.setOnClickListener(view -> {
            // Crear un AlertDialog.Builder
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            // Configurar el mensaje y los botones
            builder.setMessage("¿Estás seguro?")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Acciones cuando se presiona OK

                            Intent intentModificar = new Intent(requireContext(), EditarClienteActivity.class);
                            startActivity(intentModificar);

                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Acciones cuando se presiona Cancelar
                            dialog.cancel();
                        }
                    });

            // Crear el AlertDialog
            AlertDialog alert = builder.create();

            // Mostrar el AlertDialog
            alert.show();

        });


    }
    private void peticionEliminarCliente(){
        SharedPreferences preferences = requireActivity().getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        String clieIdRecibido = preferences.getString("clieId", "No existe la información");
        String usuaIdRecibido = preferences.getString("usuaId", "No existe la información");

        String url = Uri.parse(ConexionApi.URL_BASE + "/cliente/" + clieIdRecibido)
                .buildUpon()
                .build().toString();

        JsonObjectRequest requerimiento = new JsonObjectRequest(Request.Method.DELETE,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String Status = response.getString("Status");
                            Log.d("TAG", response.getString("Detalles"));
                            if(Status.equals("200")){

                                peticionEliminarUsuario(usuaIdRecibido);

                                //Se elimino correctamente
                            }else if(Status.equals("404")){
                                Toast.makeText(requireContext(), "Error al eliminar, intentelo mas tarde", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Authorization", ConexionApi.AUTH);
                return params;
            }
        };

        requestQueue.add(requerimiento);

    }

    private void peticionEliminarUsuario(String usuaIdRecbido){

        String url = Uri.parse(ConexionApi.URL_BASE + "/usuario/" + usuaIdRecbido)
                .buildUpon()
                .build().toString();

        JsonObjectRequest requerimiento = new JsonObjectRequest(Request.Method.DELETE,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String Status = response.getString("Status");
                            if(Status.equals("200")){

                                Toast.makeText(requireContext(), "Elimino su cuenta correctamente", Toast.LENGTH_SHORT).show();
                                ConexionApi conexionApi = new ConexionApi();
                                conexionApi.eliminarCredenciales(requireContext());
                                Intent i = new Intent(requireActivity(), MainActivity.class);
                                startActivity(i);
                            }else if(Status.equals("404")){
                                Toast.makeText(requireContext(), "Error al eliminar, intentelo mas tarde", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Authorization", ConexionApi.AUTH);
                return params;
            }
        };

        requestQueue.add(requerimiento);
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