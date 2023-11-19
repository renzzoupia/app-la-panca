package com.braxly.lapancaproject.recyclerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.braxly.lapancaproject.CambiarNumberoProductoLista;
import com.braxly.lapancaproject.ManejarCarta;
import com.braxly.lapancaproject.R;
import com.braxly.lapancaproject.models.Producto;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;

import java.util.ArrayList;

public class CartaAdapterRecycler extends RecyclerView.Adapter<CartaAdapterRecycler.ViewHolder>{
    ArrayList<Producto> listaProductoSelected;
    private ManejarCarta manejarCarta;
    CambiarNumberoProductoLista cambiarNumberoProductoLista;

    public CartaAdapterRecycler(ArrayList<Producto> listaProductoSelected, Context context, CambiarNumberoProductoLista cambiarNumberoProductoLista){
        this.listaProductoSelected = listaProductoSelected;
        manejarCarta = new ManejarCarta(context);
        this.cambiarNumberoProductoLista = cambiarNumberoProductoLista;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.ver_carrito, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(listaProductoSelected.get(position).getProdNombre());
        holder.feeEachItem.setText("S/." + listaProductoSelected.get(position).getProdPrecio());
        holder.totalEachItem.setText("S/. " + Math.round(listaProductoSelected.get(position).getNumberInCart() * listaProductoSelected.get(position).getProdPrecio()));
        holder.num.setText(String.valueOf(listaProductoSelected.get(position).getNumberInCart()));

        String imageUrl = listaProductoSelected.get(position).getProdFoto();

        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .into(holder.pic);

        holder.plusItem.setOnClickListener(v -> manejarCarta.plusNumberFood(listaProductoSelected, position, () ->{
            notifyDataSetChanged();
            cambiarNumberoProductoLista.changed();
        }));

        holder.minusItem.setOnClickListener(v -> manejarCarta.minusNumberFood(listaProductoSelected, position, () -> {
            notifyDataSetChanged();
            cambiarNumberoProductoLista.changed();
        }));
    }

    @Override
    public int getItemCount() {
        return listaProductoSelected.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, feeEachItem, plusItem, minusItem;
        ImageView pic;
        TextView totalEachItem, num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            pic = itemView.findViewById(R.id.pic);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            totalEachItem = itemView.findViewById(R.id.totalProducItem);
            plusItem = itemView.findViewById(R.id.plusCartBtn);
            minusItem = itemView.findViewById(R.id.minusCartBtn);
            num = itemView.findViewById(R.id.numberItemTxt);
        }
    }
}
