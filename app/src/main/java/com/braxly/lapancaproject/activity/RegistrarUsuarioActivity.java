package com.braxly.lapancaproject.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braxly.lapancaproject.R;
import com.braxly.lapancaproject.conexionApi.ConexionApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrarUsuarioActivity extends AppCompatActivity {
    private EditText nombresTxt, apellidosTxt, dniTxt, correoTxt, celularTxt, usernameTxt, passwordTxt, direccionTxt, usuaIdTxt;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        iniciarRegistrarUsuarioActivity();
    }
    private void iniciarRegistrarUsuarioActivity(){
        usuaIdTxt = findViewById(R.id.txtClieUsuaId);
        nombresTxt = findViewById(R.id.txtNombresCompletos);
        apellidosTxt = findViewById(R.id.txtApellidosCompletos);
        dniTxt = findViewById(R.id.txtDni);
        correoTxt = findViewById(R.id.txtCorreo);
        celularTxt = findViewById(R.id.txtCelular);
        usernameTxt = findViewById(R.id.txtUsername);
        passwordTxt = findViewById(R.id.txtPassword);
        direccionTxt = findViewById(R.id.txtDireccion);

        // instancioando el objeto
        requestQueue = Volley.newRequestQueue(this);
    }
    public void registrarUsuarioCliente(View view){
        registrarUsuario();
        registrarCliente();
        Toast.makeText(getApplicationContext(), "registrado correctamente: ", Toast.LENGTH_SHORT).show();

        Intent pasarMensaje = new Intent(this,LoginActivity.class);
        //activamos el  Intent
        startActivity(pasarMensaje);
    }
    public void registrarUsuario(){
        String url = ConexionApi.URL_BASE + "usuario";

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            String usuaId;
                        //Toast.makeText(getApplicationContext(), "registrado correctamente: ", Toast.LENGTH_SHORT).show();
                        try {
                        JSONObject jsonObject = new JSONObject(response);

                        // Obtén el objeto "credenciales" del objeto principal
                        JSONObject credencialesObject = jsonObject.getJSONObject("credenciales");

                        // Obtén el valor de "usua_id" del objeto "credenciales"
                        usuaId = credencialesObject.getString("usua_id");
                        usuaIdTxt.setText(usuaId);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "error al registrar", Toast.LENGTH_SHORT).show();
                        //System.out.println(error.getMessage());

                    }
                }
        ){
            //cargando los datos a enviar
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parametros = new HashMap<>();

                parametros.put("usua_tius_id", "1");
                parametros.put("usua_username", usernameTxt.getText().toString());
                parametros.put("usua_pass", passwordTxt.getText().toString());


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

    public void registrarCliente(){
        String url = ConexionApi.URL_BASE + "cliente";

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getApplicationContext(), "registrado correctamente: ", Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "error al registrar", Toast.LENGTH_SHORT).show();
                        //System.out.println(error.getMessage());

                    }
                }
        ){
            //cargando los datos a enviar
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parametros = new HashMap<>();

                parametros.put("clie_usua_id", usuaIdTxt.getText().toString());
                parametros.put("clie_nombres", nombresTxt.getText().toString());
                parametros.put("clie_apellidos", apellidosTxt.getText().toString());
                parametros.put("clie_dni", dniTxt.getText().toString());
                parametros.put("clie_celular", celularTxt.getText().toString());
                parametros.put("clie_correo", correoTxt.getText().toString());
                parametros.put("clie_foto", nombresTxt.getText().toString() + ".jpg");
                parametros.put("clie_direccion", direccionTxt.getText().toString());


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