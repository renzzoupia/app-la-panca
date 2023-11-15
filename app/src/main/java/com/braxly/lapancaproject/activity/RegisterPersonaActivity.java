package com.braxly.lapancaproject.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;
import java.util.Map;

public class RegisterPersonaActivity extends AppCompatActivity {
    private EditText id, nombre, apellidos, telefono,dni, correo;

    //objeto  del tipo RequestQueue
    RequestQueue requestQueue;
    private static final String url = "https://panca.informaticapp.com/personas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_persona);

        id = (EditText) findViewById(R.id.txtId);
        nombre = (EditText) findViewById(R.id.txtNombre);
        apellidos = (EditText) findViewById(R.id.txtApellidos);
        telefono = (EditText) findViewById(R.id.txtTelefono);
        dni = (EditText) findViewById(R.id.txtDni);
        correo = (EditText) findViewById(R.id.txtCorreo);

        // instancioando el objeto
        requestQueue = Volley.newRequestQueue(this);
    }
    public void RegistrarDatos(View vista){

        //System.out.println(id.getText().toString());
        //Toast.makeText(this, "registrado correctamente: " + id.getText().toString(), Toast.LENGTH_SHORT).show();

        // Objeto  que recibe como  parametro  5 compomentes (Request.Method.POST, url, new Response.Listener<String>(), new Response.ErrorListener() )
        // y como cuerpo:Map<String, String> getParams() que es la carga de datos a enviar
        String url = ConexionApi.URL_BASE + "personas";

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "registrado correctamente: ", Toast.LENGTH_SHORT).show();

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

                parametros.put("per_nombres", nombre.getText().toString());
                parametros.put("per_apellidos", apellidos.getText().toString());
                parametros.put("per_telefono", telefono.getText().toString());
                parametros.put("per_dni", dni.getText().toString());
                parametros.put("per_correo", correo.getText().toString());


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