package com.braxly.lapancaproject.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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
import com.braxly.lapancaproject.models.Cliente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditarClienteActivity extends AppCompatActivity {
    private EditText clieNombresTxt, clieApellidosTxt, clieDniTxt, clieCelularTxt, clieCorreoTxt, clieDireccionTxt;
    RequestQueue requestQueue;
    private List<Cliente> cliente;
    private TextView registrarModificar;
    private ImageButton GoBackEliminarCliente;
    private Button modificarDatosBtn;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);

        iniciarActivity();
        traerCliente();
        regresar();
    }
    private void iniciarActivity(){

        clieNombresTxt = findViewById(R.id.txtClienNombres);
        clieApellidosTxt = findViewById(R.id.txtClienApellidos);
        clieDniTxt = findViewById(R.id.txtClienDni);
        clieCelularTxt = findViewById(R.id.txtClienCelular);
        clieCorreoTxt = findViewById(R.id.txtClienCorreo);
        clieDireccionTxt = findViewById(R.id.txtClienDireccion);
        GoBackEliminarCliente = findViewById(R.id.goBackModificarDatos);
        registrarModificar = findViewById(R.id.txtTituloRegistrarModificar);
        modificarDatosBtn = findViewById(R.id.btnModificarDatos);
        context = getApplicationContext();
        requestQueue = Volley.newRequestQueue(context);
    }
    private void regresar(){
        GoBackEliminarCliente.setOnClickListener(view -> {
            Intent intentModificar = new Intent(context, MainActivity.class);
            Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
            startActivity(intentModificar);
        });
    }
    private void editarCliente(){

        SharedPreferences preferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        String clieIdRecibido = preferences.getString("clieId", "No existe la información");

        String url = Uri.parse(ConexionApi.URL_BASE + "cliente/" + clieIdRecibido)
                .buildUpon()
                .build().toString();
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
                                Toast.makeText(getApplicationContext(), "Editado correctamente", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), "Inicie nuevamente", Toast.LENGTH_SHORT).show();

                                ConexionApi conexionApi = new ConexionApi();
                                conexionApi.eliminarCredenciales(context);


                                Intent pasarMensaje = new Intent(context,LoginActivity.class);
                                //activamos el  Intent
                                startActivity(pasarMensaje);
                            }
                            if(estadoReserva.equals("404")){
                                Toast.makeText(getApplicationContext(), "Error, intentelo nuevamente mas tarde", Toast.LENGTH_SHORT).show();
                                Intent pasarMensaje = new Intent(context,MainActivity.class);
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
                        Toast.makeText(getApplicationContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            //cargando los datos a enviar
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parametros = new HashMap<>();
                String usuaIdRecibido = preferences.getString("usuaId", "No existe la información");

                parametros.put("clie_usua_id", usuaIdRecibido);
                parametros.put("clie_nombres", clieNombresTxt.getText().toString());
                parametros.put("clie_apellidos", clieApellidosTxt.getText().toString());
                parametros.put("clie_dni", clieDniTxt.getText().toString());
                parametros.put("clie_celular", clieCelularTxt.getText().toString());
                parametros.put("clie_correo", clieCorreoTxt.getText().toString());
                parametros.put("clie_foto", clieNombresTxt.getText().toString() + ".jpg");
                parametros.put("clie_direccion", clieDireccionTxt.getText().toString());


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

    private void traerCliente(){

        SharedPreferences preferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        String clieIdRecibido = preferences.getString("clieId", "No existe la información");

        String url = Uri.parse(ConexionApi.URL_BASE + "cliente/" + clieIdRecibido)
                .buildUpon()
                .build().toString();
        JsonObjectRequest requerimiento = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String valor = response.get("Detalles").toString();
                            JSONArray arreglo = new JSONArray(valor);
                            JSONObject objeto = new JSONObject(arreglo.get(0).toString());

                            String clieId = objeto.getString("clie_id");
                            String clieUsuaId = objeto.getString("clie_usua_id");
                            String clieNombres = objeto.getString("clie_nombres");
                            String clieApellidos = objeto.getString("clie_apellidos");
                            String clieDni = objeto.getString("clie_dni");
                            String clieCelular = objeto.getString("clie_celular");
                            String clieCorreo = objeto.getString("clie_correo");
                            String clieDireccion = objeto.getString("clie_direccion");


                            //Cliente clienteId = new Cliente(clieId,clieUsuaId,clieNombres,clieApellidos,clieDni,clieCelular,clieCorreo,clieDireccion);
                            ///String tipoEspecifico = "1";

                            clieNombresTxt.setText(clieNombres);
                            clieApellidosTxt.setText(clieApellidos);
                            clieDniTxt.setText(clieDni);
                            clieCelularTxt.setText(clieCelular);
                            clieCorreoTxt.setText(clieCorreo);
                            clieDireccionTxt.setText(clieDireccion);

                            modificarDatosBtn.setOnClickListener(view -> {
                                editarCliente();
                            });


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
}