package com.braxly.lapancaproject.recyclerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.braxly.lapancaproject.R;
import com.braxly.lapancaproject.models.Mesa;
import com.braxly.lapancaproject.models.Reserva;

import java.util.List;

public class ReservaAdapterRecycler extends RecyclerView.Adapter<ReservaAdapterRecycler.MyViewHolder>{
    private Context context;
    private List<Reserva> reserva;
    private LayoutInflater nInflater;

    public ReservaAdapterRecycler(Context context, List<Reserva> reserva) {
        this.nInflater = LayoutInflater.from(context);
        this.context = context;
        this.reserva = reserva;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(context).inflate(R.layout.list_element, parent, false);
        View view = nInflater.inflate(R.layout.list_reservas, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaAdapterRecycler.MyViewHolder holder, int position) {
        holder.nameMesa.setText(reserva.get(position).getMesaNumero());
        holder.reservaFecha.setText(reserva.get(position).getReseFecha());
        holder.reservaHora.setText(reserva.get(position).getReseHora());
    }

    @Override
    public int getItemCount() {
        return reserva.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameMesa, reservaFecha, reservaHora;
        RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameMesa = itemView.findViewById(R.id.txtReservas);
            reservaFecha = itemView.findViewById(R.id.txtDescripcionReserva);
            reservaHora = itemView.findViewById(R.id.txtReservaHora);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}
