package com.braxly.lapancaproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
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
    private EditText txtUsername, txtPass;
    private Context context;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniciarLoginActivity();
    }
    private void iniciarLoginActivity(){
        txtUsername = findViewById (R.id.txtUsuario);
        txtPass = findViewById(R.id.txtContra);

        context = getApplicationContext();

        requestQueue = Volley.newRequestQueue(context);
    }
    /*public void iniciarSesion(View vista) {

        String usuario = txtUsername.getText().toString().trim();
        String contra = txtPass.getText().toString().trim();

        String url = ConexionApi.URL_BASE + "usuario/" + usuario + "&" + contra;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String usuarioBd;
                        try {
                            String valor = response.get("Detalles").toString();
                            JSONArray arreglo = new JSONArray(valor);
                            JSONObject objeto = new JSONObject(arreglo.get(0).toString());

                            usuarioBd = objeto.getString("usua_username");

                            Intent pasarMainActivity = new Intent(context,MainActivity.class);
                            //activamos el  Intent
                            startActivity(pasarMainActivity);

                            Toast.makeText(getApplicationContext(), "Bienvenido " + usuarioBd, Toast.LENGTH_SHORT).show();


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

                );
            requestQueue.add(jsonObjectRequest);

    }*/
    public void iniciarSesion2(View vista) {

        String usuario = txtUsername.getText().toString().trim();
        String contra = txtPass.getText().toString().trim();
        String url2 = ConexionApi.URL_BASE + "login";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url2,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            /*String Detalles = response.get("Datos").toString();
                            JSONArray arreglo = new JSONArray(Detalles);
                            JSONObject objeto = new JSONObject(arreglo.get(0).toString());*/
                            String valor = response.get("Status").toString();


                            if(valor.equals("200")){
                                JSONObject datos = response.getJSONObject("Datos");
                                String usuaId = datos.getString("usua_id");
                                String usuaUsername = datos.getString("usua_username");
                                String usuaUserSecreto = datos.getString("usua_user_secreto");
                                String usuaLlaveSecreta = datos.getString("usua_llave_secreta");

                                ConexionApi.userSecreto = usuaUserSecreto;
                                ConexionApi.llaveSecreta = usuaLlaveSecreta;
                                Toast.makeText(getApplicationContext(), ConexionApi.llaveSecreta, Toast.LENGTH_SHORT).show();
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

    public void pasarCrearCuenta(View vista){
        Intent pasarMensaje = new Intent(this,RegistrarUsuarioActivity.class);
        //activamos el  Intent
        startActivity(pasarMensaje);
    }


}