package com.braxly.lapancaproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.braxly.lapancaproject.R;
import com.braxly.lapancaproject.conexionApi.ConexionApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RecuperarContraActivity extends AppCompatActivity {
    private EditText correoRecuperarTxt;
    private Context context;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contra);

        iniciarActivity();
    }

    private void iniciarActivity(){
        correoRecuperarTxt = findViewById(R.id.txtCorreoRecuperar);
        context = getApplicationContext();
        requestQueue = Volley.newRequestQueue(context);
    }

    public void buscarCorreo(View view){
        String correoRecuperar = correoRecuperarTxt.getText().toString().trim();
        String url = ConexionApi.URL_BASE + "recuperar";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
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
                                String usuaUsernameRecuperado = datos.getString("usua_username");
                                Toast.makeText(getApplicationContext(), usuaId, Toast.LENGTH_SHORT).show();
                                Intent pasarMainActivity = new Intent(context,RestablecerContraActivity.class);
                                pasarMainActivity.putExtra("usuaId", usuaId);
                                pasarMainActivity.putExtra("usuaUsername", usuaUsernameRecuperado);
                                startActivity(pasarMainActivity);
                            }
                            if(valor.equals("404")){
                                Toast.makeText(getApplicationContext(), "Correo no encontrado", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Correo no encontrado", Toast.LENGTH_SHORT).show();
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
                params.put("Correo", correoRecuperar);
                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }
}