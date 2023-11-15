package com.braxly.lapancaproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
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
import com.braxly.lapancaproject.models.Cliente;
import com.braxly.lapancaproject.recyclerAdapters.ClienteAdapterRecycler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClienteActivity extends AppCompatActivity {
    private List<Cliente> listaCliente;
    private RequestQueue requestQueue;
    private RecyclerView rv1;
    private ClienteAdapterRecycler clienteAdapterRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        listaCliente = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        cargarPersona();
        rv1 = findViewById(R.id.recyclerView1);

        rv1.setLayoutManager(new LinearLayoutManager(this));
        clienteAdapterRecycler = new ClienteAdapterRecycler(this, listaCliente);
        rv1.setAdapter(clienteAdapterRecycler);
        Toast.makeText(getApplicationContext(), ConexionApi.AUTH, Toast.LENGTH_SHORT).show();
    }

    private void cargarPersona() {
        String url = Uri.parse(ConexionApi.URL_BASE + "/cliente")
                .buildUpon()
                .build().toString();
        JsonObjectRequest requerimiento = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
/*                              Cuenta con la funcion length cuantos datos tiene en "Detalles"
                                JSONArray detallesArray = response.getJSONArray("Detalles");
                                int numRegistros = detallesArray.length();*/

                            // Saca el dato del JSON "Total de registros"
                            int totalRegistros = response.getInt("Total de registros");
                            Toast.makeText(getApplicationContext(), "es: " + totalRegistros, Toast.LENGTH_SHORT).show();

                            for (int i = 0; i < totalRegistros; i++) {

                                String valor = response.get("Detalles").toString();
                                JSONArray arreglo = new JSONArray(valor);
                                JSONObject objeto = new JSONObject(arreglo.get(i).toString());

                                /*String perNombres = objeto.getString("per_nombres");
                                String perApellidos = objeto.getString("per_apellidos");

                                String perTelefono = objeto.getString("per_telefono");
                                String perDni = objeto.getString("per_dni");
                                String perCorreo = objeto.getString("per_correo");*/

                                String clieId = objeto.getString("clie_id");
                                String clieUsuaId = objeto.getString("clie_usua_id");
                                String clieNombres = objeto.getString("clie_nombres");
                                String clieApellidos = objeto.getString("clie_apellidos");
                                String clieDni = objeto.getString("clie_dni");
                                String clieCelular = objeto.getString("clie_celular");
                                String clieCorreo = objeto.getString("clie_correo");

                                // Toast.makeText(getApplicationContext(), "hola", Toast.LENGTH_SHORT).show();

                                //Persona persona = new Persona(perNombres, perApellidos, perTelefono, perDni, perCorreo);
                                Cliente cliente = new Cliente(clieId, clieUsuaId, clieNombres, clieApellidos, clieDni, clieCelular, clieCorreo);
                                listaCliente.add(cliente);
                                clienteAdapterRecycler.notifyItemRangeInserted(listaCliente.size(), 1);
                            }
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