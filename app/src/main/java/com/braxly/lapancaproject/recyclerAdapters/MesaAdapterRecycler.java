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
import com.braxly.lapancaproject.activity.ProductoDetallesActivity;
import com.braxly.lapancaproject.activity.ReservaActivity;
import com.braxly.lapancaproject.models.Mesa;

import java.util.List;

public class MesaAdapterRecycler extends RecyclerView.Adapter<MesaAdapterRecycler.MyViewHolder>{
    private Context context;
    private List<Mesa> mesa;
    private LayoutInflater nInflater;
    public MesaAdapterRecycler(Context context, List<Mesa> mesa) {
        this.nInflater = LayoutInflater.from(context);
        this.context = context;
        this.mesa = mesa;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(context).inflate(R.layout.list_element, parent, false);
        View view = nInflater.inflate(R.layout.list_mesas, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,@SuppressLint("RecyclerView") int position) {
        holder.NameMesa.setText(mesa.get(position).getMesaNumero());
        holder.cantidadPersonas.setText("Para " + mesa.get(position).getMesaCantidadPersonas() + " personas");
        holder.statusMesa.setText("Activo");
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(),"Seleccionaste la mesa " + mesa.get(position).getMesaNumero(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(holder.itemView.getContext(), ReservaActivity.class);

                intent.putExtra("mesa", mesa.get(position));

                holder.itemView.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mesa.size();
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
