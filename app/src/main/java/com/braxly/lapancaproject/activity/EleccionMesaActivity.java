package com.braxly.lapancaproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
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
import com.braxly.lapancaproject.models.Mesa;
import com.braxly.lapancaproject.models.Producto;
import com.braxly.lapancaproject.recyclerAdapters.ClienteAdapterRecycler;
import com.braxly.lapancaproject.recyclerAdapters.MesaAdapterRecycler;
import com.braxly.lapancaproject.recyclerAdapters.ProductoAdapterRecycler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EleccionMesaActivity extends AppCompatActivity {
    private List<Mesa> mesas;
    private RequestQueue requestQueue;
    private RecyclerView mesaRecycler;
    private MesaAdapterRecycler mesaAdapterRecycler;
    private TextView btnRegresarInicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleccion_mesa);

        regresarInicio();
        iniciarActivity();
        cargarMesas();
    }
    private void iniciarActivity(){
        mesas = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        mesaRecycler = findViewById(R.id.recyclerMesa);

        mesaRecycler.setLayoutManager(new LinearLayoutManager(this));
        mesaAdapterRecycler = new MesaAdapterRecycler(this, mesas);
        mesaRecycler.setAdapter(mesaAdapterRecycler);
    }
    private void regresarInicio(){
        btnRegresarInicio = findViewById(R.id.goback);
        btnRegresarInicio.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        });
    }
    private void cargarMesas() {
        String url = Uri.parse(ConexionApi.URL_BASE + "/mesa")
                .buildUpon()
                .build().toString();

        JsonObjectRequest requerimiento = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            int totalRegistros = response.getInt("Total de registros");


                            for (int i = 0; i < totalRegistros; i++) {

                                String valor = response.get("Detalles").toString();
                                JSONArray arreglo = new JSONArray(valor);
                                JSONObject objeto = new JSONObject(arreglo.get(i).toString());

                                String mesaId = objeto.getString("mesa_id");
                                String mesaRestId = objeto.getString("mesa_rest_id");
                                String mesaNumero = objeto.getString("mesa_numero");
                                String mesaCantidadPersonas = objeto.getString("mesa_cantidad_personas");
                                String mesaReferenciaUbicacion = objeto.getString("mesa_referencia_ubicacion");
                                String mesaActivo = objeto.getString("mesa_activo");
                                String mesaEstado = objeto.getString("mesa_estado");

                                Mesa mesa = new Mesa(mesaId, mesaRestId, mesaNumero, mesaCantidadPersonas, mesaReferenciaUbicacion, mesaActivo, mesaEstado);

                                mesas.add(mesa);
                                mesaAdapterRecycler.notifyItemRangeInserted(mesas.size(), 1);
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