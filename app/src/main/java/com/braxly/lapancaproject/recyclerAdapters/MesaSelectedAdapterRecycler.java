package com.braxly.lapancaproject.recyclerAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.braxly.lapancaproject.R;
import com.braxly.lapancaproject.activity.EleccionMesaActivity;
import com.braxly.lapancaproject.models.Mesa;

import java.util.List;

public class MesaSelectedAdapterRecycler extends RecyclerView.Adapter<MesaSelectedAdapterRecycler.MyViewHolder>{
    private Context context;
    private List<Mesa> mesaSelected;
    private LayoutInflater nInflater;

    public MesaSelectedAdapterRecycler(Context context, List<Mesa> mesaSelected) {
        this.nInflater = LayoutInflater.from(context);
        this.context = context;
        this.mesaSelected = mesaSelected;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = nInflater.inflate(R.layout.list_mesas, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,@SuppressLint("RecyclerView") int position) {
        holder.NameMesa.setText(mesaSelected.get(position).getMesaNumero());
        holder.cantidadPersonas.setText(mesaSelected.get(position).getMesaCantidadPersonas());
        holder.statusMesa.setText(mesaSelected.get(position).getMesaActivo());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Mesa " + mesaSelected.get(position).getMesaNumero(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mesaSelected.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView NameMesa,cantidadPersonas, statusMesa;
        RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            NameMesa = itemView.findViewById(R.id.txtNameMesa);
            cantidadPersonas = itemView.findViewById(R.id.txtCantidadPersonas);
            statusMesa = itemView.findViewById(R.id.txtStatusMesa);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}
