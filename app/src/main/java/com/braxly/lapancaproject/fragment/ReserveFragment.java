package com.braxly.lapancaproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.braxly.lapancaproject.R;
import com.braxly.lapancaproject.activity.LoginActivity;
import com.braxly.lapancaproject.activity.RecuperarContraActivity;
import com.braxly.lapancaproject.conexionApi.ConexionApi;
import com.braxly.lapancaproject.models.Mesa;
import com.braxly.lapancaproject.models.Producto;
import com.braxly.lapancaproject.models.Reserva;
import com.braxly.lapancaproject.recyclerAdapters.MesaAdapterRecycler;
import com.braxly.lapancaproject.recyclerAdapters.ReservaAdapterRecycler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReserveFragment extends Fragment {
    private List<Reserva> reservas;
    private RequestQueue requestQueue;
    private RecyclerView reservaRecycler;
    private ReservaAdapterRecycler reservaAdapterRecycler;
    private String tipoFiltrado = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_reserve, container, false);
        iniciarActivity(rootView);
        return rootView;
    }
    private void iniciarActivity(View rootView){
        reservas = new ArrayList<>();
        reservaRecycler = rootView.findViewById(R.id.recyclerReserve);
        reservaRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        reservaAdapterRecycler = new ReservaAdapterRecycler(getActivity(),reservas);
        reservaRecycler.setAdapter(reservaAdapterRecycler);
        cargarListaReserva();
    }
    public void filtrar(String filtrado){
        tipoFiltrado = filtrado; // Cambiar a tu tipo específico de producto
        // Filtrar la lista y actualizar el RecyclerView
        filtrarProductosPorTipo(filtrado);
    }
    private void filtrarProductosPorTipo(String tipo) {
        ArrayList<Reserva> reservaFiltrado = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getReseId().equals(tipo)) {
                reservaFiltrado.add(reserva);
            }
        }
        reservaAdapterRecycler.filtrar(reservaFiltrado);
    }
    private void cargarListaReserva() {
        requestQueue = Volley.newRequestQueue(requireContext());
        SharedPreferences preferences = requireContext().getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        String clieIdRecibido = preferences.getString("clieId", "No existe la información");
        if(!clieIdRecibido.equals("No existe la información")){

        String url = Uri.parse(ConexionApi.URL_BASE + "/ReservaFiltrada/" + clieIdRecibido)
                .buildUpon()
                .build().toString();

        JsonObjectRequest requerimiento = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String Status = response.getString("Status");
                            if(Status.equals("200")){
                                int totalRegistros = response.getInt("Total de registros");

                                for (int i = 0; i < totalRegistros; i++) {

                                    String valor = response.get("Detalles").toString();
                                    JSONArray arreglo = new JSONArray(valor);
                                    JSONObject objeto = new JSONObject(arreglo.get(i).toString());

                                    String reseId = objeto.getString("rese_id");
                                    String reseMesaId = objeto.getString("rese_mesa_id");
                                    String reseClieId = objeto.getString("rese_clie_id");
                                    String reseFecha = objeto.getString("rese_fecha");
                                    String reseHora = objeto.getString("rese_hora");
                                    String resePersonas = objeto.getString("rese_personas");
                                    String mesaNumero = objeto.getString("mesa_numero");
                                    // Toast.makeText(getApplicationContext(), "hola", Toast.LENGTH_SHORT).show();

                                    Reserva reserva = new Reserva(reseId, reseMesaId, reseClieId, reseFecha, reseHora, resePersonas, mesaNumero);

                                    reservas.add(reserva);
                                    reservaAdapterRecycler.notifyItemRangeInserted(reservas.size(), 1);
                                }
                            }else if(Status.equals("404")){
                                Toast.makeText(requireContext(), "No tienes reservas", Toast.LENGTH_SHORT).show();
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
        }else{
            Toast.makeText(requireContext(), "Debes iniciar sesión", Toast.LENGTH_SHORT).show();

            Intent pasarMensaje = new Intent(requireContext(), LoginActivity.class);
            //activamos el  Intent
            startActivity(pasarMensaje);
        }
    }
}