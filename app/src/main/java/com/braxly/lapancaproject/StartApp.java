package com.braxly.lapancaproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.braxly.lapancaproject.activity.LoginActivity;
import com.braxly.lapancaproject.activity.MainActivity;
import com.braxly.lapancaproject.conexionApi.ConexionApi;
import com.braxly.lapancaproject.models.Producto;
import com.braxly.lapancaproject.recyclerAdapters.ProductoAdapterRecycler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StartApp extends AppCompatActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_app);

        Thread timeTread = new Thread(){
            public void run(){
                try{
                    sleep(1500);
                    //fillArrays();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally {

                    Intent intencion = new Intent(StartApp.this, MainActivity.class);
                    startActivity(intencion);

                }
            }
        };
        timeTread.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}