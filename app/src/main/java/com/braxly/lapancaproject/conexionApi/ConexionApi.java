package com.braxly.lapancaproject.conexionApi;


import static android.util.Base64.encodeToString;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braxly.lapancaproject.R;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ConexionApi {
    public static final String URL_BASE = "https://panca.informaticapp.com/";
    public static String AUTH = "Basic YTJhYTA3YWRmaGRmcmV4ZmhnZGZoZGZlcnR0Z2VsUWl6NFFOY0VPR0hqby4ya1B6dVoza0Y0LlZEbWo2OmEyYWEwN2FkZmhkZnJleGZoZ2RmaGRmZXJ0dGdlNkl0RnU2Zzd2VmJ3Skd3SnJYMnhudTJxanUvZmpjdQ==";
    RequestQueue requestQueue;
    public void realizarDetallePedido(String depePediId, String depeProdId, String depeCantidad, String depeSubtotal, Context context){

        requestQueue = Volley.newRequestQueue(context);
        String url = ConexionApi.URL_BASE + "DetallePedido";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("ERROR", response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("ERROR", Objects.requireNonNull(error.getMessage()));
                        //System.out.println(error.getMessage());

                    }
                }
        ){
            //cargando los datos a enviar
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parametros = new HashMap<>();

                parametros.put("depe_pedi_id", depePediId);
                parametros.put("depe_prod_id", depeProdId);
                parametros.put("depe_cantidad", depeCantidad);
                parametros.put("depe_subtotal", depeSubtotal);

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

    public void eliminarCredenciales(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences("Credenciales", context.MODE_PRIVATE).edit();

        // Remueve los datos que quieres eliminar (correo, id_cliente)
        editor.remove("clieId");
        editor.remove("usuaId");
        editor.remove("clieDireccion");
        editor.remove("clieCorreo");
        editor.remove("usuaUsername");
        editor.remove("clieNombres");
        editor.remove("clieApellidos");

        // Aplica los cambios
        editor.apply();
    }
}
