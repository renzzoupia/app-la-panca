package com.braxly.lapancaproject.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braxly.lapancaproject.R;
import com.braxly.lapancaproject.conexionApi.ConexionApi;

import java.util.HashMap;
import java.util.Map;

public class RegistrarClienteActivity extends AppCompatActivity {
    private EditText clienNombres, clienApellidos, clienDni, clieCelular, clieCorreo, clienDireccion;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cliente);
        iniciarActivity();
        Locura();
    }

    private void iniciarActivity(){
        clienNombres = findViewById(R.id.txtClienNombres);
        clienApellidos = findViewById(R.id.txtClienApellidos);
        clienDni = findViewById(R.id.txtClienDni);
        clieCelular = findViewById(R.id.txtClienCelular);
        clieCorreo = findViewById(R.id.txtClienCorreo);
        clienDireccion = findViewById(R.id.txtClienDireccion);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }
    public void registrarCliente(View view){
        String url = ConexionApi.URL_BASE + "cliente";

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Registrado correctamente", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);

                        startActivity(i);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            //cargando los datos a enviar
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parametros = new HashMap<>();
                String clienUsuaId = getIntent().getStringExtra("usuaId");
                parametros.put("clie_usua_id", clienUsuaId);
                parametros.put("clie_nombres", clienNombres.getText().toString());
                parametros.put("clie_apellidos", clienApellidos.getText().toString());
                parametros.put("clie_dni", clienDni.getText().toString());
                parametros.put("clie_celular", clieCelular.getText().toString());
                parametros.put("clie_correo", clieCorreo.getText().toString());
                parametros.put("clie_foto", clienNombres.getText().toString() + ".jpg");
                parametros.put("clie_direccion", clienDireccion.getText().toString());


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

    private void Locura(){
        clieCorreo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No se utiliza en este caso
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No se utiliza en este caso
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String email = clieCorreo.getText().toString().trim();

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    clieCorreo.setError("Ingrese un correo electrónico válido");
                    // Puedes deshabilitar un botón o realizar otra acción si el formato es incorrecto
                } else {
                    clieCorreo.setError(null);
                    // El formato es válido, realiza la acción correspondiente
                }
            }
        });
    }
}