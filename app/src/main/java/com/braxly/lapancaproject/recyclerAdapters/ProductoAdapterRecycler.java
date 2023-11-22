package com.braxly.lapancaproject.recyclerAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.braxly.lapancaproject.R;
import com.braxly.lapancaproject.activity.ProductoDetallesActivity;
import com.braxly.lapancaproject.models.Producto;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductoAdapterRecycler extends RecyclerView.Adapter<ProductoAdapterRecycler.ViewHolder> {
    Context context;
    List<Producto> productoFiltrado;//producto filtrado
    List<Producto> listaProducto;//todos los datos

    public ProductoAdapterRecycler(List<Producto> listaProducto){
        this.listaProducto = listaProducto;
        productoFiltrado = new ArrayList<>();
        productoFiltrado.addAll(listaProducto);
    }

    public void filtrado(String txtBuscar){
        int longitud = txtBuscar.length();
        if(longitud == 0){
            listaProducto.clear();
            listaProducto.addAll(productoFiltrado);
        }else{
            if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
                List<Producto> collecion = listaProducto.stream()
                        .filter(i -> i.getProdNombre().toLowerCase()
                                .contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                productoFiltrado.clear();
                productoFiltrado.addAll(collecion);
            }else{
                for(Producto c: productoFiltrado){
                    if(c.getProdNombre().toLowerCase().contains(txtBuscar.toLowerCase())){
                        productoFiltrado.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_productos, parent, false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.titleTxt.setText(listaProducto.get(position).getProdNombre());
        holder.timeTxt.setText("20 min");
        holder.ScoreTxt.setText("S/. " + listaProducto.get(position).getProdPrecio());
        String imageUrl = listaProducto.get(position).getProdFoto();

        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .into(holder.pic);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), ProductoDetallesActivity.class);

                intent.putExtra("producto", listaProducto.get(position));

                holder.itemView.getContext().startActivity(intent);
            }
        });
    }
    public void filtrar(List<Producto> listaFiltrada) {
        listaProducto.clear();
        listaProducto.addAll(listaFiltrada);
        notifyDataSetChanged();
    }

    public void actualizarListaCompleta(List<Producto> listaCompleta) {
        listaProducto.clear();
        listaProducto.addAll(listaCompleta);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listaProducto.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt, timeTxt, ScoreTxt;
        ImageView pic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.txtTitulo);
            timeTxt = itemView.findViewById(R.id.txtTime);
            ScoreTxt = itemView.findViewById(R.id.txtScore);
            pic = itemView.findViewById(R.id.image);
        }
    }
}
