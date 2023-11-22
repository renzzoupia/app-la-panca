package com.braxly.lapancaproject.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braxly.lapancaproject.R;
import com.braxly.lapancaproject.conexionApi.ConexionApi;
import com.braxly.lapancaproject.models.Cliente;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrarClienteActivity extends AppCompatActivity {
    private EditText clieNombresTxt, clieApellidosTxt, clieDniTxt, clieCelularTxt, clieCorreoTxt, clieDireccionTxt;
    RequestQueue requestQueue;
    private List<Cliente> cliente;
    private TextView registrarModificar;
    private ImageButton goBackRegistrarClienteBtn;
    private Button registrarClienteBtn;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cliente);
        iniciarActivity();
        regresarRegistroUsuario();
        Locura();

       // modoRegistroModificacion();


    }
    private void modoRegistroModificacion(){
        /*
        Intent intent = getIntent();
        String modo = intent.getStringExtra("MODO");
        if ("REGISTRO".equals(modo)) {
            registrarModificar.setText("Completa los datos finales");
            limpiarTextField();
            // Otros ajustes para el modo de registro
        } else if ("MODIFICACION".equals(modo)) {
            registrarModificar.setText("Modifica tus datos");
            registrarClienteBtn.setText("Modificar cuenta");
            traerCliente();
            limpiarTextField();
            // Otros ajustes para el modo de modificación
        }*/
    }
    private void iniciarActivity(){
        clieNombresTxt = findViewById(R.id.txtClienNombres);
        clieApellidosTxt = findViewById(R.id.txtClienApellidos);
        clieDniTxt = findViewById(R.id.txtClienDni);
        clieCelularTxt = findViewById(R.id.txtClienCelular);
        clieCorreoTxt = findViewById(R.id.txtClienCorreo);
        clieDireccionTxt = findViewById(R.id.txtClienDireccion);
        goBackRegistrarClienteBtn = findViewById(R.id.go_back_registrar_cliente);
        registrarModificar = findViewById(R.id.txtTituloRegistrarModificar);
        registrarClienteBtn = findViewById(R.id.btnModificarDatos);
        context = getApplicationContext();
        requestQueue = Volley.newRequestQueue(context);
    }
    private void limpiarTextField(){
        clieNombresTxt.setText("");
        clieApellidosTxt.setText("");
        clieDniTxt.setText("");
        clieCelularTxt.setText("");
        clieCorreoTxt.setText("");
        clieDireccionTxt.setText("");
    }


    private void regresarRegistroUsuario(){

        goBackRegistrarClienteBtn.setOnClickListener(view -> {
            Intent pasarMensaje = new Intent(getApplicationContext(),MainActivity.class);
            Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
            limpiarTextField();
            startActivity(pasarMensaje);
        });

    }
    public void registrarCliente(View view){
        String url = ConexionApi.URL_BASE + "cliente";

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Registrado correctamente", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);

                        startActivity(i);
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
                String clienUsuaId = getIntent().getStringExtra("usuaId");
                parametros.put("clie_usua_id", clienUsuaId);
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

    private void Locura(){
        clieCorreoTxt.addTextChangedListener(new TextWatcher() {
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
                String email = clieCorreoTxt.getText().toString().trim();

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    clieCorreoTxt.setError("Ingrese un correo electrónico válido");
                    // Puedes deshabilitar un botón o realizar otra acción si el formato es incorrecto
                } else {
                    clieCorreoTxt.setError(null);
                    // El formato es válido, realiza la acción correspondiente
                }
            }
        });
    }
}