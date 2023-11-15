package com.braxly.lapancaproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.braxly.lapancaproject.R;
import com.braxly.lapancaproject.StartApp;
import com.braxly.lapancaproject.conexionApi.ConexionApi;
import com.braxly.lapancaproject.models.Producto;
import com.braxly.lapancaproject.recyclerAdapters.ProductoAdapterRecycler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private List<Producto> productos; //agregacion
    private RequestQueue requestQueue;
    private ProductoAdapterRecycler productoAdapterRecycler;
    private RecyclerView recyclerViewProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//asosiacion
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cargarListaProductos();

        iniciarRecyclerViewProductos();
        bottomNavigation();
        Toast.makeText(getApplicationContext(), ConexionApi.AUTH_LOGIN, Toast.LENGTH_SHORT).show();
    }

    private void bottomNavigation(){
        LinearLayout HomeBtn = findViewById(R.id.homeBtn);
        LinearLayout CartBtn = findViewById(R.id.cartBtn);

        HomeBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MainActivity.class)));

        CartBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CartaActivity.class)));
    }
    private void iniciarRecyclerViewProductos(){

        productos = new ArrayList<>();
        recyclerViewProducto = findViewById(R.id.recyclerProductos);
        recyclerViewProducto.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        productoAdapterRecycler = new ProductoAdapterRecycler(productos);
        recyclerViewProducto.setAdapter(productoAdapterRecycler);
    }
    private void cargarListaProductos() {

        requestQueue=Volley.newRequestQueue(getApplicationContext());

        String url = Uri.parse(ConexionApi.URL_BASE + "/producto")
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

                                String prodId = objeto.getString("prod_id");
                                String prodTiprId = objeto.getString("prod_tipr_id");
                                String tiprNombre = objeto.getString("tipr_nombre");
                                String prodNombre = objeto.getString("prod_nombre");
                                String prodDescripcion = objeto.getString("prod_descripcion");
                                String prodStock = objeto.getString("prod_stock");
                                Double prodPrecio = Double.parseDouble(objeto.getString("prod_precio"));
                                String prodFoto = objeto.getString("prod_foto");
                                // Toast.makeText(getApplicationContext(), "hola", Toast.LENGTH_SHORT).show();

                                Producto producto = new Producto(prodId, prodTiprId, tiprNombre, prodNombre, prodDescripcion, prodStock, prodPrecio, prodFoto);

                                productos.add(producto);
                                productoAdapterRecycler.notifyItemRangeInserted(productos.size(), 1);
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
    public void pasarVistaReserva(View view){
        Intent i = new Intent(MainActivity.this, EleccionMesaActivity.class);
        startActivity(i);
    }

}