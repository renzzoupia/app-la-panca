package com.braxly.lapancaproject.fragment;

import static com.android.volley.VolleyLog.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.braxly.lapancaproject.R;
import com.braxly.lapancaproject.activity.CartaActivity;
import com.braxly.lapancaproject.activity.EleccionMesaActivity;
import com.braxly.lapancaproject.activity.LoginActivity;
import com.braxly.lapancaproject.activity.MainActivity;
import com.braxly.lapancaproject.activity.ReservaActivity;
import com.braxly.lapancaproject.conexionApi.ConexionApi;
import com.braxly.lapancaproject.models.Producto;
import com.braxly.lapancaproject.models.TipoProducto;
import com.braxly.lapancaproject.recyclerAdapters.ListaProductoAdapterRecycler;
import com.braxly.lapancaproject.recyclerAdapters.ProductoAdapterRecycler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener{
    FloatingActionButton agregar;
    private static List<Producto> productos; //agregacion
    private ArrayList<TipoProducto> listaTipoProducto;
    private static RequestQueue requestQueue;
    private static ProductoAdapterRecycler productoAdapterRecycler;
    private ListaProductoAdapterRecycler listaProductoAdapterRecycler;
    private RecyclerView recyclerViewProducto, recyclerViewListaTipoProducto;
    private TextView pasarVistaReserva;
    private String tipoFiltrado = "";
    private SearchView txtBuscar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cargarListaProductos(requireContext());
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        //iniciarActivity(rootView);
        recyclerViewTipoProducto(rootView);
        iniciarRecyclerViewProductos(rootView);
        pasarVistaDeLaReserva(rootView);

        /*accionFiltrarPollo(rootView);
        accionFiltrarRefrescos(rootView);
        accionFiltrarGaseosas(rootView);
        accionFiltrarAguaMineral(rootView);*/
        /*TextView textView = rootView.findViewById(R.id.txtReservaAhora);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(requireActivity(), EleccionMesaActivity.class);
                startActivity(i);
                Toast.makeText(requireContext(), "Clic en el botón", Toast.LENGTH_SHORT).show();
            }
        });*/
        //bottomNavigation(rootView);
        return rootView;
    }
    private void iniciarActivity(View rootView){
        //txtBuscar = rootView.findViewById(R.id.txtBuscarProductos);

        recyclerViewListaTipoProducto = rootView.findViewById(R.id.recyclerTipoProductos);

        txtBuscar.setOnQueryTextListener(this);
    }
    private void iniciarRecyclerViewProductos(View rootView) {

        //listaFiltrada = new ArrayList<>();

        productos = new ArrayList<>();

        recyclerViewProducto = rootView.findViewById(R.id.recyclerProductos);
        recyclerViewProducto.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        productoAdapterRecycler = new ProductoAdapterRecycler(productos);
        recyclerViewProducto.setAdapter(productoAdapterRecycler);

        agregar = rootView.findViewById(R.id.FABCarta);


        //escuchando al FAB principal
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = requireContext().getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
                String clieIdRecibido = preferences.getString("clieId", "No existe la información");

                if(clieIdRecibido.equals("No existe la información")){
                    Toast.makeText(requireContext(), "Debes iniciar sesión", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(requireActivity(), LoginActivity.class);
                    startActivity(i);
                }else{
                    Intent vistaSaltar = new Intent(requireContext(), CartaActivity.class);
                    startActivity(vistaSaltar);
                }

            }
        });
    }

    public static void cargarListaProductos(Context context) {

        requestQueue = Volley.newRequestQueue(context);
        String url = Uri.parse(ConexionApi.URL_BASE + "producto")
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
                                ///String tipoEspecifico = "1";
                                //if (producto.getProdId().equals(tipoEspecifico)) {
                                productos.add(producto);
                                //
                                productoAdapterRecycler.notifyItemRangeInserted(productos.size(),1);
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

    public void pasarVistaDeLaReserva(View rootView) {
        pasarVistaReserva = rootView.findViewById(R.id.txtReservaAhora);
        pasarVistaReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = requireContext().getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
                String clieIdRecibido = preferences.getString("clieId", "No existe la información");

                if(clieIdRecibido.equals("No existe la información")){
                    Toast.makeText(requireContext(), "Debes iniciar sesión", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(requireActivity(), LoginActivity.class);
                    startActivity(i);
                }else{
                    Intent vistaSaltar = new Intent(requireContext(), EleccionMesaActivity.class);
                    startActivity(vistaSaltar);
                }

            }
        });
    }
/*
    public void accionFiltrarPollo(View rootView) {
        ConstraintLayout constraintLayout = rootView.findViewById(R.id.btnFiltrarPollos);
        final boolean[] activado = {false};
        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!activado[0]) {
                            // Cambiar el color del fondo al presionar solo si está desactivado

                            constraintLayout.setBackgroundResource(R.drawable.background_selector);

                            filtrar("1");
                        }
                        return true;

                    case MotionEvent.ACTION_UP:
                        if (activado[0]) {
                            // Si ya estaba activado, desactivar y restaurar el color original
                            constraintLayout.setBackgroundResource(R.drawable.category_background); // Color original (blanco en este caso)

                            actualizarListaProductos();
                            activado[0] = false;
                        } else if (!activado[0]) {
                            // Si no estaba activado y se levanta el dedo, activar y mantener el color naranja
                            constraintLayout.setBackgroundResource(R.drawable.background_selector); // Color naranja
                            activado[0] = true;
                        }
                        return true;
                }
                return false;
            }
        });
    }

    public void accionFiltrarRefrescos(View rootView) {
        ConstraintLayout constraintLayout = rootView.findViewById(R.id.btnFiltrarRefrescos);
        final boolean[] activado = {false};
        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!activado[0]) {
                            // Cambiar el color del fondo al presionar solo si está desactivado
                            constraintLayout.setBackgroundResource(R.drawable.background_selector); // Color naranja
                            filtrar("2");

                        }
                        return true;

                    case MotionEvent.ACTION_UP:
                        if (activado[0]) {
                            // Si ya estaba activado, desactivar y restaurar el color original
                            constraintLayout.setBackgroundResource(R.drawable.category_background); // Color original (blanco en este caso)

                            actualizarListaProductos();
                            activado[0] = false;
                        } else if (!activado[0]) {
                            // Si no estaba activado y se levanta el dedo, activar y mantener el color naranja
                            constraintLayout.setBackgroundResource(R.drawable.background_selector); // Color naranja
                            activado[0] = true;
                        }
                        return true;
                }
                return false;
            }
        });
    }

    public void accionFiltrarAguaMineral(View rootView) {
        ConstraintLayout constraintLayout = rootView.findViewById(R.id.btnFiltrarAguaMineral);
        final boolean[] activado = {false};
        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!activado[0]) {
                            // Cambiar el color del fondo al presionar solo si está desactivado
                            constraintLayout.setBackgroundResource(R.drawable.background_selector); // Color naranja
                            filtrar("3");

                        }
                        return true;

                    case MotionEvent.ACTION_UP:
                        if (activado[0]) {
                            // Si ya estaba activado, desactivar y restaurar el color original
                            constraintLayout.setBackgroundResource(R.drawable.category_background); // Color original (blanco en este caso)

                            actualizarListaProductos();
                            activado[0] = false;
                        } else if (!activado[0]) {
                            // Si no estaba activado y se levanta el dedo, activar y mantener el color naranja
                            constraintLayout.setBackgroundResource(R.drawable.background_selector); // Color naranja
                            activado[0] = true;
                        }
                        return true;
                }
                return false;
            }
        });
    }

    public void accionFiltrarGaseosas(View rootView) {
        ConstraintLayout constraintLayout = rootView.findViewById(R.id.btnFiltrarGaseosas);
        final boolean[] activado = {false};
        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!activado[0]) {
                            // Cambiar el color del fondo al presionar solo si está desactivado
                            constraintLayout.setBackgroundResource(R.drawable.background_selector); // Color naranja
                            filtrar("4");

                        }
                        return true;

                    case MotionEvent.ACTION_UP:
                        if (activado[0]) {
                            // Si ya estaba activado, desactivar y restaurar el color original
                            constraintLayout.setBackgroundResource(R.drawable.category_background); // Color original (blanco en este caso)

                            actualizarListaProductos();
                            activado[0] = false;
                        } else if (!activado[0]) {
                            // Si no estaba activado y se levanta el dedo, activar y mantener el color naranja
                            constraintLayout.setBackgroundResource(R.drawable.background_selector); // Color naranja
                            activado[0] = true;
                        }
                        return true;
                }
                return false;
            }
        });
    }*/
    public static void filtrar(String filtrado){
         // Cambiar a tu tipo específico de producto
        // Filtrar la lista y actualizar el RecyclerView
        filtrarProductosPorTipo(filtrado);
    }
    public void mostrarMensaje(){
        Toast.makeText(requireContext(), "Seleccionaste la ", Toast.LENGTH_LONG).show();
    }
    public static void filtrarProductosPorTipo(String tipo) {
        ArrayList<Producto> productosFiltrados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getProdTiprId().equals(tipo)) {
                productosFiltrados.add(producto);
            }
        }
        productoAdapterRecycler.filtrar(productosFiltrados);
    }
    public static void actualizarListaProductos() {
        productoAdapterRecycler.actualizarListaCompleta(productos);
    }

    public void recyclerViewTipoProducto(View rootView){
        listaTipoProducto = new ArrayList<>();

        listaTipoProducto.add(new TipoProducto("Pollos", R.drawable.chicken, "1"));
        listaTipoProducto.add(new TipoProducto("Refrescos", R.drawable.refresco, "2"));
        listaTipoProducto.add(new TipoProducto("Aguas", R.drawable.agua, "3"));
        listaTipoProducto.add(new TipoProducto("Gaseosas", R.drawable.soda, "4"));
        listaTipoProducto.add(new TipoProducto("Plato carta", R.drawable.dish, "5"));

        recyclerViewListaTipoProducto = rootView.findViewById(R.id.recyclerTipoProductos);
        recyclerViewListaTipoProducto.setLayoutManager(new LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false));
        listaProductoAdapterRecycler = new ListaProductoAdapterRecycler(listaTipoProducto);
        recyclerViewListaTipoProducto.setAdapter(listaProductoAdapterRecycler);
        recyclerViewListaTipoProducto.setHasFixedSize(true);
        recyclerViewListaTipoProducto.setNestedScrollingEnabled(false);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        productoAdapterRecycler.filtrado(s);
        return false;
    }
}