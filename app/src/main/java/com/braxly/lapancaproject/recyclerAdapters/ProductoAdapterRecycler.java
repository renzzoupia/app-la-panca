package com.braxly.lapancaproject.recyclerAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

public class ProductoAdapterRecycler extends RecyclerView.Adapter<ProductoAdapterRecycler.ViewHolder> {
    Context context;
    List<Producto> producto;

    public ProductoAdapterRecycler(List<Producto> producto){
        this.producto = producto;
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
        holder.titleTxt.setText(producto.get(position).getProdNombre());
        holder.timeTxt.setText("20 min");
        holder.ScoreTxt.setText("S/. " + producto.get(position).getProdPrecio());
        String imageUrl = producto.get(position).getProdFoto();

        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .into(holder.pic);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), ProductoDetallesActivity.class);

                intent.putExtra("producto", producto.get(position));

                holder.itemView.getContext().startActivity(intent);
            }
        });
    }
    public void filtrar(ArrayList<Producto> listaFiltrada) {
        producto.clear();
        producto.addAll(listaFiltrada);
        notifyDataSetChanged();
    }

    public void actualizarListaCompleta(List<Producto> listaCompleta) {
        producto.clear();
        producto.addAll(listaCompleta);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return producto.size();
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
