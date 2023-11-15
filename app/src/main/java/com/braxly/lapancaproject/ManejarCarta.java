package com.braxly.lapancaproject;

import android.content.Context;
import android.widget.Toast;

import com.braxly.lapancaproject.helper.TinyDB;
import com.braxly.lapancaproject.models.Producto;

import java.util.ArrayList;

public class ManejarCarta {
    private Context context;
    private TinyDB tinyDB;
    public ManejarCarta(Context context){
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }
    public void insertarComida(Producto producto){

        ArrayList<Producto> listaProducto = obtenerCarrito();

        boolean existAlready = false;
        int n = 0;

        for(int y = 0; y < listaProducto.size(); y ++){
            if(listaProducto.get(y).getProdNombre().equals(producto.getProdNombre())){
                existAlready = true;
                n = y;
                break;
            }
        }
        if(existAlready){
            listaProducto.get(n).setNumberInCart(producto.getNumberInCart());
        }else{
            listaProducto.add(producto);
        }
        tinyDB.putListObject("CartList", listaProducto);
        Toast.makeText(context, "Agregado a tu carta", Toast.LENGTH_SHORT).show();
    }
    public ArrayList<Producto> obtenerCarrito(){
        return tinyDB.getListObject("CartList");
    }

    public void minusNumberFood(ArrayList<Producto> listaProducto, int position, CambiarNumberoProductoLista cambiarNumberoProductoLista){
        if(listaProducto.get(position).getNumberInCart() == 1){
            listaProducto.remove(position);
        }else{
            listaProducto.get(position).setNumberInCart(listaProducto.get(position).getNumberInCart() - 1);
        }
        tinyDB.putListObject("CartList", listaProducto);
        cambiarNumberoProductoLista.changed();
    }

    public void plusNumberFood(ArrayList<Producto> listaProducto, int position, CambiarNumberoProductoLista cambiarNumberoProductoLista){
        listaProducto.get(position).setNumberInCart(listaProducto.get(position).getNumberInCart() + 1);
        tinyDB.putListObject("CartList", listaProducto);
        cambiarNumberoProductoLista.changed();
    }

    public Double getTotalFee(){
        ArrayList<Producto> listaProducto2 = obtenerCarrito();
        double fee = 0;
        for(int i = 0; i < listaProducto2.size(); i ++){
            fee = fee + (listaProducto2.get(i).getProdPrecio() * listaProducto2.get(i).getNumberInCart());
        }
        return fee;
    }
}
