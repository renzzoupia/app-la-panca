package com.braxly.lapancaproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.braxly.lapancaproject.CambiarNumberoProductoLista;
import com.braxly.lapancaproject.ManejarCarta;
import com.braxly.lapancaproject.R;
import com.braxly.lapancaproject.recyclerAdapters.CartaAdapterRecycler;

public class CartaActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManejarCarta manejarCarta;
    private TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt;
    private double tax;
    private ScrollView scrollView;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta);

        manejarCarta = new ManejarCarta(this);

        iniciarActivity();
        iniciarLista();
        calcularCarta();
        setVariable();
    }
    private void setVariable(){
        backBtn.setOnClickListener(v -> finish());
    }

    private void iniciarActivity(){
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        taxTxt = findViewById(R.id.taxTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        totalTxt = findViewById(R.id.totalTxt);
        recyclerViewList = findViewById(R.id.view3);
        scrollView = findViewById(R.id.scrollView);
        backBtn = findViewById(R.id.backBtn);
        emptyTxt = findViewById(R.id.emptyTxt);
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
            scrollView.setVisibility(View.GONE);
        }else{
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void calcularCarta(){
        double porcentajeTax = 0.02; // you can cambiar este objeto por  txtPrice
        double delivery = 10;
        tax = Math.round((manejarCarta.getTotalFee() * porcentajeTax * 100.0))/100.0;

        double total = Math.round((manejarCarta.getTotalFee() + tax + delivery) * 100.0)/100;
        double itemTotal = Math.round(manejarCarta.getTotalFee() * 100.0)/100.0;

        totalFeeTxt.setText("S/. " + itemTotal);
        taxTxt.setText("S/." + tax);
        deliveryTxt.setText("S/. " + delivery);
        totalTxt.setText("S/. " + total);
    }
}