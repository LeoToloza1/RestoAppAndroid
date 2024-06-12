package com.leotoloza.restoapp.ui.Pedido;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leotoloza.restoapp.Models.Producto;
import com.leotoloza.restoapp.R;

import java.util.List;

public class VistaPreviaAdapter extends RecyclerView.Adapter<VistaPreviaAdapter.ProductoViewHolder> {

    private List<Producto> productosSeleccionados;
    private List<Integer> cantidades;
    private Context context;

    public VistaPreviaAdapter(List<Producto> productosSeleccionados, List<Integer> cantidades, Context context) {
        this.productosSeleccionados = productosSeleccionados;
        this.cantidades = cantidades;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vista_previa, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = productosSeleccionados.get(position);
        int cantidad = cantidades.get(position);
        holder.tvNombreProducto.setText(producto.getNombre_producto());
        holder.tvPrecioProducto.setText(String.valueOf(producto.getPrecio()));
        holder.tvCantidadProducto.setText(String.valueOf(cantidad));

    }

    @Override
    public int getItemCount() {
        return productosSeleccionados.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombreProducto, tvPrecioProducto, tvCantidadProducto;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreProducto = itemView.findViewById(R.id.tvNombreProducto);
            tvPrecioProducto = itemView.findViewById(R.id.tvPrecioProducto);
            tvCantidadProducto = itemView.findViewById(R.id.tvCantidadProducto);

        }
    }
}
