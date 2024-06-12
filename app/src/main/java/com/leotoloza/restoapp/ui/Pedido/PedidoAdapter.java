package com.leotoloza.restoapp.ui.Pedido;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leotoloza.restoapp.Models.PedidoDTO;
import com.leotoloza.restoapp.R;

import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {

    private List<PedidoDTO> pedidoList;
    private Context context;
    private OnPedidoClickListener onPedidoClickListener;

    public PedidoAdapter(List<PedidoDTO> pedidoList, Context context, OnPedidoClickListener onPedidoClickListener) {
        this.pedidoList = pedidoList;
        this.context = context;
        this.onPedidoClickListener = onPedidoClickListener;
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedidos, parent, false);
        return new PedidoViewHolder(view, onPedidoClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        PedidoDTO pedido = pedidoList.get(position);
        holder.textViewDetalle.setText(pedido.getDetalle());
        holder.textViewCliente.setText("Cliente: " + pedido.getClienteDTO().getNombre_cliente() + " " + pedido.getClienteDTO().getApellido_cliente());
        holder.textViewTotal.setText("Total: $" + pedido.getTotal());
        holder.textViewFechaPedido.setText("Fecha del Pedido: " + pedido.getFechaPedido());
        ProductoAdapter productoAdapter = new ProductoAdapter(pedido.getProductos());
        holder.recyclerViewProductos.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerViewProductos.setAdapter(productoAdapter);
    }

    public void setPedidoList(List<PedidoDTO> pedidoList) {
        this.pedidoList = pedidoList;
        notifyDataSetChanged();
    }

    public List<PedidoDTO> getPedidoList() {
        return pedidoList;
    }

    @Override
    public int getItemCount() {
        return pedidoList.size();
    }

    public static class PedidoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewDetalle, textViewCliente, textViewTotal, textViewFechaPedido;
        Button btnEnvio;
        RecyclerView recyclerViewProductos;
        OnPedidoClickListener onPedidoClickListener;

        public PedidoViewHolder(@NonNull View itemView, OnPedidoClickListener onPedidoClickListener) {
            super(itemView);
            textViewDetalle = itemView.findViewById(R.id.textViewDetalle);
            textViewCliente = itemView.findViewById(R.id.textViewCliente);
            textViewTotal = itemView.findViewById(R.id.textViewTotal);
            textViewFechaPedido = itemView.findViewById(R.id.textViewFechaPedido);
            recyclerViewProductos = itemView.findViewById(R.id.recyclerViewProductos);
            btnEnvio = itemView.findViewById(R.id.btnEnvio);
            this.onPedidoClickListener = onPedidoClickListener;

            btnEnvio.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnEnvio) {
                onPedidoClickListener.onPedidoClick(getAdapterPosition());
            }
        }
    }

    public interface OnPedidoClickListener {
        void onPedidoClick(int position);
    }
}
