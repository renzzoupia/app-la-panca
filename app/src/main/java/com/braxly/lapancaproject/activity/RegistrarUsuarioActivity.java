package com.braxly.lapancaproject.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    private EditText usuarioTxt, contrasenaTxt, usuaIdTxt;
    private ImageButton goBackBtn;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        iniciarActivity();
        ola();
        regresarInicio();
    }

    private void iniciarActivity(){
        usuarioTxt = findViewById(R.id.txtUsuarioRegistrar);
        contrasenaTxt = findViewById(R.id.txtContraRegistrar);
        usuaIdTxt = findViewById(R.id.txtUsuaId);

        goBackBtn = findViewById(R.id.go_back);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }
    private void regresarInicio(){
        goBackBtn.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);

            startActivity(i);
        });
    }
private void ola(){
    Button button= (Button) findViewById(R.id.btnRegistrarUsuario);
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            registrarUsuario();
            //Intent i = new Intent(getApplicationContext(), RegistrarClienteActivity.class);
            //i.putExtra("usuaId", usuaId);
            //startActivity(i);
        }
    });
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

                            Intent intentRegistrar = new Intent(getApplicationContext(), RegistrarClienteActivity.class);
                            intentRegistrar.putExtra("usuaId", usuaId);
                            startActivity(intentRegistrar );


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
                parametros.put("usua_username", usuarioTxt.getText().toString());
                parametros.put("usua_pass", contrasenaTxt.getText().toString());


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