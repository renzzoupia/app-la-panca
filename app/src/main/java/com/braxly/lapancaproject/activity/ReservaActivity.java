package com.braxly.lapancaproject.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braxly.lapancaproject.R;
import com.braxly.lapancaproject.conexionApi.ConexionApi;
import com.braxly.lapancaproject.models.Mesa;
import com.braxly.lapancaproject.models.Producto;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ReservaActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText fechaReserva, horaReserva, personaCantidad;
    private TextView nameMesa, cantidadPersonas;
    private ImageView mesaImg;
    private Button bFecha, bHora;
    private int dia, mes, anio, hora, minutos;
    private Mesa mesa;
    private RecyclerView recyclerMesaSelected;
    private boolean recyclerViewEnabled = false;
    RequestQueue requestQueue;
    private RecyclerView.Adapter adapter;
    private String mesaId, clieId;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);
        iniciarActivity();

        obtenerMesa();
    }
    private void iniciarActivity(){
        //mesaId = findViewById(R.id.txtMesaId);
        //recyclerMesaSelected = findViewById(R.id.recyclerMesaSelected);
        //nameMesa = findViewById(R.id.txtNumMesa);
        //cantidadPersonas = findViewById(R.id.txtReferenciaMesa);
        //cantidadPersonas = findViewById(R.id.txtCantidadPersonasSelected);
        nameMesa = findViewById(R.id.txtNameMesaSelected);
        fechaReserva = findViewById(R.id.txtReservaFecha);
        horaReserva = findViewById(R.id.txtHoraReserva);
        personaCantidad = findViewById(R.id.txtPersonasCantidad);

        bFecha = findViewById(R.id.bFechaReserva);
        bHora = findViewById(R.id.bHoraReserva);
        bFecha.setOnClickListener(this);
        bHora.setOnClickListener(this);
        context = getApplicationContext();
        requestQueue = Volley.newRequestQueue(context);


    }
    public void eleccionMesa(View view){
        Intent i = new Intent(ReservaActivity.this, EleccionMesaActivity.class);
        startActivity(i);

    }
    public void obtenerMesa(){
        mesa = (Mesa) getIntent().getSerializableExtra("mesa");

        nameMesa.setText(mesa.getMesaNumero());
        mesaId = mesa.getMesaId();
    }

    @Override
    public void onClick(View view) {
        if(view == bFecha){
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            anio = c.get(Calendar.YEAR);
            // Configura la fecha mínima (puedes ajustar esto según tus necesidades)
            c.set(2023, 10, 1); // 1 de enero de 2000

            long minDate = c.getTimeInMillis();
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                   // fechaReserva.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                    fechaReserva.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
                }
            }
            , dia, mes, anio);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMinDate(minDate);
        }
        if(view == bHora){
            final Calendar c = Calendar.getInstance();
            hora = c.get(Calendar.HOUR_OF_DAY);
            minutos = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                    horaReserva.setText(hourOfDay + ":" + minute);
                }
            }, hora, minutos, true);
            timePickerDialog.show();
        }
    }

    public void registrarReserva(View vista){

        String url = ConexionApi.URL_BASE + "reserva";

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            String estadoReserva = jsonObject.getString("Status");
                            if (estadoReserva.equals("200")){
                                Toast.makeText(getApplicationContext(), "Reservado correctamente", Toast.LENGTH_SHORT).show();
                                Intent pasarMensaje = new Intent(context,MainActivity.class);
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
                        Toast.makeText(getApplicationContext(), "error al registrar", Toast.LENGTH_SHORT).show();
                        //System.out.println(error.getMessage());

                    }
                }
        ){
            //cargando los datos a enviar
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parametros = new HashMap<>();

                parametros.put("rese_mesa_id", mesaId);
                parametros.put("rese_clie_id", "4");
                parametros.put("rese_fecha", fechaReserva.getText().toString());
                parametros.put("rese_hora", horaReserva.getText().toString());
                parametros.put("rese_personas", personaCantidad.getText().toString());


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
    public void regresarMainActivity(View view){
        Toast.makeText(getApplicationContext(), "Reserva cancelado", Toast.LENGTH_SHORT).show();
        Intent pasarMensaje = new Intent(context,MainActivity.class);
        //activamos el  Intent
        startActivity(pasarMensaje);
    }

    private void validarCantidadPersona(){
        personaCantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No se utiliza en este caso
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No se utiliza en este caso
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = personaCantidad.getText().toString().trim();

                if (text.length() < 3) {
                    personaCantidad.setError("Solo se puede reservar hasta 8");
                    // Puedes deshabilitar un botón o realizar otra acción si la longitud es menor a 10
                } else {
                    personaCantidad.setError(null);
                    // La longitud es válida, realiza la acción correspondiente
                }
            }
        });
    }
}