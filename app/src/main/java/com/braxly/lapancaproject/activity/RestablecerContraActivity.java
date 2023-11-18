package com.braxly.lapancaproject.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braxly.lapancaproject.R;
import com.braxly.lapancaproject.conexionApi.ConexionApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RestablecerContraActivity extends AppCompatActivity {
    EditText contraRestablecer, contraRestablecerConfirmar, usuaIdRestablecerTxt, usernameRestablecerTxt;
    RequestQueue requestQueue;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecer_contra);

        iniciarActivity();
        coincidenContra();
    }

    public void confirmarRestablecerContra(View v){
        consultarUsuario();
        editarUsuario();

    }
    private void iniciarActivity(){
        contraRestablecer = findViewById(R.id.txtContraRestablecer);
        contraRestablecerConfirmar = findViewById(R.id.txtContraRestablecerConfirmar);

        usuaIdRestablecerTxt = findViewById(R.id.txtUsuaIdRestablecer);
        usernameRestablecerTxt = findViewById(R.id.txtUsernameRestablecer);

        context = getApplicationContext();
        requestQueue = Volley.newRequestQueue(context);
    }
    private void coincidenContra(){
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No es necesario implementar este método para tu caso
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No es necesario implementar este método para tu caso
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String password = contraRestablecer.getText().toString();
                String confirmPassword = contraRestablecerConfirmar.getText().toString();

                if (!password.equals(confirmPassword)) {
                    // Las contraseñas no coinciden, muestra un mensaje de error o realiza alguna acción
                    contraRestablecerConfirmar.setError("Las contraseñas no coinciden");
                } else {
                    // Las contraseñas coinciden, puedes quitar cualquier mensaje de error
                    contraRestablecerConfirmar.setError(null);
                }
            }
        };

// Agregar el TextWatcher a ambos EditText
        contraRestablecerConfirmar.addTextChangedListener(textWatcher);
        contraRestablecerConfirmar.addTextChangedListener(textWatcher);
    }

    private void consultarUsuario() {

        String usuaId = getIntent().getStringExtra("usuaId");
        String url = ConexionApi.URL_BASE + "usuario/" + usuaId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String valor = response.get("Detalles").toString();
                            JSONArray arreglo = new JSONArray(valor);
                            JSONObject objeto = new JSONObject(arreglo.get(0).toString());

                            String usuaTiusId = objeto.getString("usua_tius_id");
                            String usuaIdTxt = objeto.getString("usua_id");
                            String usuaUser = objeto.getString("usua_username");
                            String usuaPassword = objeto.getString("usua_pass");

                            usuaIdRestablecerTxt.setText(usuaIdTxt);
                            usernameRestablecerTxt.setText(usuaUser);

                            /*txtApellidos.setText(clieApellidos);
                            txtTelefono.setText(clieTelefono);
                            txtDni.setText(clieDni);
                            txtCorreo.setText(clieCorreo);*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error en la red", Toast.LENGTH_SHORT).show();
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
        requestQueue.add(jsonObjectRequest);

    }
    public void editarUsuario(){
        String usuaId = getIntent().getStringExtra("usuaId");
        //String id = usuaIdRestablecerTxt.getText().toString().trim();
        String url = ConexionApi.URL_BASE + "usuario/" + usuaId;
        StringRequest request = new StringRequest(
                Request.Method.PUT,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            String estadoReserva = jsonObject.getString("Status");
                            if (estadoReserva.equals("200")){
                                Toast.makeText(getApplicationContext(), "Cambio su contraseña correctamente", Toast.LENGTH_SHORT).show();
                                Intent pasarMensaje = new Intent(context,LoginActivity.class);
                                //activamos el  Intent
                                startActivity(pasarMensaje);
                            }
                            if(estadoReserva.equals("404")){
                                Toast.makeText(getApplicationContext(), "Error, intentelo nuevamente mas tarde", Toast.LENGTH_SHORT).show();
                                Intent pasarMensaje = new Intent(context,LoginActivity.class);
                                //activamos el  Intent
                                startActivity(pasarMensaje);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "error al editar", Toast.LENGTH_SHORT).show();
                        System.out.println(error.getMessage());

                    }
                }
        ){
            //cargando los datos a enviar
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parametros = new HashMap<>();
                String usuaUsernameRecuperadoPut = getIntent().getStringExtra("usuaUsername");
                parametros.put("usua_tius_id", "1");
                parametros.put("usua_username", usuaUsernameRecuperadoPut);
                parametros.put("usua_pass", contraRestablecerConfirmar.getText().toString());


                return parametros;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Authorization", ConexionApi.AUTH);
                return params;
            }
        };
        requestQueue.add(request);
    }
}