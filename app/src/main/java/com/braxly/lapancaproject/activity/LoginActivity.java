package com.braxly.lapancaproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.braxly.lapancaproject.R;
import com.braxly.lapancaproject.conexionApi.ConexionApi;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText txtUsername, txtPass; //Creando las variables que se usaran en el activity
    private ImageButton goBackBtn;
    private Context context;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniciarLoginActivity();
        regresarMain();
    }
    private void iniciarLoginActivity(){
        txtUsername = findViewById (R.id.txtUsuario);
        txtPass = findViewById(R.id.txtContra);
        goBackBtn = findViewById(R.id.go_back);

        context = getApplicationContext();

        requestQueue = Volley.newRequestQueue(context); //Volley libreria q permite peticiones HTTP
    }
    private void regresarMain(){
        goBackBtn.setOnClickListener(view ->
        {
            Intent pasarMensaje = new Intent(getApplicationContext(), MainActivity.class);
            //activamos el  Intent
            startActivity(pasarMensaje);
        });
    }
    public void iniciarSesion2(View vista) {

        String usuario = txtUsername.getText().toString().trim();
        String contra = txtPass.getText().toString().trim();

        String url = ConexionApi.URL_BASE + "login";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(//instanciado JsonObjectRequest
                Request.Method.POST,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String valor = response.get("Status").toString();

                            if(valor.equals("200")){
                                JSONObject datos = response.getJSONObject("Datos");

                                String clieId = datos.getString("clie_id");

                                String usuaId = datos.getString("usua_id");
                                String usuaUsername = datos.getString("usua_username");
                                //String usuaLlaveSecreta = datos.getString("usua_llave_secreta");

                                String clieDireccion = datos.getString("clie_direccion");
                                String clieCorreo = datos.getString("clie_correo");
                                String clieNombres = datos.getString("clie_nombres");
                                String clieApellidos = datos.getString("clie_apellidos");

                                guardarPreferencias(clieId,usuaId,clieDireccion, clieCorreo, usuaUsername, clieNombres, clieApellidos);

                                Intent pasarMainActivity = new Intent(context,MainActivity.class);
                                //activamos el  Intent
                                startActivity(pasarMainActivity);
                            }
                            if(valor.equals("404")){
                                Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error en la red", Toast.LENGTH_SHORT).show();
                    }
                }

        )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Usuario", usuario);
                params.put("Contra", contra);
                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);

    }
    private void guardarPreferencias(String clieId, String usuaId, String clieDireccion, String clieCorreo, String usuaUsername, String clieNombres, String clieApellidos){
        SharedPreferences preferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("clieId", clieId);
        editor.putString("usuaId", usuaId);
        editor.putString("clieDireccion", clieDireccion);
        editor.putString("clieCorreo", clieCorreo);
        editor.putString("usuaUsername", usuaUsername);
        editor.putString("clieNombres", clieNombres);
        editor.putString("clieApellidos", clieApellidos);


        editor.commit();
    }

    private void cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        String clieIdRecibido = preferences.getString("clieId", "No existe la información");
        String usuaIdRecibido = preferences.getString("usuaId", "No existe la información");
        String clieDireccionRecibido = preferences.getString("clieDireccion", "No existe la información");
        String clieCorreoRecibido = preferences.getString("clieCorreo", "No existe la información");
        String usuaUsernameRecibido = preferences.getString("usuaUsername", "No existe la información");
        String clieNombresRecibido = preferences.getString("clieNombres", "No existe la información");
        String clieApellidosRecibido = preferences.getString("clieApellidos", "No existe la información");
    }

    public void pasarCrearCuenta(View vista){
        Intent pasarMensaje = new Intent(getApplicationContext(),RegistrarUsuarioActivity.class);
        //activamos el  Intent
        startActivity(pasarMensaje);
    }

    public void pasarOlvideContra(View vista){
        Intent pasarMensaje = new Intent(getApplicationContext(),RecuperarContraActivity.class);
        //activamos el  Intent
        startActivity(pasarMensaje);
    }


}