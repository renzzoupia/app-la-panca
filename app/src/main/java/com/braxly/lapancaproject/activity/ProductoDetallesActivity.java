package com.braxly.lapancaproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.braxly.lapancaproject.ManejarCarta;
import com.braxly.lapancaproject.R;
import com.braxly.lapancaproject.StartApp;
import com.braxly.lapancaproject.models.Producto;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;

import java.io.Serializable;

public class ProductoDetallesActivity extends AppCompatActivity {
    private Button addToCartBtn;
    private TextView plusBtn, minusBtn, tittleTxt, feeTxt, descriptionTxt, numberOrderTxt, startTxt, caloryTxt, timeTxt;
    private ImageView picFood;
    private Producto producto;
    private int numberOrder = 1;
    private ManejarCarta manejarCarta;
    private ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_detalles);

        manejarCarta = new ManejarCarta(ProductoDetallesActivity.this);

        iniciarProductoDetallesActivity();
        obtenerProductos();
        regresarVistaMain();
        desactivarMinusProducto();
    }
    private void iniciarProductoDetallesActivity(){
        addToCartBtn = findViewById(R.id.btnAddToCart);
        tittleTxt = findViewById(R.id.txtTittle);
        feeTxt = findViewById(R.id.txtPrice);
        descriptionTxt = findViewById(R.id.txtDescription);
        numberOrderTxt = findViewById(R.id.numberItem);
        plusBtn = findViewById(R.id.btnPlusCart);
        minusBtn = findViewById(R.id.btnMinusCart);
        picFood = findViewById(R.id.foodPic);
        startTxt = findViewById(R.id.txtStart);
        caloryTxt = findViewById(R.id.txtCal);
        timeTxt = findViewById(R.id.txtTime);
        backBtn = findViewById(R.id.btnBackMain);
    }
    private void obtenerProductos(){
        producto = (Producto) getIntent().getSerializableExtra("producto");
        /*String prodNombre = getIntent().getStringExtra("prodNombre");
        String prodDescripcion = getIntent().getStringExtra("prodDescripcion");
        String prodPrecio = getIntent().getStringExtra("prodPrecio");
        String prodFoto = getIntent().getStringExtra("prodFoto");*/

       /* int drawableResourceId = this.getResources().getIdentifier(producto.getPicUrl(), "drawable", this.getPackageName());
        Glide.with(this)
                .load(drawableResourceId)
                .into(picFood);*/

        String imageUrl = producto.getProdFoto();

        Glide.with(this)
                .load(imageUrl)
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .into(picFood);

        //Double precio = Double.parseDouble(prodPrecio);

        tittleTxt.setText(producto.getProdNombre());
        feeTxt.setText("S/. " + producto.getProdPrecio());
        descriptionTxt.setText(producto.getProdDescripcion());
        numberOrderTxt.setText("" + numberOrder);
        caloryTxt.setText("30  Cal");
        startTxt.setText(" 10");
        timeTxt.setText("20 min");
        addToCartBtn.setText("Agregar a tu carta - S/. " + Math.round(numberOrder * producto.getProdPrecio()));

        plusBtn.setOnClickListener(view -> {
            numberOrder = numberOrder + 1;
            numberOrderTxt.setText("" + numberOrder);
            addToCartBtn.setText("Agregar a tu carta - S/. " + Math.round(numberOrder * producto.getProdPrecio()));
            desactivarMinusProducto();
        });

        minusBtn.setOnClickListener(view -> {
            numberOrder = numberOrder - 1;
            numberOrderTxt.setText("" + numberOrder);
            addToCartBtn.setText("Agregar a tu carta - S/. " + Math.round(numberOrder * producto.getProdPrecio()));
            desactivarMinusProducto();
        });

        addToCartBtn.setOnClickListener(view -> {
            producto.setNumberInCart(numberOrder);
            manejarCarta.insertarComida(producto);

            Intent i = new Intent(ProductoDetallesActivity.this, MainActivity.class);
            startActivity(i);
        });
    }

    public void regresarVistaMain(){
        backBtn.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        });
    }

    public void desactivarMinusProducto(){
        String textoItem = numberOrderTxt.getText().toString();
        if (textoItem.equals("1")) {
            minusBtn.setEnabled(false); // Desactiva textView1
        }else{
            minusBtn.setEnabled(true);
        }
    }
}