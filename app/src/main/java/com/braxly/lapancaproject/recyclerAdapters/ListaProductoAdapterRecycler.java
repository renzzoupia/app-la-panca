package com.braxly.lapancaproject.recyclerAdapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.braxly.lapancaproject.R;
import com.braxly.lapancaproject.fragment.HomeFragment;
import com.braxly.lapancaproject.models.TipoProducto;

import java.util.List;

public class ListaProductoAdapterRecycler extends RecyclerView.Adapter<ListaProductoAdapterRecycler.ViewHolder> {
    List<TipoProducto> listaTipoProducto;
    int row_index = -1;
    public ListaProductoAdapterRecycler(List<TipoProducto> listaTipoProducto){
        this.listaTipoProducto = listaTipoProducto;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_tipo_producto, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListaProductoAdapterRecycler.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.imageView.setImageResource(listaTipoProducto.get(position).getTipoProductoImg());
        holder.name.setText(listaTipoProducto.get(position).getTipoProductoNombre());
        TipoProducto selectedItem = listaTipoProducto.get(position);

        if (row_index == position) {
            holder.cardView.setBackgroundResource(R.drawable.background_selector);
        } else {
            holder.cardView.setBackgroundResource(R.drawable.category_background);
        }
        // Obtén el elemento actual de la lista
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (row_index == position) {
                    row_index = -1; // Reinicia el índice para desactivar la selección
                } else {
                    row_index = position;
                }
                notifyDataSetChanged();

                // Mensaje para indicar el cambio de estado
                if (row_index != -1) {
                    HomeFragment.cargarListaProductos(v.getContext());
                    HomeFragment.filtrar(listaTipoProducto.get(position).getTipoProductoId());
                } else {
                    HomeFragment.cargarListaProductos(v.getContext());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return listaTipoProducto.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.platosCevicheIMG);
            name = itemView.findViewById(R.id.platosCevicheTXT);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

}
