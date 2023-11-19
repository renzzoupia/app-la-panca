package com.braxly.lapancaproject.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braxly.lapancaproject.CambiarNumberoProductoLista;
import com.braxly.lapancaproject.MailJob;
import com.braxly.lapancaproject.ManejarCarta;
import com.braxly.lapancaproject.R;
import com.braxly.lapancaproject.conexionApi.ConexionApi;
import com.braxly.lapancaproject.models.DetallePedido;
import com.braxly.lapancaproject.models.Pedido;
import com.braxly.lapancaproject.models.Producto;
import com.braxly.lapancaproject.recyclerAdapters.CartaAdapterRecycler;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class CartaActivity extends AppCompatActivity{
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManejarCarta manejarCarta;
    private TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt, direccionUsuarioTxt;
    private double tax;
    private ScrollView scrollView;
    private ImageView backBtn, backBtnDos;
    private Pedido pedido;
    private DetallePedido detallePedido;
    private Button btnEnviarCarrito;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta);

        manejarCarta = new ManejarCarta(this);

        iniciarActivity();
        iniciarLista();
        calcularCarta();
        setVariable();
        setVariable2();

        enviarPedido();
    }
    private void enviarCorreo(){

    }
    private void iniciarActivity(){
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        taxTxt = findViewById(R.id.taxTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        totalTxt = findViewById(R.id.totalTxt);
        recyclerViewList = findViewById(R.id.view3);
        scrollView = findViewById(R.id.scrollView);
        backBtn = findViewById(R.id.backBtn);
        backBtnDos = findViewById(R.id.backBtn2);
        emptyTxt = findViewById(R.id.emptyTxt);
        Context context = getApplicationContext();
        requestQueue = Volley.newRequestQueue(context);
        btnEnviarCarrito = findViewById(R.id.btnAddToCart);

        direccionUsuarioTxt = findViewById(R.id.txtDireccionUsuario);
        SharedPreferences preferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        String clieDireccionRecibido = preferences.getString("clieDireccion", "No existe la información");
        direccionUsuarioTxt.setText(clieDireccionRecibido);

    }
    private void setVariable(){
        backBtn.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        });
    }

    private void setVariable2(){
        backBtnDos.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        });
    }
    private void iniciarLista(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new CartaAdapterRecycler(manejarCarta.obtenerCarrito(), this, new CambiarNumberoProductoLista() {
            @Override
            public void changed() {
                calcularCarta();
            }
        });

        recyclerViewList.setAdapter(adapter);

        if(manejarCarta.obtenerCarrito().isEmpty()){
            emptyTxt.setVisibility(View.VISIBLE);
            backBtnDos.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }else{
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void calcularCarta(){
        //double porcentajeTax = 0.02; // you can cambiar este objeto por  txtPrice
        double delivery = 10;
        //tax = Math.round(((manejarCarta.getTotalFee()))/100.0);

        //double total = Math.round((manejarCarta.getTotalPrecioCantidad() + delivery) * 100.0)/100;
        //double itemTotal = Math.round(manejarCarta.getTotalPrecioCantidad() * 100.0)/100.0;

        double total = Math.round(manejarCarta.getTotalPrecioCantidad() + delivery);
        double itemTotal = Math.round(manejarCarta.getTotalPrecioCantidad());

        totalFeeTxt.setText("S/. " + itemTotal);
        taxTxt.setText("S/. 0");
        deliveryTxt.setText("S/. " + delivery);
        totalTxt.setText("S/. " + total);
    }
    private void enviarPedido(){
        btnEnviarCarrito.setOnClickListener(view -> {
            realizarPedido();

            //String pedi = ConexionApi.pedidoId;
            //Toast.makeText(getApplicationContext(), "Pediste " + pedi, Toast.LENGTH_SHORT).show();
            ///////////////////////////77

          /*  String url = ConexionApi.URL_BASE + "pedido";
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);


                                // Obtén el valor de "usua_id" del objeto "credenciales"
                                pedidoIdFinal = jsonObject.getString("Pedido");

                            } catch (JSONException e) {
                                Log.e("JSON", Objects.requireNonNull(e.getMessage()));
                                e.printStackTrace();
                            }
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
                    //Toast t = Toast.makeText(context, mesaId + clieId + tipoPedido + fechaPedido + total, Toast.LENGTH_LONG);
                    //t.show();
                /*Toast.makeText(context, mesaId, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, clieId, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, tipoPedido, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, fechaPedido, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, total, Toast.LENGTH_SHORT).show();
                    Calendar calendar = Calendar.getInstance();
                    Date date = calendar.getTime();
                    // Formatear la fecha y hora según tu preferencia
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateTime = sdf.format(date);
                    String total = Math.round(manejarCarta.getTotalPrecioCantidad() + 10) + "";
                    ConexionApi conexionApi = new ConexionApi();

                    //conexionApi.realizarPedido("1", "1", "Local", dateTime, total, getApplicationContext());

                    Map<String,String> parametros = new HashMap<>();

                    parametros.put("pedi_mesa_id", "1");
                    parametros.put("pedi_clie_id", "1");
                    parametros.put("pedi_tipo_pedido", "Local");
                    parametros.put("pedi_fecha", dateTime);
                    parametros.put("pedi_total", total);


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
*/
                /*ArrayList<Producto> listaProducto2 = manejarCarta.obtenerCarrito();
                for(int i = 0; i < listaProducto2.size(); i ++){
                    double itemTotal = Math.round(listaProducto2.get(i).getProdPrecio() * listaProducto2.get(i).getNumberInCart());
                    String itemTotalString = itemTotal + "";
                    String depeCantidad = listaProducto2.get(i).getNumberInCart() + "";
                    ConexionApi conexionApi = new ConexionApi();
                    conexionApi.realizarDetallePedido("1",listaProducto2.get(i).getProdId(),depeCantidad,itemTotalString,getApplicationContext());
                    //Toast toast = Toast.makeText(getApplicationContext(), "Pediste " + listaProducto2.get(i).getNumberInCart() + " "  + listaProducto2.get(i).getProdNombre() + "  y son: S/." + itemTotal, Toast.LENGTH_SHORT);
                    //toast.show();
                    //Toast.makeText(getApplicationContext(), "Pediste " + listaProducto2.get(i).getNumberInCart() + " "  + listaProducto2.get(i).getProdNombre() + "  y son: S/." + itemTotal, Toast.LENGTH_SHORT).show();

                }*/

        });
    }

    private void realizarPedido(){
        String url = ConexionApi.URL_BASE + "pedido";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("PED", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            // Obtén el valor de "usua_id" del objeto "credenciales"
                            String pedidoIdFinal = jsonObject.getString("Pedido");
                            realizarPedidoTotal(pedidoIdFinal);

                            Toast.makeText(getApplicationContext(), "Pedido realizado", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);

                        } catch (JSONException e) {
                            Log.e("JSON", Objects.requireNonNull(e.getMessage()));
                            e.printStackTrace();
                        }
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

                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                // Formatear la fecha y hora según tu preferencia
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateTime = sdf.format(date);
                String total = Math.round(manejarCarta.getTotalPrecioCantidad() + 10) + "";

                SharedPreferences preferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
                String clieIdRecibido = preferences.getString("clieId", "No existe la información");

                Map<String,String> parametros = new HashMap<>();

                parametros.put("pedi_mesa_id", "1");
                parametros.put("pedi_clie_id", clieIdRecibido);
                parametros.put("pedi_tipo_pedido", "Delivery");
                parametros.put("pedi_fecha", dateTime);
                parametros.put("pedi_total", total);


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
    private void realizarPedidoTotal(String pedidoIdFinal){
        SharedPreferences preferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        String usuaUsernameRecibido = preferences.getString("usuaUsername", "No existe la información");
        String clieDireccionRecibido = preferences.getString("clieDireccion", "No existe la información");
        StringBuilder cuerpoCorreo = new StringBuilder();
        cuerpoCorreo.append("Hola ").append(usuaUsernameRecibido).append(", usted acaba de pedir:\n\n");

        ArrayList<Producto> listaProducto2 = manejarCarta.obtenerCarrito();
        for(int i = 0; i < listaProducto2.size(); i ++){
            double itemTotal = Math.round(listaProducto2.get(i).getProdPrecio() * listaProducto2.get(i).getNumberInCart());
            String itemTotalString = itemTotal + "";
            String depeCantidad = listaProducto2.get(i).getNumberInCart() + "";
            ConexionApi conexionApi = new ConexionApi();
            conexionApi.realizarDetallePedido(pedidoIdFinal,listaProducto2.get(i).getProdId(),depeCantidad,itemTotalString,getApplicationContext());

            cuerpoCorreo.append("Plato: ").append(listaProducto2.get(i).getProdNombre()).append("\n");
            cuerpoCorreo.append("Cantidad: ").append(listaProducto2.get(i).getNumberInCart()).append("\n");
            cuerpoCorreo.append("Precio unitario: S/.").append(listaProducto2.get(i).getProdPrecio()).append("\n");
            cuerpoCorreo.append("Precio total: S/.").append(itemTotalString).append("\n\n");


        }
        cuerpoCorreo.append("Precio total de a pagar + delivery: ").append(totalTxt.getText().toString()).append("\n");
        cuerpoCorreo.append("Lo llevaremos a : ").append(clieDireccionRecibido).append("\n");
        cuerpoCorreo.append("Gracias por confiar en La Panca :)").append("\n");

        String user = "lapanca.oficial@gmail.com";
        String passwd = "xmpz fedu ibjs yztd";

        new MailJob(user, passwd).execute(
                new MailJob.Mail("renzoupiachihua3@gmail.com", "renzoupiachihua3@gmail.com", "Tu Pedido",
                        cuerpoCorreo.toString())
        );
    }
}