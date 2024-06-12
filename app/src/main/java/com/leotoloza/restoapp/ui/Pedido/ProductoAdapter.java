package com.leotoloza.restoapp.ui.Pedido;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leotoloza.restoapp.Models.ProductoDTO;
import com.leotoloza.restoapp.R;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private List<ProductoDTO> productoList;

    public ProductoAdapter(List<ProductoDTO> productoList) {
        this.productoList = productoList;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto_pedido, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        ProductoDTO producto = productoList.get(position);
        holder.textViewNombreProducto.setText(producto.getNombre_producto());
        holder.textViewDescripcionProducto.setText(producto.getDescripcion());
        holder.textViewPrecioProducto.setText("Precio: $" + producto.getPrecio());
        holder.textViewCantidadProducto.setText("Cantidad: " + producto.getCantidad());
    }

    @Override
    public int getItemCount() {
        return productoList.size();
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombreProducto, textViewDescripcionProducto, textViewPrecioProducto, textViewCantidadProducto;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombreProducto = itemView.findViewById(R.id.textViewNombreProducto);
            textViewDescripcionProducto = itemView.findViewById(R.id.textViewDescripcionProducto);
            textViewPrecioProducto = itemView.findViewById(R.id.textViewPrecioProducto);
            textViewCantidadProducto = itemView.findViewById(R.id.textViewCantidadProducto);
        }
    }
}