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
import com.braxly.lapancaproject.models.Producto;
import com.braxly.lapancaproject.recyclerAdapters.ClienteAdapterRecycler;
import com.braxly.lapancaproject.recyclerAdapters.ProductoAdapterRecycler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaProductosActivity extends AppCompatActivity {
    private List<Producto> productos;
    private RequestQueue requestQueue;
    private RecyclerView rv1;
    private ProductoAdapterRecycler productoAdapterRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        productos = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        cargarProducto();
        rv1 = findViewById(R.id.recyclerView1);

        rv1.setLayoutManager(new LinearLayoutManager(this));
       // productoAdapterRecycler = new ProductoAdapterRecycler(this, listaProducto);
        rv1.setAdapter(productoAdapterRecycler);
    }
    private void cargarProducto() {
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
                            Toast.makeText(getApplicationContext(), "es: " + totalRegistros, Toast.LENGTH_SHORT).show();

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
}