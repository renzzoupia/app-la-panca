package com.braxly.lapancaproject.recyclerAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.braxly.lapancaproject.R;
import com.braxly.lapancaproject.models.Cliente;
import java.util.List;

public class ClienteAdapterRecycler extends RecyclerView.Adapter<ClienteAdapterRecycler.MyViewHolder>{
    private Context context;
    private List<Cliente> cliente;
    private LayoutInflater nInflater;
    public ClienteAdapterRecycler(Context context, List<Cliente> cliente){
        this.nInflater = LayoutInflater.from(context);
        this.context = context;
        this.cliente = cliente;
    }
    @Override
    public ClienteAdapterRecycler.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(context).inflate(R.layout.list_element, parent, false);
        View view = nInflater.inflate(R.layout.list_cliente, null);
        return new ClienteAdapterRecycler.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClienteAdapterRecycler.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tituloTextView.setText(cliente.get(position).getClieNombres());
        holder.autorTextView.setText(cliente.get(position).getClieApellidos());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Hiciste click en " + cliente.get(position).getClieNombres(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cliente.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tituloTextView;
        TextView autorTextView;
        RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.txtNameMesa);
            autorTextView = itemView.findViewById(R.id.txtCantidadPersonas);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}
