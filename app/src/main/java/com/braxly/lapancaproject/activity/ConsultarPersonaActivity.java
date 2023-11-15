package com.braxly.lapancaproject.activity;

import static android.util.Base64.encodeToString;

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

public class ConsultarPersonaActivity extends AppCompatActivity {
    private EditText txtId, txtNombre, txtApellidos, txtTelefono, txtDni, txtCorreo;
    private ImageButton btnBuscar;
    private Button btnActualizar ,btnEliminiar;

    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_persona);

        txtId = (EditText) findViewById(R.id.txtId);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellidos = (EditText) findViewById(R.id.txtApellidos);
        txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        txtDni = (EditText) findViewById(R.id.txtDni);
        txtCorreo = (EditText) findViewById(R.id.txtCorreo);

        btnBuscar= (ImageButton) findViewById(R.id.BTNBuscar);
        btnActualizar= findViewById(R.id.BTNActualizar);
        btnEliminiar= findViewById(R.id.BTNEliminar);
        requestQueue = Volley.newRequestQueue(this);
        txtCorreo.setText(ConexionApi.AUTH);
    }
    public void consultarUsuario(View vista) {
        String id = txtId.getText().toString().trim();
        String url = ConexionApi.URL_BASE + "usuario/" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String clieNombres, clieApellidos, clieTelefono, clieDni, clieCorreo;
                        try {
                            String valor = response.get("Detalles").toString();
                            JSONArray arreglo = new JSONArray(valor);
                            JSONObject objeto = new JSONObject(arreglo.get(0).toString());

                            clieNombres = objeto.getString("usua_username");
                            clieApellidos = objeto.getString("usua_username");
                            clieTelefono = objeto.getString("usua_username");
                            clieDni = objeto.getString("usua_username");
                            clieCorreo = objeto.getString("usua_username");

                            txtNombre.setText(clieNombres);
                            txtApellidos.setText(clieApellidos);
                            txtTelefono.setText(clieTelefono);
                            txtDni.setText(clieDni);
                            txtCorreo.setText(clieCorreo);

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
            public Map<String, String> getHeaders() throws AuthFailureError{
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Authorization", ConexionApi.AUTH);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);

    }

    public void editarPersona(View vista){
        String id = txtId.getText().toString().trim();
        String url = ConexionApi.URL_BASE + "cliente/" + id;
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();

        // Objeto  que recibe como  parametro  5 compomentes (Request.Method.POST, url, new Response.Listener<String>(), new Response.ErrorListener() )
        // y como cuerpo:Map<String, String> getParams() que es la carga de datos a enviar

        StringRequest request = new StringRequest(
                Request.Method.PUT,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        txtNombre.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "error al editar: ", Toast.LENGTH_SHORT).show();
                        System.out.println(error.getMessage());

                    }
                }
        ){
            //cargando los datos a enviar
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parametros = new HashMap<>();
                parametros.put("clie_id", txtNombre.getText().toString());
                parametros.put("clie_nombres", txtNombre.getText().toString());
                parametros.put("clie_apellidos", txtApellidos.getText().toString());
                parametros.put("clie_dni", txtDni.getText().toString());
                parametros.put("clie_celular", txtTelefono.getText().toString());
                parametros.put("clie_correo", txtCorreo.getText().toString());


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

    public void borrarPersona(View vista){
        String id = txtId.getText().toString().trim();
        String url = ConexionApi.URL_BASE + "cliente/" + id;

        //System.out.println(id.getText().toString());
        //Toast.makeText(this, "registrado correctamente: " + id.getText().toString(), Toast.LENGTH_SHORT).show();

        // Objeto  que recibe como  parametro  5 compomentes (Request.Method.POST, url, new Response.Listener<String>(), new Response.ErrorListener() )
        // y como cuerpo:Map<String, String> getParams() que es la carga de datos a enviar

        StringRequest request = new StringRequest(
                Request.Method.DELETE,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "eliminado correctamente: ", Toast.LENGTH_SHORT).show();
                        System.out.println(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "error al eliminar: ", Toast.LENGTH_SHORT).show();
                        System.out.println(error.getMessage());

                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Authorization", ConexionApi.AUTH);
                return params;
            }
        };
        requestQueue.add(request);
    }
    public void saltarListadoPersona(View vista){

        Intent pasarMensaje = new Intent(this,ClienteActivity.class);
        //activamos el  Intent
        startActivity(pasarMensaje);
    }
    public void saltarRegistrarPersona(View vista){

        Intent pasarMensaje = new Intent(this,RegisterPersonaActivity.class);
        //activamos el  Intent
        startActivity(pasarMensaje);
    }
    public void saltarListaProducto(View vista){

        Intent pasarMensaje = new Intent(this,ListaProductosActivity.class);
        //activamos el  Intent
        startActivity(pasarMensaje);
    }

    public void Login(View vista){
        String username = txtId.getText().toString().trim();
        //String pass = txtPass.getText().toString().trim();
        Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();
        String url = ConexionApi.URL_BASE + "usuario/" + username;
        String url2 = "https://panca.informaticapp.com/producto";
        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(
                Request.Method.GET,
                url2,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String clieNombres, clieApellidos, clieTelefono, clieDni, clieCorreo;
                        try {
                            String valor = response.get("Detalles").toString();
                            JSONArray arreglo = new JSONArray(valor);
                            JSONObject objeto = new JSONObject(arreglo.get(0).toString());

                            clieNombres = objeto.getString("usua_username");
                            clieApellidos = objeto.getString("clie_apellidos");
                            clieTelefono = objeto.getString("clie_celular");
                            clieDni = objeto.getString("clie_dni");
                            clieCorreo = objeto.getString("clie_correo");
                            Toast.makeText(getApplicationContext(), clieNombres, Toast.LENGTH_SHORT).show();
                            txtCorreo.setText(clieNombres);

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
            public Map<String, String> getHeaders() throws AuthFailureError{
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Authorization", ConexionApi.AUTH);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest2);
    }
}